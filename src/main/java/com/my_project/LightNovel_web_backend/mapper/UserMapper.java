package com.my_project.LightNovel_web_backend.mapper;

import com.my_project.LightNovel_web_backend.enums.Role;
import com.my_project.LightNovel_web_backend.dto.request.UserRequest;
import com.my_project.LightNovel_web_backend.dto.response.UserResponse;
import com.my_project.LightNovel_web_backend.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {

    public User requestToEntity(UserRequest request) {
        User user = new User();
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        return user;
    }

    public UserResponse entityToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .image(user.getImage())
                .email(user.getEmail())
                .role(user.getRole())
                .userName(user.getUserName())
                .build();
    }
}
