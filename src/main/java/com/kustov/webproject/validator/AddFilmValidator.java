package com.kustov.webproject.validator;

import java.util.regex.Pattern;


/**
 * The Class AddFilmValidator.
 */

public class AddFilmValidator {

    /**
     * The Constant DATE_REGEX.
     */
    private final static String DATE_REGEX = "(19|20)\\d\\d[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])";

    /**
     * The Constant AGE_RESTRICTION_REGEX.
     */
    private final static String AGE_RESTRICTION_REGEX = "\\d{1,2}";

    /**
     * Check date.
     *
     * @param dateString the date string
     * @return true, if successful
     */
    public boolean checkDate(String dateString) {
        return Pattern.matches(DATE_REGEX, dateString);
    }

    /**
     * Check age restriction.
     *
     * @param ageRestrictionString the age restriction string
     * @return true, if successful
     */
    public boolean checkAgeRestriction(String ageRestrictionString) {
        return Pattern.matches(AGE_RESTRICTION_REGEX, ageRestrictionString);
    }
}
