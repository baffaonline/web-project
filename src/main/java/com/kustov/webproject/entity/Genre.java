package com.kustov.webproject.entity;

import java.util.Objects;


/**
 * The Class Genre.
 */

public class Genre extends Entity {

    /** The name. */
    private String name;

    /**
     * Instantiates a new genre.
     */
    public Genre() {
    }

    /**
     * Instantiates a new genre.
     *
     * @param id the id
     * @param name the name
     */
    public Genre(int id, String name) {
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

    /* (non-Javadoc)
     * @see main.java.com.kustov.webproject.entity.Entity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return Objects.equals(name, genre.name);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


    @Override
    public String toString() {
        return name ;
    }
}
