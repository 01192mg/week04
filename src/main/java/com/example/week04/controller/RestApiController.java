package com.example.week04.controller;

import com.example.week04.dto.UserRequestDto;
import com.example.week04.entity.User;
import com.example.week04.repository.UserRepository;
import com.example.week04.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
// @CrossOrigin // CORS 허용
public class RestApiController {

	private final UserRepository userRepository;
	private final PasswordEncoder bCryptPasswordEncoder;

	// 모든 사람이 접근 가능
	@GetMapping("home")
	public String home() {
		return "<h1>home</h1>";
	}

	// Tip : JWT를 사용하면 UserDetailsService를 호출하지 않기 때문에 @AuthenticationPrincipal 사용
	// 불가능.
	// 왜냐하면 @AuthenticationPrincipal은 UserDetailsService에서 리턴될 때 만들어지기 때문이다.

	// 유저 혹은 매니저 혹은 어드민이 접근 가능
	@GetMapping("user")
	public String user(Authentication authentication) {
		PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
		System.out.println("principal : " + principal.getUser());
		System.out.println("principal : " + principal.getUser().getNickname());
		System.out.println("principal : " + principal.getUser().getPassword());

		return "<h1>user</h1>";
	}

	// 매니저 혹은 어드민이 접근 가능
	@GetMapping("manager/reports")
	public String reports() {
		return "<h1>reports</h1>";
	}

	// 어드민이 접근 가능
	@GetMapping("admin/users")
	public List<User> users() {
		return userRepository.findAll();
	}

	@PostMapping("join")
	public String join(@RequestBody UserRequestDto userRequestDto) {
		System.out.println(userRequestDto.getNickname());
		System.out.println(userRequestDto.getPassword());
		System.out.println(userRequestDto.getPasswordConfirm());
		String encodedPassword = bCryptPasswordEncoder.encode(userRequestDto.getPassword());
		userRepository.save(new User(userRequestDto.getNickname(), encodedPassword));
		return "회원가입완료";
	}

}
