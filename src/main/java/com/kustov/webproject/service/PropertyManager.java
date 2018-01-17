package com.kustov.webproject.service;

import java.util.ResourceBundle;

public class PropertyManager {
    private ResourceBundle resourceBundle;

    public PropertyManager(String filename){
        resourceBundle = ResourceBundle.getBundle(filename);
    }

    public String getProperty(String key){
        return resourceBundle.getString(key);
    }
}
