package com.my_project.LightNovel_web_backend.service.Otp;

import com.my_project.LightNovel_web_backend.entity.User;
import com.my_project.LightNovel_web_backend.enums.Role;
import com.my_project.LightNovel_web_backend.exception.AppException;
import com.my_project.LightNovel_web_backend.exception.ErrorCode;
import com.my_project.LightNovel_web_backend.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class OtpService implements IOtpService{

    private final RedisTemplate<Object, Object> redisTemplate;

    private final JavaMailSender mailSender;

    private final UserRepository userRepository;

    @Override
    public void sendOtp(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new AppException(ErrorCode.INVALID_REQUEST, email);
        }
        String otp = String.format("%06d", new Random().nextInt(999999));
        redisTemplate.opsForValue().set("Otp:"+email, otp);
        redisTemplate.expire(email, 5, TimeUnit.MINUTES);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("RLH.com");
            helper.setText("Your OTP code is: " + otp + "This code will expire after 5 minutes.");

            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("Mail error");
        }
    }

    @Override
    public void verifyOtp(String otpInput, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                ()->new AppException(ErrorCode.INVALID_REQUEST, email)
        );
        String otp = (String) redisTemplate.opsForValue().get("Otp:"+email);
        if (otp==null) {
            throw new AppException(ErrorCode.OTP_INVALID, otpInput);
        }
        if (otp.compareTo(otpInput)==0) {
            user.setRole(Role.USER);
            redisTemplate.delete("Otp:"+email);
            userRepository.save(user);
        }
    }
}
