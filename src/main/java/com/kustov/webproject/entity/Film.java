package com.kustov.webproject.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Film extends Entity{
    private String title;
    private Country country;
    private String description;
    private int ageRestriction;
    private LocalDate releaseDate;
    private String posterPath;
    private double rating;
    private List<Actor> actors;
    private List<Genre> genres;
    private List<Review> reviews;

    public Film() {
        releaseDate = LocalDate.MIN;
        country = new Country();
        actors = null;
        genres = null;
        reviews = null;
    }

    public Film(int id, String title, Country country, String description, int ageRestriction, LocalDate releaseDate,
                String posterPath, double rating, List<Actor> actors, List<Genre> genres, List<Review> reviews) {
        super(id);
        this.title = title;
        this.country = country;
        this.description = description;
        this.ageRestriction = ageRestriction;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.rating = rating;
        this.actors = actors;
        this.genres = genres;
        this.reviews = reviews;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(int ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public LocalDate getreleaseDate() {
        return releaseDate;
    }

    public void setreleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return ageRestriction == film.ageRestriction &&
                Double.compare(film.rating, rating) == 0 &&
                Objects.equals(title, film.title) &&
                Objects.equals(country, film.country) &&
                Objects.equals(description, film.description) &&
                Objects.equals(releaseDate, film.releaseDate) &&
                Objects.equals(posterPath, film.posterPath) &&
                Objects.equals(actors, film.actors) &&
                Objects.equals(genres, film.genres) &&
                Objects.equals(reviews, film.reviews);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, country, description, ageRestriction, releaseDate, posterPath, rating, actors, genres, reviews);
    }

    @Override
    public String toString() {
        return "Film" + title  + " from " + country + " with rating " + rating;
    }
}
