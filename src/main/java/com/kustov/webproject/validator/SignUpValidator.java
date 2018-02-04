package com.kustov.webproject.validator;

import java.util.regex.Pattern;

public class SignUpValidator {
    private final static String USERNAME_REGEX = "[a-zA-Z]\\w+";
    private final static String PASSWORD_REGEX = "\\w+";
    private final static String NAME_SURNAME_REGEX = "[A-Z][a-zA-Z]+";
    private final static String EMAIL_REGEX = "\\w+@[a-zA-Z]+[.][a-zA-Z]+";
    private final static String DATE_REGEX = "(19|20)\\d\\d[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])";

    public boolean checkUsername(String username) {
        return (Pattern.matches(USERNAME_REGEX, username));
    }

    public boolean checkEmail(String email) {
        return (Pattern.matches(EMAIL_REGEX, email));
    }

    public boolean checkPassword(String password) {
        return (Pattern.matches(PASSWORD_REGEX, password));
    }

    public boolean checkNameAndSurname(String name, String surname) {
        return (Pattern.matches(NAME_SURNAME_REGEX, name) && Pattern.matches(NAME_SURNAME_REGEX, surname));
    }

    public boolean checkDate(String date) {
        return (Pattern.matches(DATE_REGEX, date));
    }
}
