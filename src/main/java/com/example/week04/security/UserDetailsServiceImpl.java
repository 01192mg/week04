package com.example.week04.security;

import com.example.week04.entity.Member;
import com.example.week04.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("PrincipalDetailsService : 진입");
		Member member = memberRepository.findByNickname(username).orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

		// session.setAttribute("loginUser", user);
		return new UserDetailsImpl(member);
	}
}
