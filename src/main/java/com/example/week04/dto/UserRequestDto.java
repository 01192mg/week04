package com.example.week04.dto;

import lombok.Getter;

@Getter
public class UserRequestDto {
    private String nickname;
    private String password;
    private String passwordConfirm;
}
