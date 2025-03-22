package com.my_project.LightNovel_web_backend.service.Jwt;

import com.my_project.LightNovel_web_backend.entity.User;
import com.my_project.LightNovel_web_backend.exception.AppException;
import com.my_project.LightNovel_web_backend.exception.ErrorCode;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService implements IJwtService{

    private final RedisTemplate<Object, Object> redisTemplate;

    @Value("${jwt.signer_key}")
    private String signer_key;

    @Override
    public String generateToken(User user) {

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUserName())
                .issuer("RLH.com")
                .issueTime(new Date())
                .jwtID(UUID.randomUUID().toString())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()
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

    @Override
    public SignedJWT verifyToken(String token) {
        try {
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
        } catch (JOSEException | ParseException e) {
            log.error("Token check error");
            throw new AppException(ErrorCode.UNAUTHENTICATED, token);
        }
    }
}
