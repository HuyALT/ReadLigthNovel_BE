package com.my_project.LightNovel_web_backend.dto.response;


import com.my_project.LightNovel_web_backend.enums.Role;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponse {
    private long id;
    private String userName;
    private String email;
    private String image;
    private Role role;
}
