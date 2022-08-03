package com.example.week04.utils.validator;

import com.example.week04.dto.UserRequestDto;
import com.example.week04.utils.Properties;
import org.springframework.util.PatternMatchUtils;

public class SignupInputValidator {
    public static void validate(UserRequestDto loginRequestDto) {
        nullCheck(loginRequestDto);
        patternCheck(loginRequestDto);
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
        if (loginRequestDto.getPasswordConfirm() == null) {
            throw new IllegalArgumentException("passwordConfirm을 입력해주세요");
        }
    }

    private static void patternCheck(UserRequestDto loginRequestDto) {
        PatternMatchUtils.simpleMatch(Properties.LOGIN_NICKNAME_PATTERN, loginRequestDto.getNickname());
        PatternMatchUtils.simpleMatch(Properties.LOGIN_PASSWORD_PATTERN, loginRequestDto.getPassword());
    }
}
