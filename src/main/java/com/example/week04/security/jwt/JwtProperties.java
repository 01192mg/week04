package com.example.week04.security.jwt;

public interface JwtProperties {
	int SEC = 1;
	int MINUTE = 60 * SEC;
	int HOUR = 60 * MINUTE;
	int DAY = 24 * HOUR;

	// JWT 토큰의 유효기간: 3일 (단위: seconds)
	int JWT_TOKEN_VALID_SEC = 3 * DAY;

	String SECRET = "hanghae99"; // 우리 서버만 알고 있는 비밀값
	// JWT 토큰의 유효기간: 3일 (단위: milliseconds)
	int JWT_TOKEN_VALID_MILLI_SEC = JWT_TOKEN_VALID_SEC * 1000;
	String ACCESS_TOKEN_PREFIX = "Bearer ";
	String HEADER_ACCESS_TOKEN = "Authorization";
	String HEADER_REFRESH_TOKEN = "Refresh-Token";
	String HEADER_TOKEN_EXPIRE_TIME = "Access-Token-Expire-Time";

	String CLAIM_AUTH = "auth";
	String CLAIM_SUBJECT = "sub";
	String CLAIM_EXPIRE = "exp";
}
