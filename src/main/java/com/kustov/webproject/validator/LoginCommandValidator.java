package com.kustov.webproject.validator;

import java.util.regex.Pattern;

public class LoginCommandValidator {
    private final static String LOGIN_PASSWORD_PATTERN = "[a-zA-Z][\\w]+";
    public static boolean checkLoginAndPassword(String login, String password){
        return (Pattern.matches(login, LOGIN_PASSWORD_PATTERN) && Pattern.matches(password, LOGIN_PASSWORD_PATTERN));
    }
}
