package com.kustov.webproject.entity;

import java.util.Objects;

public class ReviewUserRating {

    private int userId;
    private int rating;

    public ReviewUserRating() {
    }

    public ReviewUserRating(int userId, int rating) {
        this.userId = userId;
        this.rating = rating;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

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