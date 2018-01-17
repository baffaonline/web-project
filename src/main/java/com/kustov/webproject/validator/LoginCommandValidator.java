package com.kustov.webproject.validator;

import java.util.regex.Pattern;

public class LoginCommandValidator {
    private final static String LOGIN_PATTERN = "[a-zA-Z]\\w+";
    private final static String PASSWORD_PATTERN = "\\w+";
    public static boolean checkLoginAndPassword(String login, String password){
        return (Pattern.matches(LOGIN_PATTERN, login) && Pattern.matches(PASSWORD_PATTERN, password));
    }
}
