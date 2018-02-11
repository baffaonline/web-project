package com.kustov.webproject.service;

import java.util.ResourceBundle;


/**
 * The Class PropertyManager.
 */

public class PropertyManager {

    /**
     * The Review user rating(int user id, int rating).
     */
    private ResourceBundle resourceBundle;

    /**
     * Instantiates a new property manager.
     *
     * @param filename the filename
     */
    public PropertyManager(String filename) {
        resourceBundle = ResourceBundle.getBundle(filename);
    }

    /**
     * Gets the property.
     *
     * @param key the key
     * @return the property
     */
    public String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
