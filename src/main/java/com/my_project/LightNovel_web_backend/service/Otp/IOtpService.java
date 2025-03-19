package com.my_project.LightNovel_web_backend.service.Otp;

public interface IOtpService {
    void sendOtp(String email);
    void verifyOtp(String otpInput, String email);
}
