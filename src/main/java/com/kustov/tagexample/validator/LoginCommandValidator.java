package com.kustov.tagexample.validator;

public class LoginCommandValidator {
    public static boolean checkLoginAndPassword(String login, String password){
        return (login != null && !login.isEmpty() && password != null && !password.isEmpty());
    }
}
