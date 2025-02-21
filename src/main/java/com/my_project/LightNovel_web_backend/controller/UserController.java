package com.my_project.LightNovel_web_backend.controller;

import com.my_project.LightNovel_web_backend.service.Authentication.AuthenticationService;
import com.my_project.LightNovel_web_backend.service.User.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUser(){

        return ResponseEntity.ok(userService);
    }

}
