package com.kustov.webproject.entity;

import java.util.Objects;


/**
 * The Class ReviewUserRating.
 */

public class ReviewUserRating {

    /**
     * The user id.
     */
    private int userId;

    /**
     * The rating.
     */
    private int rating;

    /**
     * Instantiates a new review user rating.
     */
    public ReviewUserRating() {
    }

    /**
     * Instantiates a new review user rating.
     *
     * @param userId the user id
     * @param rating the rating
     */
    public ReviewUserRating(int userId, int rating) {
        this.userId = userId;
        this.rating = rating;
    }

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     *
     * @param userId the new user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the rating.
     *
     * @return the rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets the rating.
     *
     * @param rating the new rating
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewUserRating that = (ReviewUserRating) o;
        return userId == that.userId &&
                rating == that.rating;
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, rating);
    }
}