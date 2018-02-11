package com.kustov.webproject.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Review {

    private int filmId;
    private User user;
    private String text;
    private String title;
    private int userMark;
    private List<ReviewUserRating> reviewUserRatings;

    public Review() {
        user = new User();
        reviewUserRatings = new ArrayList<>();
    }

    public Review(int filmId, User user, String text, String title, int userMark,
                  List<ReviewUserRating> reviewUserRatings) {
        this.filmId = filmId;
        this.user = user;
        this.text = text;
        this.title = title;
        this.userMark = userMark;
        this.reviewUserRatings = reviewUserRatings;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserMark() {
        return userMark;
    }

    public void setUserMark(int userMark) {
        this.userMark = userMark;
    }

    public List<ReviewUserRating> getReviewUserRatings() {
        return reviewUserRatings;
    }

    public void setReviewUserRatings(List<ReviewUserRating> reviewUserRatings) {
        this.reviewUserRatings = reviewUserRatings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return filmId == review.filmId &&
                userMark == review.userMark &&
                Objects.equals(user, review.user) &&
                Objects.equals(text, review.text) &&
                Objects.equals(title, review.title) &&
                Objects.equals(reviewUserRatings, review.reviewUserRatings);
    }

    @Override
    public int hashCode() {

        return Objects.hash(filmId, user, text, title, userMark, reviewUserRatings);
    }

    @Override
    public String toString() {
        return "Review to film " + filmId +
                " from user " + user.getUsername() +
                " with mark " + userMark;
    }
}
