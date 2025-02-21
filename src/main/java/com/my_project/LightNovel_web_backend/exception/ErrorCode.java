package com.my_project.LightNovel_web_backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    USER_EXISTED(101, "User existed", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(102,"Email extisted", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(103,"Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(104,"Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(105, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    INVALID_LIGHTNOVEL_REQUEST(106,"Invalid ligth novel request",HttpStatus.BAD_REQUEST);


    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
