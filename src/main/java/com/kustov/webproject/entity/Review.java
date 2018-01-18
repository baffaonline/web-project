package com.kustov.webproject.entity;

import java.util.Objects;

public class Review {
    private Film film;
    private User user;
    private String text;
    private int userMark;

    public Review() {
        film = new Film();
        user = new User();
    }

    public Review(Film film, User user, String text, int userMark) {
        this.film = film;
        this.user = user;
        this.text = text;
        this.userMark = userMark;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
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
        return userMark == review.userMark &&
                Objects.equals(film, review.film) &&
                Objects.equals(user, review.user) &&
                Objects.equals(text, review.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(film, user, text, userMark);
    }

    @Override
    public String toString() {
        return "Review to " + film +
                " from " + user +
                " with mark " + userMark;
    }
}
