package com.my_project.LightNovel_web_backend.service.Jwt;

import com.my_project.LightNovel_web_backend.entity.User;
import com.nimbusds.jwt.SignedJWT;

public interface IJwtService {
    String generateToken(User user);
    SignedJWT verifyToken(String token);
}
