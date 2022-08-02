package com.example.week04.controller;

import com.example.week04.dto.ResponseDto;
import com.example.week04.dto.UserRequestDto;
import com.example.week04.entity.User;
import com.example.week04.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원 가입 요청 처리
    @PostMapping("/api/member/signup")
    public User registerUser(@RequestBody UserRequestDto requestDto) {
        return userService.registerUser(requestDto);
    }

    // 로그인 요청 처리
    @PostMapping("/api/member/login")
    public ResponseDto<User> login(Authentication authentication) {
        return userService.login(authentication);
    }
    //{
    //    "success": true,
    //    "data": {
    //        "id": 25,
    //        "nickname": "123ddaa",
    //        "createdAt": "2022-08-01T17:10:01.361956",
    //        "modifiedAt": "2022-08-01T17:10:01.361956"
    //    },
    //    "error": null
    //}


}