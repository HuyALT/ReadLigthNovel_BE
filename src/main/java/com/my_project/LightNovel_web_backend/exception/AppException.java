package com.my_project.LightNovel_web_backend.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppException extends RuntimeException{
    public AppException(ErrorCode errorCode, Object errorData) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.errorData = errorData;
    }

    private ErrorCode errorCode;
    private Object errorData;
}
