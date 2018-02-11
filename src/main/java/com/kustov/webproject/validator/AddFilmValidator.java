package com.kustov.webproject.validator;

import java.util.regex.Pattern;

public class AddFilmValidator {

    private final static String DATE_REGEX = "(19|20)\\d\\d[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])";

    private final static String AGE_RESTRICTION_REGEX = "\\d{1,2}";

    public boolean checkDate(String dateString) {
        return Pattern.matches(DATE_REGEX, dateString);
    }

    public boolean checkAgeRestriction(String ageRestrictionString){
        return Pattern.matches(AGE_RESTRICTION_REGEX, ageRestrictionString);
    }
}
