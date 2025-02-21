package com.my_project.LightNovel_web_backend.service.Authentication;

import com.my_project.LightNovel_web_backend.dto.request.AuthenticationRequest;
import com.my_project.LightNovel_web_backend.dto.response.AuthenticationResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface IAuthencationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
    void logout(String token) throws ParseException, JOSEException;
}
