package com.my_project.LightNovel_web_backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ErrorResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        return ResponseEntity.status(errorCode.getStatusCode()).body(new ErrorResponse(errorCode.getCode(), errorCode.getMessage(), exception.getErrorData()));
    }

}
