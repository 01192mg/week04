package com.example.week04.utils.validator;

import com.example.week04.dto.UserRequestDto;

public class LoginInputValidator {
    public static void validate(UserRequestDto loginRequestDto) {
        nullCheck(loginRequestDto);
    }

    private static void nullCheck(UserRequestDto loginRequestDto) {
        if (loginRequestDto == null) {
            throw new IllegalArgumentException("nickname, password를 입력해주세요");
        }
        if (loginRequestDto.getNickname() == null) {
            throw new IllegalArgumentException("nickname을 입력해주세요");
        }
        if (loginRequestDto.getPassword() == null) {
            throw new IllegalArgumentException("password를 입력해주세요");
        }
    }
}
