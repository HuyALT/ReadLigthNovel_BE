package com.my_project.LightNovel_web_backend.service.Authentication;

import com.my_project.LightNovel_web_backend.dto.request.AuthenticationRequest;
import com.my_project.LightNovel_web_backend.dto.response.AuthenticationResponse;
import com.my_project.LightNovel_web_backend.entity.User;
import com.my_project.LightNovel_web_backend.enums.Role;
import com.my_project.LightNovel_web_backend.exception.AppException;
import com.my_project.LightNovel_web_backend.exception.ErrorCode;
import com.my_project.LightNovel_web_backend.repository.UserRepository;
import com.my_project.LightNovel_web_backend.service.Otp.OtpService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthenticationService implements IAuthencationService {

    private final UserRepository userRepository;

    private final OtpService otpService;

    private final RedisTemplate<Object, Object> redisTemplate;


    @Value("${jwt.signer_key}")
    private String signer_key;

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

        String token = generateToken(user);
        authenticationResponse.setToken(token);
        authenticationResponse.setAuthenticate(true);

        return authenticationResponse;
    }

    @Override
    public void logout(String token) throws JOSEException, ParseException {
        SignedJWT signToken = verifyToken(token);

        String uuid = signToken.getJWTClaimsSet().getJWTID();
        Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();
        redisTemplate.opsForValue().set("Block:"+uuid, token);
        redisTemplate.expire(uuid, expiryTime.getTime(), TimeUnit.MILLISECONDS);
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUserName())
                .issuer("RLH.com")
                .issueTime(new Date())
                .jwtID(UUID.randomUUID().toString())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("scope", user.getRole())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(signer_key.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Can not create token", e);
            throw new RuntimeException(e);
        }
    }

    public SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(signer_key.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        boolean verified = signedJWT.verify(verifier);

        String uuid = signedJWT.getJWTClaimsSet().getJWTID();

        String datatoken = (String) redisTemplate.opsForValue().get("Block:"+uuid);

        if (datatoken!=null) {
            throw new AppException(ErrorCode.UNAUTHENTICATED, token);
        }

        if (!verified && expiryTime.after(new Date())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED, token);
        }

        return signedJWT;
    }

}
