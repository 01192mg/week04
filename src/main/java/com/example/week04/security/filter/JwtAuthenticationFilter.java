package com.example.week04.security.filter;

import com.example.week04.dto.UserRequestDto;
import com.example.week04.security.UserDetailsImpl;
import com.example.week04.security.jwt.JwtProperties;
import com.example.week04.security.jwt.JwtUtils;
import com.example.week04.utils.validator.LoginInputValidator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// POST "/api/member/login" 요청 시에만 실행 됨
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
		this.setFilterProcessesUrl("/api/member/login");
	}

	// Authentication 객체 만들어서 리턴 => 의존 : AuthenticationManager
	// 인증 요청시에 실행되는 함수 => /login
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("JwtAuthenticationFilter : 진입");

		// request에 있는 username과 password를 파싱해서 자바 Object로 받기
		UserRequestDto loginRequestDto = UserRequestDto.from(request);

		LoginInputValidator.validate(loginRequestDto);

		// 유저네임패스워드 토큰 생성
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(
						loginRequestDto.getNickname(),
						loginRequestDto.getPassword());

		System.out.println("JwtAuthenticationFilter : 토큰생성완료");

		// authenticate() 시 '인증 프로바이더'가 유저 디테일 서비스의 loadUserByUsername(토큰의 첫번째 파라미터)를 호출
		// 유저 디테일 서비스는 UserDetails를 리턴
		// '인증 프로바이더'는 UserDetails(DB값)의 getPassword() 값과 토큰의 두번째 파라미터(credential)을 비교
		// 동일하면 Authentication 객체를 생성

		// Tip: 인증 프로바이더의 디폴트 서비스는 UserDetailsService 타입, 디폴트 암호화 방식은 BCryptPasswordEncoder
		// 결론은 인증 프로바이더에게 알려줄 필요가 없음.
		return getAuthenticationManager().authenticate(authenticationToken);
	}

	// JWT Token 생성해서 response에 담아주기
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
											Authentication authResult) throws ServletException, IOException {
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authResult.getPrincipal();

		// 토큰 만료 시간
		Long expireTime = JwtUtils.getTokenExpireTime();

		// 토큰 생성
		String jwtToken = JwtUtils.createJwtToken(userDetailsImpl, expireTime);
		String refreshToken = JwtUtils.createRefreshToken(expireTime);
		String tokenExpireTime = String.valueOf(expireTime);

		// 헤더에 토큰 추가
		response.addHeader(JwtProperties.HEADER_ACCESS_TOKEN, jwtToken);
		response.addHeader(JwtProperties.HEADER_REFRESH_TOKEN, refreshToken);
		response.addHeader(JwtProperties.HEADER_TOKEN_EXPIRE_TIME, tokenExpireTime);

		// login controller 에서 @AuthenticationPrincipal 로 Authentication 주입 받아야 하기 때문에 context에 넣어준다.
		SecurityContextHolder.getContext().setAuthentication(authResult);
		chain.doFilter(request, response);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
		throw new RuntimeException(failed.getMessage());
	}
}
