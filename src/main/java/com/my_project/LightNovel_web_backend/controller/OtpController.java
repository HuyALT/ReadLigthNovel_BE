package com.my_project.LightNovel_web_backend.controller;

import com.my_project.LightNovel_web_backend.service.Otp.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/otp/")
public class OtpController {

    private final OtpService otpService;

    @PostMapping("verify")
    public ResponseEntity<?> verifyOtp(@RequestParam String email, @RequestParam String otpInput) {

        otpService.verifyOtp(otpInput, email);

        return ResponseEntity.ok().build();
    }

}
