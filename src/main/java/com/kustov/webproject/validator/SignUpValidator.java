package com.kustov.webproject.validator;

import java.util.regex.Pattern;


/**
 * The Class SignUpValidator.
 */
public class SignUpValidator {

    /**
     * The Constant USERNAME_REGEX.
     */
    private final static String USERNAME_REGEX = "[a-zA-Zа-яА-Я][a-zA-Zа-яА-Я\\d]+";

    /**
     * The Constant PASSWORD_REGEX.
     */
    private final static String PASSWORD_REGEX = "[a-zA-Zа-яА-Я\\d]+";

    /**
     * The Constant NAME_SURNAME_REGEX.
     */
    private final static String NAME_SURNAME_REGEX = "[А-ЯA-Z][а-яА-Яa-zA-Z]+";

    /**
     * The Constant EMAIL_REGEX.
     */
    private final static String EMAIL_REGEX = "\\w+@[a-zA-Z]+[.][a-zA-Z]+";

    /**
     * The Constant DATE_REGEX.
     */
    private final static String DATE_REGEX = "(19|20)\\d\\d[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])";

    /**
     * Check username.
     *
     * @param username the username
     * @return true, if successful
     */
    public boolean checkUsername(String username) {
        return (Pattern.matches(USERNAME_REGEX, username));
    }

    /**
     * Check email.
     *
     * @param email the email
     * @return true, if successful
     */
    public boolean checkEmail(String email) {
        return (Pattern.matches(EMAIL_REGEX, email));
    }

    /**
     * Check password.
     *
     * @param password the password
     * @return true, if successful
     */
    public boolean checkPassword(String password) {
        return (Pattern.matches(PASSWORD_REGEX, password));
    }

    /**
     * Check name and surname.
     *
     * @param name    the name
     * @param surname the surname
     * @return true, if successful
     */
    public boolean checkNameAndSurname(String name, String surname) {
        return (Pattern.matches(NAME_SURNAME_REGEX, name) && Pattern.matches(NAME_SURNAME_REGEX, surname));
    }

    /**
     * Check date.
     *
     * @param date the date
     * @return true, if successful
     */
    public boolean checkDate(String date) {
        return (Pattern.matches(DATE_REGEX, date));
    }
}
