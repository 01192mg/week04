package com.example.week04.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;

@Getter
public class UserRequestDto {
    private String nickname;
    private String password;
    private String passwordConfirm;

    public static UserRequestDto from(HttpServletRequest request) {
        ObjectMapper om = new ObjectMapper();
        UserRequestDto loginRequestDto = null;
        try {
            loginRequestDto = om.readValue(request.getInputStream(), UserRequestDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginRequestDto;
    }
}
