package com.example.week04.security;

import com.example.week04.entity.User;
import com.example.week04.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("PrincipalDetailsService : 진입");
		User user = userRepository.findByNickname(username).orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

		// session.setAttribute("loginUser", user);
		return new PrincipalDetails(user);
	}
}
