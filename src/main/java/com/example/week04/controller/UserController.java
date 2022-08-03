package com.example.week04.controller;

import com.example.week04.dto.ResponseDto;
import com.example.week04.dto.UserRequestDto;
import com.example.week04.security.UserDetailsImpl;
import com.example.week04.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final MemberService memberService;

    // 회원 가입 요청 처리
    @PostMapping("/api/member/signup")
    public ResponseDto<?> registerUser(@RequestBody UserRequestDto requestDto) {
        return memberService.registerUser(requestDto);
    }

    // 로그인 요청 처리
    @PostMapping("/api/member/login")
    public ResponseDto<?> login(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return memberService.login(userDetailsImpl);
    }

}