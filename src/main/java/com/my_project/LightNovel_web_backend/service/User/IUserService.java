package com.my_project.LightNovel_web_backend.service.User;

import com.my_project.LightNovel_web_backend.dto.request.UserRequest;
import com.my_project.LightNovel_web_backend.dto.response.UserResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;
import java.util.List;

public interface IUserService  {
    UserResponse addUser(UserRequest request);
    List<UserResponse> getAllUser();
    UserResponse getUserInfo(String token) throws ParseException, JOSEException;
    UserResponse changeImage(String token, String image);
    UserResponse chageEmail(String token, String email);
    boolean changePassword(String newPassword, String oldPassword);
}
