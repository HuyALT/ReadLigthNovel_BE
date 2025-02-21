package com.my_project.LightNovel_web_backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequest {
    @Size(min = 5,max = 30)
    private String userName;

    @Size(min = 6, max = 50)
    private String password;

    @Email
    private String email;
}
