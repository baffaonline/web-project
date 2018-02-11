package com.kustov.webproject.entity;

import java.io.Serializable;
import java.util.Objects;


/**
 * The Exception DAOException.
 */
public class Entity implements Serializable, Cloneable {

    /**
     * The id.
     */
    private int id;

    /**
     * Instantiates a new entity.
     */
    Entity() {
    }

    /**
     * Instantiates a new entity.
     *
     * @param id the id
     */
    Entity(int id) {
        this.id = id;
    }

    /**
     * Gets the ${e.g(1).rsfl()}.
     *
     * @return the ${e.g(1).rsfl()}
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the sql update ban
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return id == entity.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
