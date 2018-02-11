package com.kustov.webproject.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * The Class User.
 */

public class User extends Entity {

    /**
     * The username.
     */
    private String username;

    /**
     * The password.
     */
    private String password;

    /**
     * The email.
     */
    private String email;

    /**
     * The name.
     */
    private String name;

    /**
     * The surname.
     */
    private String surname;

    /**
     * The birthday.
     */
    private LocalDate birthday;

    /**
     * The country.
     */
    private Country country;

    /**
     * The rating.
     */
    private int rating;

    /**
     * The is banned.
     */
    private boolean isBanned;

    /**
     * The type.
     */
    private UserType type;

    /**
     * The reviews.
     */
    private List<Review> reviews;

    /**
     * Instantiates a new user.
     */
    public User() {
        birthday = LocalDate.MIN;
        type = UserType.GUEST;
        reviews = new ArrayList<>();
    }

    /**
     * Instantiates a new user.
     *
     * @param id       the id
     * @param username the username
     * @param password the password
     * @param email    the email
     * @param name     the name
     * @param surname  the surname
     * @param birthday the birthday
     * @param country  the country
     * @param rating   the rating
     * @param isBanned the is banned
     * @param type     the type
     * @param reviews  the reviews
     */
    public User(int id, String username, String password, String email, String name, String surname, LocalDate birthday,
                Country country, int rating, boolean isBanned, UserType type, List<Review> reviews) {
        super(id);
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.country = country;
        this.rating = rating;
        this.isBanned = isBanned;
        this.type = type;
        this.reviews = reviews;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public UserType getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    public void setType(UserType type) {
        this.type = type;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
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
     * Gets the birthday.
     *
     * @return the birthday
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * Sets the birthday.
     *
     * @param birthday the new birthday
     */
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
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

    /**
     * Checks if is banned.
     *
     * @return true, if is banned
     */
    public boolean isBanned() {
        return isBanned;
    }

    /**
     * Sets the banned.
     *
     * @param banned the new banned
     */
    public void setBanned(boolean banned) {
        isBanned = banned;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return rating == user.rating &&
                isBanned == user.isBanned &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(birthday, user.birthday) &&
                Objects.equals(country, user.country) &&
                type == user.type &&
                Objects.equals(reviews, user.reviews);
    }


    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), username, password, email, name, surname, birthday, country, rating, isBanned, type, reviews);
    }


    @Override
    public String toString() {
        return "User : id : " + this.getId() +
                ", username : " + username +
                ", password : " + password +
                ", email : " + email +
                ", name : " + name +
                ", surname : " + surname +
                ", birthday : " + birthday +
                ", country : " + country +
                ", rating : " + rating +
                ", type : " + type +
                ", isBanned : " + isBanned;
    }
}
