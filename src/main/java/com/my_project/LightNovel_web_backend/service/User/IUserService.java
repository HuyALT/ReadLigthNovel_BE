package com.my_project.LightNovel_web_backend.service.User;

import com.my_project.LightNovel_web_backend.dto.request.UserRequest;
import com.my_project.LightNovel_web_backend.dto.response.UserResponse;

import java.util.List;

public interface IUserService  {
    UserResponse addUser(UserRequest request);
    List<UserResponse> getAllUser();
    UserResponse findByUsername();

}
