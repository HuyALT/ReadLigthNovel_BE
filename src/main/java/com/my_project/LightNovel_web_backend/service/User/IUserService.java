package com.my_project.LightNovel_web_backend.service.User;

import com.my_project.LightNovel_web_backend.dto.request.UserRequest;
import com.my_project.LightNovel_web_backend.dto.response.UserResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;
import java.util.List;

public interface IUserService  {
    List<UserResponse> getAllUser();
    UserResponse getUserInfo(String userName);
    UserResponse changeImage(String userName, String image);
    UserResponse chageEmail(String userName, String email);
    boolean changePassword(String newPassword, String oldPassword, String userName);

}
