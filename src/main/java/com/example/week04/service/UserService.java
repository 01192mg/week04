package com.example.week04.service;

import com.example.week04.dto.ResponseDto;
import com.example.week04.dto.UserRequestDto;
import com.example.week04.entity.User;
import com.example.week04.repository.UserRepository;
import com.example.week04.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User registerUser(UserRequestDto requestDto) {
        // 회원 ID 중복 확인
        String nickname = requestDto.getNickname();
        Optional<User> found = userRepository.findByNickname(nickname);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }

        // password 일치 확인
        if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
            throw new IllegalArgumentException("비밀번호, 비밀번호 확인이 일치하지 않습니다.");
        }

        // 패스워드 암호화
        String password = passwordEncoder.encode(requestDto.getPassword());

        User user = new User(nickname, password);
        return userRepository.save(user);
    }

    public ResponseDto<User> login(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();
        return ResponseDto.success(user);
    }
}