package com.my_project.LightNovel_web_backend.service.Authentication;

import com.my_project.LightNovel_web_backend.dto.request.AuthenticationRequest;
import com.my_project.LightNovel_web_backend.dto.request.UserRequest;
import com.my_project.LightNovel_web_backend.dto.response.AuthenticationResponse;
import com.my_project.LightNovel_web_backend.dto.response.UserResponse;

public interface IAuthencationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
    UserResponse addUser(UserRequest request);
    void logout(String token);
    void verifyUser(String email, String otpInput);
    void requestResetPassword(String email);
    void verifyRequestResetPassword(String email, String otpInput);
    void resetPassword(String newPassword, String email);
}
