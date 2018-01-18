package com.kustov.webproject.entity;

import java.util.List;
import java.util.Objects;

public class Actor extends Entity {
    private String name;
    private String surname;
    private Country country;
    private List<Film> films;

    public Actor() {
        films = null;
        country = new Country();
    }

    public Actor(int id, String name, String surname, Country country, List<Film> films) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.films = films;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return Objects.equals(name, actor.name) &&
                Objects.equals(surname, actor.surname) &&
                Objects.equals(country, actor.country) &&
                Objects.equals(films, actor.films);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, country, films);
    }

    @Override
    public String toString() {
        return "Actor " + name + " " + surname + " from " + country;
    }
}
