package com.example.week04.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.week04.security.UserDetailsImpl;

import java.util.Date;

public class JwtUtils {

    public static String createJwtToken(UserDetailsImpl userDetailsImpl, Long expireTime) {
        return JwtProperties.ACCESS_TOKEN_PREFIX +
                JWT.create()
                .withSubject(userDetailsImpl.getUsername())
                .withExpiresAt(new Date(expireTime))
                .withClaim(JwtProperties.CLAIM_AUTH, userDetailsImpl.getUser().getAuthority())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
    }

    public static String createRefreshToken(Long expireTime) {
        return JWT.create()
                .withExpiresAt(new Date(expireTime))
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
    }

    public static Long getTokenExpireTime() {
        return System.currentTimeMillis()+ JwtProperties.JWT_TOKEN_VALID_MILLI_SEC;
    }
}
