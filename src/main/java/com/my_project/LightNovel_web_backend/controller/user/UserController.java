package com.my_project.LightNovel_web_backend.controller.user;

import com.my_project.LightNovel_web_backend.service.Authentication.AuthenticationService;
import com.my_project.LightNovel_web_backend.service.User.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PatchMapping("/changeEmail")
    public ResponseEntity<?> changeEmail(@AuthenticationPrincipal Jwt jwt, String email) {
        return ResponseEntity.ok(userService.chageEmail(jwt.getTokenValue(), email));
    }

    @PatchMapping("/changeImage")
    public ResponseEntity<?> changeImage(@AuthenticationPrincipal Jwt jwt, String image) {
        return ResponseEntity.ok(userService.changeImage(jwt.getTokenValue(),image));
    }

}
