package com.kustov.webproject.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * The Class Review.
 */

public class Review {

    /**
     * The film id.
     */
    private int filmId;

    /**
     * The user.
     */
    private User user;

    /**
     * The text.
     */
    private String text;

    /**
     * The title.
     */
    private String title;

    /**
     * The user mark.
     */
    private int userMark;

    /**
     * The review user ratings.
     */
    private List<ReviewUserRating> reviewUserRatings;

    /**
     * Instantiates a new review.
     */
    public Review() {
        user = new User();
        reviewUserRatings = new ArrayList<>();
    }

    /**
     * Instantiates a new review.
     *
     * @param filmId            the film id
     * @param user              the user
     * @param text              the text
     * @param title             the title
     * @param userMark          the user mark
     * @param reviewUserRatings the review user ratings
     */
    public Review(int filmId, User user, String text, String title, int userMark,
                  List<ReviewUserRating> reviewUserRatings) {
        this.filmId = filmId;
        this.user = user;
        this.text = text;
        this.title = title;
        this.userMark = userMark;
        this.reviewUserRatings = reviewUserRatings;
    }

    /**
     * Gets the film id.
     *
     * @return the film id
     */
    public int getFilmId() {
        return filmId;
    }

    /**
     * Sets the film id.
     *
     * @param filmId the new film id
     */
    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    /**
     * Gets the user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user.
     *
     * @param user the new user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text.
     *
     * @param text the new text
     */
    public void setText(String text) {
        this.text = text;
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
     * Gets the user mark.
     *
     * @return the user mark
     */
    public int getUserMark() {
        return userMark;
    }

    /**
     * Sets the user mark.
     *
     * @param userMark the new user mark
     */
    public void setUserMark(int userMark) {
        this.userMark = userMark;
    }

    /**
     * Gets the review user ratings.
     *
     * @return the review user ratings
     */
    public List<ReviewUserRating> getReviewUserRatings() {
        return reviewUserRatings;
    }

    /**
     * Sets the review user ratings.
     *
     * @param reviewUserRatings the new review user ratings
     */
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
