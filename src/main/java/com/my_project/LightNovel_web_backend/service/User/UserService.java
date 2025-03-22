package com.my_project.LightNovel_web_backend.service.User;

import com.my_project.LightNovel_web_backend.dto.response.UserResponse;
import com.my_project.LightNovel_web_backend.entity.User;

import com.my_project.LightNovel_web_backend.exception.AppException;
import com.my_project.LightNovel_web_backend.exception.ErrorCode;
import com.my_project.LightNovel_web_backend.mapper.UserMapper;
import com.my_project.LightNovel_web_backend.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> getAllUser() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::entityToResponse)
                .toList();
    }

    @Override
    public UserResponse getUserInfo(String userName)  {

        User user = userRepository.findByUserName(userName).orElseThrow(
                ()->new AppException(ErrorCode.INVALID_REQUEST, userName)
        );

        return userMapper.entityToResponse(user);
    }

    @Override
    @Transactional
    public UserResponse changeImage(String userName, String image) {
        User user = userRepository.findByUserName(userName).orElseThrow(
                ()->new AppException(ErrorCode.INVALID_REQUEST, userName)
        );
        user.setImage(image);
        return userMapper.entityToResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponse chageEmail(String userName, String email) {
        User user = userRepository.findByUserName(userName).orElseThrow(
                ()->new AppException(ErrorCode.INVALID_REQUEST, userName)
        );
        user.setEmail(email);
        return userMapper.entityToResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public boolean changePassword(String newPassword, String oldPassword, String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(
                ()->new AppException(ErrorCode.INVALID_REQUEST, userName)
        );
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED, oldPassword);
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        return true;
    }
}
