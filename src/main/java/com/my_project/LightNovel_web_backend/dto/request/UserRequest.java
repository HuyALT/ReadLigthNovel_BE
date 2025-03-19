package com.my_project.LightNovel_web_backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRequest {

    @NotBlank
    @Size(min = 5,max = 30)
    private String userName;

    @NotBlank
    @Size(min = 4, max = 50)
    private String password;

    @Email
    @NotBlank
    private String email;
}
