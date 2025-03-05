package com.my_project.LightNovel_web_backend.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AuthenticationRequest {
    private String userName;
    private String password;
}
