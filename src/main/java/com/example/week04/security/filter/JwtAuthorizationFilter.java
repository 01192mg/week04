package com.example.week04.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.week04.entity.User;
import com.example.week04.repository.UserRepository;
import com.example.week04.security.PrincipalDetails;
import com.example.week04.security.jwt.JwtProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

// 인가
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private final UserRepository userRepository;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
		super(authenticationManager);
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String accessToken = request.getHeader(JwtProperties.HEADER_ACCESS_TOKEN);
		String refreshToken = request.getHeader(JwtProperties.HEADER_REFRESH_TOKEN);
		if (accessToken == null || !accessToken.startsWith(JwtProperties.ACCESS_TOKEN_PREFIX) || refreshToken == null) {
			chain.doFilter(request, response);
			return;
		}
		accessToken = accessToken.replace(JwtProperties.ACCESS_TOKEN_PREFIX, "");

		System.out.println("accessToken : " + accessToken);
		System.out.println("refreshToken : " + refreshToken);
		System.out.println("검증시작");
		// 토큰 검증 (이게 인증이기 때문에 AuthenticationManager도 필요 없음)
		// 내가 SecurityContext에 집적접근해서 세션을 만들때 자동으로 UserDetailsService에 있는
		// loadByUsername이 호출됨.
		DecodedJWT decodedAccessToken = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(accessToken);
		Long accessTokenExp = decodedAccessToken.getClaim(JwtProperties.CLAIM_EXPIRE).asLong();
		Long refreshTokenExp = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(refreshToken).getClaim(JwtProperties.CLAIM_EXPIRE).asLong();
		System.out.println(accessTokenExp);
		System.out.println(refreshTokenExp);
		if (!Objects.equals(accessTokenExp, refreshTokenExp)) {
			throw new SignatureVerificationException(Algorithm.HMAC512(JwtProperties.SECRET));
		}

		String username = decodedAccessToken.getClaim(JwtProperties.CLAIM_SUBJECT).asString();
		System.out.println("검증끝 username=" + username);
		if (username != null) {
			User user = userRepository.findByNickname(username).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

			// 인증은 토큰 검증시 끝. 인증을 하기 위해서가 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위해
			// 아래와 같이 토큰을 만들어서 Authentication 객체를 강제로 만들고 그걸 세션에 저장!
			PrincipalDetails principalDetails = new PrincipalDetails(user);
			Authentication authentication = new UsernamePasswordAuthenticationToken(
					principalDetails, // 나중에 컨트롤러에서 DI해서 쓸 때 사용하기 편함.
					null, // 패스워드는 모르니까 null 처리, 어차피 지금 인증하는게 아니니까!!
					principalDetails.getAuthorities());

			// 강제로 시큐리티의 세션에 접근하여 값 저장
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		System.out.println("AuthorizationFilter 끝");
		chain.doFilter(request, response);
	}

}
