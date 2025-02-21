package com.my_project.LightNovel_web_backend.service.Authentication;

import com.my_project.LightNovel_web_backend.dto.request.AuthenticationRequest;
import com.my_project.LightNovel_web_backend.dto.response.AuthenticationResponse;
import com.my_project.LightNovel_web_backend.entity.TokenBlackList;
import com.my_project.LightNovel_web_backend.entity.User;
import com.my_project.LightNovel_web_backend.exception.AppException;
import com.my_project.LightNovel_web_backend.exception.ErrorCode;
import com.my_project.LightNovel_web_backend.repository.TokenBackListRepository;
import com.my_project.LightNovel_web_backend.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthenticationService implements IAuthencationService{

    private final UserRepository userRepository;

    private final TokenBackListRepository tokenBackListRepository;

    @Value("${jwt.signer_key}")
    private String signer_key;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        Optional<User> userOptional = userRepository.findByUserName(request.getUserName());

        if (userOptional.isPresent()){
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            boolean authenticated = passwordEncoder.matches(request.getPassword(), userOptional.get().getPassword());
            if (!authenticated){
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }
            String token = generateToken(userOptional.get());
            authenticationResponse.setToken(token);
            authenticationResponse.setAuthenticate(true);
        }
        else {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return authenticationResponse;
    }

    @Override
    public void logout(String token) throws JOSEException, ParseException {
        SignedJWT signToken = verifyToken(token);

        String uuid = signToken.getJWTClaimsSet().getJWTID();
        Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

        TokenBlackList tokenBlackList = new TokenBlackList(uuid,expiryTime);

        tokenBackListRepository.save(tokenBlackList);

    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUserName())
                .issuer("readlightnovel.com")
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

        Optional<TokenBlackList> tokenBlackList = tokenBackListRepository.findById(signedJWT.getJWTClaimsSet().getJWTID());

        if (tokenBlackList.isPresent()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if (!verified && expiryTime.after(new Date())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }

}
