package com.my_project.LightNovel_web_backend.mapper;

import com.my_project.LightNovel_web_backend.Enum.Role;
import com.my_project.LightNovel_web_backend.dto.request.UserRequest;
import com.my_project.LightNovel_web_backend.dto.response.UserResponse;
import com.my_project.LightNovel_web_backend.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User requestToEntity(UserRequest request) {
        User user = new User();
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        if (request.getImage().isEmpty()) {
            user.setImage("https://ik.imagekit.io/dx1lgwjws/News/default-avatar.jpg?updatedAt=1716483707937");
        }
        else {
            user.setImage(request.getImage());
        }
        return user;
    }

    public UserResponse entityToResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUserName(user.getUserName());
        userResponse.setRole(user.getRole());
        userResponse.setEmail(user.getEmail());
        userResponse.setImage(user.getImage());
        return userResponse;
    }
}
