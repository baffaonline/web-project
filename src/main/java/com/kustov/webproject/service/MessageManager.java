package com.kustov.webproject.service;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {

    private static Locale locale;

    public String getString(String key){
        ResourceBundle bundle = ResourceBundle.getBundle("message", locale);
        return bundle.getString(key);
    }

    public static void setLocale(Locale locale) {
        MessageManager.locale = locale;
    }
}
