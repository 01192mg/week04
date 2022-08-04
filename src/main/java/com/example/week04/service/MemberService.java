package com.example.week04.service;

import com.example.week04.dto.ResponseDto;
import com.example.week04.dto.UserRequestDto;
import com.example.week04.entity.Member;
import com.example.week04.repository.MemberRepository;
import com.example.week04.security.UserDetailsImpl;
import com.example.week04.utils.validator.SignupInputValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public ResponseDto<?> registerUser(UserRequestDto requestDto) {
        SignupInputValidator.validate(requestDto);
        // 회원 ID 중복 확인
        String nickname = requestDto.getNickname();
        Optional<Member> found = memberRepository.findByNickname(nickname);
        if (found.isPresent()) {
            return ResponseDto.fail("DUPLICATE_ID", "중복된 사용자 ID 가 존재합니다.");
        }

        // password 일치 확인
        if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
            return ResponseDto.fail("INVALID_PASSWORD", "비밀번호, 비밀번호 확인이 일치하지 않습니다.");
        }

        // 패스워드 암호화
        String password = passwordEncoder.encode(requestDto.getPassword());

        Member member = new Member(nickname, password);
        return ResponseDto.success(memberRepository.save(member));
    }

    public ResponseDto<?> login(UserDetailsImpl userDetails) {
        Member member = userDetails.getUser();
        return ResponseDto.success(member);
    }
}