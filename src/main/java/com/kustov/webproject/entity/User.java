package com.kustov.webproject.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User extends Entity {
    private String username;
    private String password;
    private String email;
    private String name;
    private String surname;
    private LocalDate birthday;
    private Country country;
    private int rating;
    private boolean isBanned;
    private UserType type;
    private List<Review> reviews;

    public User() {
        birthday = LocalDate.MIN;
        type = UserType.GUEST;
        reviews = new ArrayList<>();
    }

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

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public List<Review> getReviews() {
        return reviews;
    }

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
