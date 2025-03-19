package com.my_project.LightNovel_web_backend.service.User;

import com.my_project.LightNovel_web_backend.dto.request.UserRequest;
import com.my_project.LightNovel_web_backend.dto.response.UserResponse;
import com.my_project.LightNovel_web_backend.entity.User;
import com.my_project.LightNovel_web_backend.enums.Role;
import com.my_project.LightNovel_web_backend.exception.AppException;
import com.my_project.LightNovel_web_backend.exception.ErrorCode;
import com.my_project.LightNovel_web_backend.mapper.UserMapper;
import com.my_project.LightNovel_web_backend.repository.UserRepository;
import com.my_project.LightNovel_web_backend.service.Authentication.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final AuthenticationService authenticationService;

    private final PasswordEncoder passwordEncoder;

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
    public List<UserResponse> getAllUser() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::entityToResponse)
                .toList();
    }

    @Override
    public UserResponse getUserInfo(String token)  {

        return userMapper.entityToResponse(extractUser(token));
    }

    @Override
    public UserResponse changeImage(String token, String image) {
        User user = extractUser(token);
        user.setImage(image);
        return userMapper.entityToResponse(userRepository.save(user));
    }

    @Override
    public UserResponse chageEmail(String token, String email) {
        User user = extractUser(token);
        user.setEmail(email);
        return userMapper.entityToResponse(userRepository.save(user));
    }

    @Override
    public boolean changePassword(String newPassword, String oldPassword) {
        return false;
    }

    private User extractUser(String token)  {
        try {
            SignedJWT signedJWT = authenticationService.verifyToken(token);
            String username = signedJWT.getJWTClaimsSet().getSubject();
            return userRepository.findByUserName(username).orElseThrow(
                    ()-> new AppException(ErrorCode.UNAUTHENTICATED, token)
            );
        } catch (JOSEException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
