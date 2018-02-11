package com.kustov.webproject.service;

import java.util.Locale;
import java.util.ResourceBundle;


/**
 * The Class MessageManager.
 */

public class MessageManager {

    /** The locale. */
    private static Locale locale;

    /**
     * Gets the string.
     *
     * @param key the key
     * @return the string
     */
    public String getString(String key){
        ResourceBundle bundle = ResourceBundle.getBundle("message", locale);
        return bundle.getString(key);
    }

    /**
     * Sets the locale.
     *
     * @param locale the new locale
     */
    public static void setLocale(Locale locale) {
        MessageManager.locale = locale;
    }
}
