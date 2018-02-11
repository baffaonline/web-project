package com.kustov.webproject.entity;

import java.util.Objects;


/**
 * The Class Country.
 */
public class Country extends Entity {

    /**
     * The name.
     */
    private String name;

    /**
     * Instantiates a new country.
     */
    public Country() {
    }

    /**
     * Instantiates a new country.
     *
     * @param id   the id
     * @param name the name
     */
    public Country(int id, String name) {
        super(id);
        this.name = name;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return super.equals(o) && Objects.equals(name, country.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
