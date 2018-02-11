package com.kustov.webproject.validator;

import java.util.regex.Pattern;


/**
 * The Class LoginCommandValidator.
 */

public class LoginCommandValidator {

    /**
     * The Constant LOGIN_PATTERN.
     */
    private final static String LOGIN_PATTERN = "[a-zA-Zа-яА-Я][a-zA-Zа-яА-Я\\d]+";

    /**
     * The Constant PASSWORD_PATTERN.
     */
    private final static String PASSWORD_PATTERN = "[a-zA-Z\\d]+";

    /**
     * Check login and password.
     *
     * @param login    the login
     * @param password the password
     * @return true, if successful
     */
    public static boolean checkLoginAndPassword(String login, String password) {
        return (Pattern.matches(LOGIN_PATTERN, login) && Pattern.matches(PASSWORD_PATTERN, password));
    }
}
