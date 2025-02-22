package com.my_project.LightNovel_web_backend.controller;

import com.my_project.LightNovel_web_backend.dto.request.AuthenticationRequest;
import com.my_project.LightNovel_web_backend.dto.request.UserRequest;
import com.my_project.LightNovel_web_backend.exception.AppException;
import com.my_project.LightNovel_web_backend.exception.ErrorCode;
import com.my_project.LightNovel_web_backend.service.Authentication.AuthenticationService;
import com.my_project.LightNovel_web_backend.service.User.UserService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthencationController {

    private final UserService userService;
    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            throw new AppException(ErrorCode.REGISTER_REQUEST_INVALID);
        }
        return ResponseEntity.ok(userService.addUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.authenticate(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String bearerToken) throws ParseException, JOSEException {
        String token = bearerToken.substring(6);
        authenticationService.logout(token);
        return ResponseEntity.ok().build();
    }
}
