package com.my_project.LightNovel_web_backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    USER_EXISTED(101, "User existed", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(102,"Email extisted", HttpStatus.BAD_REQUEST),
    REGISTER_REQUEST_INVALID(103,"Can not register", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(104, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    INVALID_REQUEST(105,"something was wrong with your request",HttpStatus.BAD_REQUEST),
    NOTFOUND(106, "No data available", HttpStatus.NOT_FOUND);


    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
