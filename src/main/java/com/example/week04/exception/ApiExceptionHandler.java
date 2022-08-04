package com.example.week04.exception;

import com.example.week04.dto.ResponseDto;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({IllegalArgumentException.class, AuthenticationException.class})
    private ResponseDto<?> invalidIdException(Exception e) {
        return ResponseDto.fail("BAD_REQUEST", e.getMessage());
    }
}
