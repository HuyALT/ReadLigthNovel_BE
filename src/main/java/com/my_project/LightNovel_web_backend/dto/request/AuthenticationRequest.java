package com.my_project.LightNovel_web_backend.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthenticationRequest {
    private String userName;
    private String password;
}
