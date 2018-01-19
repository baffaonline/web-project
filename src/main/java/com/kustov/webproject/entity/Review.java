package com.kustov.webproject.entity;

import java.util.Objects;

public class Review {
    private int filmId;
    private User user;
    private String text;
    private int userMark;

    public Review() {
        User user = new User();
    }

    public Review(int filmId, User user, String text, int userMark) {
        this.filmId = filmId;
        this.user = user;
        this.text = text;
        this.userMark = userMark;
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

    public int getUserMark() {
        return userMark;
    }

    public void setUserMark(int userMark) {
        this.userMark = userMark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return filmId == review.filmId &&
                userMark == review.userMark &&
                Objects.equals(user, review.user) &&
                Objects.equals(text, review.text);
    }

    @Override
    public int hashCode() {

        return Objects.hash(filmId, user, text, userMark);
    }

    @Override
    public String toString() {
        return "Review to film " + filmId +
                " from user " + user.getUsername() +
                " with mark " + userMark;
    }
}
