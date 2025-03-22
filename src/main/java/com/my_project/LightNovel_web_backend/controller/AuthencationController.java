package com.my_project.LightNovel_web_backend.controller;

import com.my_project.LightNovel_web_backend.dto.request.AuthenticationRequest;
import com.my_project.LightNovel_web_backend.dto.request.UserRequest;
import com.my_project.LightNovel_web_backend.exception.AppException;
import com.my_project.LightNovel_web_backend.exception.ErrorCode;
import com.my_project.LightNovel_web_backend.service.Authentication.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthencationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            throw new AppException(ErrorCode.REGISTER_REQUEST_INVALID, request);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.addUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.authenticate(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal Jwt jwt) throws ParseException, JOSEException {
        authenticationService.logout(jwt.getTokenValue());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify-user")
    public ResponseEntity<?> verifyUser(@RequestParam String email, @RequestParam String otpInput) {

        authenticationService.verifyUser(email, otpInput);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/request-reset-password")
    public ResponseEntity<?> requestResetPassword(@RequestParam String email) {
        authenticationService.requestResetPassword(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify-request-reset-password")
    public ResponseEntity<?> verifyResetPasswrod(@RequestParam String otpInput, @RequestParam String email) {
        authenticationService.verifyRequestResetPassword(email, otpInput);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String email, @RequestParam String newPassword) {
        authenticationService.resetPassword(newPassword, email);
        return ResponseEntity.ok().build();
    }

}
