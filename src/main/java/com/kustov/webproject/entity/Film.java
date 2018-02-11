package com.kustov.webproject.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


/**
 * The Class Film.
 */
public class Film extends Entity {

    /**
     * The title.
     */
    private String title;

    /**
     * The country.
     */
    private Country country;

    /**
     * The description.
     */
    private String description;

    /**
     * The age restriction.
     */
    private int ageRestriction;

    /**
     * The release date.
     */
    private LocalDate releaseDate;

    /**
     * The poster path.
     */
    private String posterPath;

    /**
     * The rating.
     */
    private double rating;

    /**
     * The actors.
     */
    private List<Actor> actors;

    /**
     * The genres.
     */
    private List<Genre> genres;

    /**
     * The reviews.
     */
    private List<Review> reviews;

    /**
     * Instantiates a new film.
     */
    public Film() {
        releaseDate = LocalDate.MIN;
        country = new Country();
        actors = null;
        genres = null;
        reviews = null;
    }

    /**
     * Instantiates a new film.
     *
     * @param id             the id
     * @param title          the title
     * @param country        the country
     * @param description    the description
     * @param ageRestriction the age restriction
     * @param releaseDate    the release date
     * @param posterPath     the poster path
     * @param rating         the rating
     * @param actors         the actors
     * @param genres         the genres
     * @param reviews        the reviews
     */
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

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title the new title
     */
    public void setTitle(String title) {
        this.title = title;
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
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the age restriction.
     *
     * @return the age restriction
     */
    public int getAgeRestriction() {
        return ageRestriction;
    }

    /**
     * Sets the age restriction.
     *
     * @param ageRestriction the new age restriction
     */
    public void setAgeRestriction(int ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    /**
     * Gets the release date.
     *
     * @return the release date
     */
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets the release date.
     *
     * @param releaseDate the new release date
     */
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Gets the poster path.
     *
     * @return the poster path
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * Sets the poster path.
     *
     * @param posterPath the new poster path
     */
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    /**
     * Gets the actors.
     *
     * @return the actors
     */
    public List<Actor> getActors() {
        return actors;
    }

    /**
     * Sets the actors.
     *
     * @param actors the new actors
     */
    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    /**
     * Gets the genres.
     *
     * @return the genres
     */
    public List<Genre> getGenres() {
        return genres;
    }

    /**
     * Sets the genres.
     *
     * @param genres the new genres
     */
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    /**
     * Gets the reviews.
     *
     * @return the reviews
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * Sets the reviews.
     *
     * @param reviews the new reviews
     */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    /**
     * Gets the rating.
     *
     * @return the rating
     */
    public double getRating() {
        return rating;
    }

    /**
     * Sets the rating.
     *
     * @param rating the new rating
     */
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
        return "Film " + title + " from " + country + " with rating " + rating;
    }
}
