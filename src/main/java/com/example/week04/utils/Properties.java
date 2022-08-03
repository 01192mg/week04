package com.example.week04.utils;

public interface Properties {
    String LOGIN_NICKNAME_PATTERN = "^(?=.*[a-zA-Z0-9])[a-zA-Z0-9]{4, 12}$";
    String LOGIN_PASSWORD_PATTERN = "^(?=.*[a-z0-9])[a-z0-9]{4, 32}$";
}
