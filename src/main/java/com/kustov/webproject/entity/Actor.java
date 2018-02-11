package com.kustov.webproject.entity;

import java.util.List;
import java.util.Objects;


/**
 * The Class Actor.
 */
public class Actor extends Entity {

    /**
     * The name.
     */
    private String name;

    /**
     * The surname.
     */
    private String surname;

    /**
     * The image path.
     */
    private String imagePath;

    /**
     * The country.
     */
    private Country country;

    /**
     * The films.
     */
    private List<Film> films;

    /**
     * Instantiates a new actor.
     */
    public Actor() {
        films = null;
        country = null;
    }

    /**
     * Instantiates a new actor.
     *
     * @param id        the id
     * @param name      the name
     * @param surname   the surname
     * @param imagePath the image path
     * @param country   the country
     * @param films     the films
     */
    public Actor(int id, String name, String surname, String imagePath, Country country, List<Film> films) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.imagePath = imagePath;
        this.country = country;
        this.films = films;
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

    /**
     * Gets the surname.
     *
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname.
     *
     * @param surname the new surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the country.
     *
     * @return the country
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Sets the country.
     *
     * @param country the new country
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * Gets the films.
     *
     * @return the films
     */
    public List<Film> getFilms() {
        return films;
    }

    /**
     * Sets the films.
     *
     * @param films the new films
     */
    public void setFilms(List<Film> films) {
        this.films = films;
    }

    /**
     * Gets the image path.
     *
     * @return the image path
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Sets the image path.
     *
     * @param imagePath the new image path
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return Objects.equals(name, actor.name) &&
                Objects.equals(surname, actor.surname) &&
                Objects.equals(imagePath, actor.imagePath) &&
                Objects.equals(country, actor.country) &&
                Objects.equals(films, actor.films);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name, surname, imagePath, country, films);
    }

    @Override
    public String toString() {
        return name + " " + surname + " from " + country;
    }
}
