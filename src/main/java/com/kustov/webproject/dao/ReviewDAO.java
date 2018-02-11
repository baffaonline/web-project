package com.kustov.webproject.dao;

import com.kustov.webproject.entity.Review;
import com.kustov.webproject.entity.ReviewUserRating;
import com.kustov.webproject.entity.User;
import com.kustov.webproject.exception.ConnectionException;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.pool.DBConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * The Class ReviewDAO.
 */

public class ReviewDAO extends AbstractReviewDAO {

    /**
     * The Constant SQL_SELECT_REVIEWS_BY_FILM_ID.
     */
    private final static String SQL_SELECT_REVIEWS_BY_FILM_ID = "SELECT film_id, review_title, review_text, user_mark,"
            + " id, username, password, email,  name, lastname, " +
            "birthdate, country_id, country_name,  \n" +
            "user_rating, isAdmin, isBanned FROM film JOIN (SELECT id, username, password, email, name, lastname, " +
            "birthdate, country_id, country_name,  user_rating, isAdmin, isBanned, film_rvw_id, review_text, " +
            "review_title, user_mark FROM (SELECT id, username," +
            " password, email, name, lastname, birthdate, country_id, " +
            "country_name, user_rating, isAdmin, isBanned FROM user JOIN country ON user.user_country = country_id) " +
            "AS country_user JOIN review WHERE id = user_rvw_id) AS film_review ON film_id = film_rvw_id " +
            "WHERE film_id = ?";

    /**
     * The Constant SQL_SELECT_USER_RATINGS_BY_REVIEW.
     */
    private final static String SQL_SELECT_USER_RATINGS_BY_REVIEW = "SELECT film_id, user_rvw_id, user_id, user_rating" +
            " FROM review_user_rating WHERE film_id = ? AND user_rvw_id = ?";

    /**
     * The Constant SQL_SELECT_REVIEWS_BY_USER_ID.
     */
    private final static String SQL_SELECT_REVIEWS_BY_USER_ID = "SELECT film_id, review_title, review_text, user_mark,"
            + " id, username, password, email,  name, lastname, " +
            "birthdate, country_id, country_name,  \n" +
            "user_rating, isAdmin, isBanned FROM film JOIN (SELECT id, username, password, email, name, lastname, " +
            "birthdate, country_id, country_name,  user_rating, isAdmin, isBanned, film_rvw_id, review_text, " +
            "review_title, user_mark FROM (SELECT id, username," +
            " password, email, name, lastname, birthdate, country_id, " +
            "country_name, user_rating, isAdmin, isBanned FROM user JOIN country ON user.user_country = country_id) " +
            "AS country_user JOIN review WHERE id = user_rvw_id) AS film_review ON film_id = film_rvw_id " +
            "WHERE id = ?";

    /**
     * The Constant SQL_INSERT_REVIEW.
     */
    private final static String SQL_INSERT_REVIEW = "INSERT INTO review (film_rvw_id, user_rvw_id, review_text, " +
            "user_mark, review_title) VALUES (?, ?, ?, ?, ?)";

    /**
     * The Constant SQL_INSERT_USER_RATING.
     */
    private final static String SQL_INSERT_USER_RATING = "INSERT INTO review_user_rating (film_id, user_rvw_id, user_id, "
            + "user_rating) VALUES (?, ?, ?, ?)";

    /**
     * The Constant SQL_DELETE_REVIEW.
     */
    private final static String SQL_DELETE_REVIEW = "DELETE FROM review WHERE film_rvw_id = ? AND user_rvw_id = ?";

    /**
     * The Constant LOGGER.
     */
    private final static Logger LOGGER = LogManager.getLogger();

    public List<Review> findReviewsByFilmId(int id) throws DAOException {
        return findById(id, SQL_SELECT_REVIEWS_BY_FILM_ID);
    }

    /**
     * Find reviews by user id.
     *
     * @param id the id
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Review> findReviewsByUserId(int id) throws DAOException {
        return findById(id, SQL_SELECT_REVIEWS_BY_USER_ID);
    }

    @Override
    public boolean insert(Review entity) throws DAOException {
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_REVIEW)) {
            preparedStatement.setInt(1, entity.getFilmId());
            preparedStatement.setInt(2, entity.getUser().getId());
            preparedStatement.setString(3, entity.getText());
            preparedStatement.setInt(4, entity.getUserMark());
            preparedStatement.setString(5, entity.getTitle());
            return preparedStatement.executeUpdate() != 0;
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
    }

    /**
     * Find by id.
     *
     * @param id           the id
     * @param sqlStatement the sql statement
     * @return the list
     * @throws DAOException the DAO exception
     */
    private List<Review> findById(int id, String sqlStatement) throws DAOException {
        List<Review> reviews = new ArrayList<>();
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UserDAO userDAO = new UserDAO();
                User user = userDAO.createUserFromResultSet(resultSet);
                int filmId = resultSet.getInt(SQLConstant.FILM_ID);
                String reviewText = resultSet.getString(SQLConstant.REVIEW_TEXT);
                String title = resultSet.getString(SQLConstant.REVIEW_TITLE);
                int userMark = resultSet.getInt(SQLConstant.USER_MARK);
                Review review = new Review(filmId, user, reviewText, title, userMark, null);
                reviews.add(review);
            }
            for (Review review : reviews) {
                review.setReviewUserRatings(findUserRatingsByReview(review, connection));
            }
            return (reviews.isEmpty()) ? null : reviews;
        } catch (SQLException | ConnectionException exc) {
            throw new DAOException(exc);
        }
    }

    /**
     * Insert user rating.
     *
     * @param filmId       the film id
     * @param userReviewId the user review id
     * @param userId       the user id
     * @param rating       the rating
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public boolean insertUserRating(int filmId, int userReviewId, int userId, int rating) throws DAOException {
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER_RATING)) {
            preparedStatement.setInt(1, filmId);
            preparedStatement.setInt(2, userReviewId);
            preparedStatement.setInt(3, userId);
            preparedStatement.setInt(4, rating);
            return preparedStatement.executeUpdate() != 0;
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
    }

    /**
     * Delete review.
     *
     * @param filmId the film id
     * @param userId the user id
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public boolean deleteReview(int filmId, int userId) throws DAOException {
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_REVIEW)) {
            preparedStatement.setInt(1, filmId);
            preparedStatement.setInt(2, userId);
            int result = preparedStatement.executeUpdate();
            if (result != 0) {
                LOGGER.log(Level.INFO, "Review was deleted");
                return true;
            } else return false;
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
    }

    /**
     * Find user ratings by review.
     *
     * @param review     the review
     * @param connection the connection
     * @return the list
     * @throws DAOException the DAO exception
     */
    private List<ReviewUserRating> findUserRatingsByReview(Review review, Connection connection) throws DAOException {
        List<ReviewUserRating> reviewUserRatings = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_RATINGS_BY_REVIEW)) {
            preparedStatement.setInt(1, review.getFilmId());
            preparedStatement.setInt(2, review.getUser().getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ReviewUserRating reviewUserRating = new ReviewUserRating();
                reviewUserRating.setRating(resultSet.getInt(SQLConstant.REVIEW_USER_RATING_USER_RATING));
                reviewUserRating.setUserId(resultSet.getInt(SQLConstant.USER_RATING_ID));
                reviewUserRatings.add(reviewUserRating);
            }
            return (reviewUserRatings.isEmpty()) ? null : reviewUserRatings;
        } catch (SQLException exc) {
            throw new DAOException(exc);
        }
    }
}
