package com.my_project.LightNovel_web_backend.service.Authentication;

import com.my_project.LightNovel_web_backend.dto.request.AuthenticationRequest;
import com.my_project.LightNovel_web_backend.dto.request.UserRequest;
import com.my_project.LightNovel_web_backend.dto.response.AuthenticationResponse;
import com.my_project.LightNovel_web_backend.dto.response.UserResponse;
import com.my_project.LightNovel_web_backend.entity.User;
import com.my_project.LightNovel_web_backend.enums.Role;
import com.my_project.LightNovel_web_backend.exception.AppException;
import com.my_project.LightNovel_web_backend.exception.ErrorCode;
import com.my_project.LightNovel_web_backend.mapper.UserMapper;
import com.my_project.LightNovel_web_backend.repository.UserRepository;
import com.my_project.LightNovel_web_backend.service.Jwt.JwtService;
import com.my_project.LightNovel_web_backend.service.Otp.OtpService;
import com.nimbusds.jwt.SignedJWT;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthenticationService implements IAuthencationService {

    private final UserRepository userRepository;

    private final OtpService otpService;

    private final RedisTemplate<Object, Object> redisTemplate;

    private final JwtService jwtService;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        User user = userRepository.findByUserName(request.getUserName()).orElseThrow(
                () -> new AppException(ErrorCode.UNAUTHENTICATED, request)
        );

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED, request);
        }

        if (user.getRole() == Role.UNVERIFIED) {
            otpService.sendOtp(user.getEmail());
            throw new AppException(ErrorCode.ACCOUNT_UNVERIFIED, user.getEmail());
        }

        String token = jwtService.generateToken(user);
        authenticationResponse.setToken(token);
        authenticationResponse.setAuthenticate(true);

        return authenticationResponse;
    }

    @Override
    @Transactional
    public UserResponse addUser(UserRequest request) {
        if (userRepository.existsByUserName(request.getUserName())){
            throw new AppException(ErrorCode.USER_EXISTED, request.getUserName());
        }
        if (userRepository.existsByEmail(request.getEmail())){
            throw new AppException(ErrorCode.EMAIL_EXISTED, request.getEmail());
        }
        User user = userMapper.requestToEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.UNVERIFIED);
        user.setImage("https://ik.imagekit.io/dx1lgwjws/News/default-avatar.jpg?updatedAt=1716483707937");

        return userMapper.entityToResponse(userRepository.save(user));
    }

    @Override
    public void logout(String token) {
        SignedJWT signToken = jwtService.verifyToken(token);
        try {
            String uuid = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();
            redisTemplate.opsForValue().set("Block:"+uuid, token);
            redisTemplate.expire("Block:"+uuid, expiryTime.getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            log.error("logout fail");
        }

    }

    @Override
    public void verifyUser(String email, String otpInput) {
        User user = userRepository.findByEmail(email).orElseThrow(
                ()->new AppException(ErrorCode.INVALID_REQUEST, email)
        );
        if (otpService.verifyOtp(otpInput, email)) {
            user.setRole(Role.USER);
            userRepository.save(user);
            log.info("{} have role USER", email);
        }
        else {
            throw new AppException(ErrorCode.OTP_INVALID, otpInput);
        }
    }

    @Override
    public void requestResetPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                ()->new AppException(ErrorCode.INVALID_REQUEST, email)
        );
        if (user.getRole()==Role.USER || user.getRole()==Role.ADMIN) {
            otpService.sendOtp(email);
            return;
        }
        throw new AppException(ErrorCode.INVALID_REQUEST, email);
    }

    @Override
    @Transactional
    public void verifyRequestResetPassword(String email, String otpInput) {
        User user = userRepository.findByEmail(email).orElseThrow(
                ()->new AppException(ErrorCode.INVALID_REQUEST, email)
        );
        if (otpService.verifyOtp(otpInput, email)) {
          user.setRole(Role.RESETPASS);
          userRepository.save(user);
        }
        throw new AppException(ErrorCode.OTP_INVALID, otpInput);
    }

    @Override
    public void resetPassword(String newPassword, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                ()->new AppException(ErrorCode.INVALID_REQUEST, email)
        );
        if (user.getRole()!=Role.RESETPASS) {
            throw new AppException(ErrorCode.UNAUTHENTICATED, email);
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        log.info("{} password change", newPassword);
    }



}
