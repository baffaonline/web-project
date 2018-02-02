package com.kustov.webproject.dao;

import com.kustov.webproject.entity.Review;
import com.kustov.webproject.entity.ReviewUserRating;
import com.kustov.webproject.entity.User;
import com.kustov.webproject.exception.ConnectionException;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.pool.DBConnectionPool;
import com.kustov.webproject.pool.ProxyConnection;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO extends AbstractReviewDAO {
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

    private final static String SQL_SELECT_USER_RATINGS_BY_REVIEW = "SELECT film_id, user_rvw_id, user_id, user_rating" +
            " FROM review_user_rating WHERE film_id = ? AND user_rvw_id = ?";

    private final static String SQL_SELECT_REVIEWS_BY_USER_ID = "SELECT id, username, password, email, name, lastname, " +
            " birthdate, country_id, country_name,  user_rating, isAdmin, isBanned, film_rvw_id, review_text, " +
            " review_title, user_mark FROM (SELECT id, username, " +
            " password, email, name, lastname, birthdate, country_id, " +
            " country_name, user_rating, isAdmin, isBanned FROM user JOIN country ON user.user_country = country_id) " +
            " AS country_user JOIN review ON id = user_rvw_id WHERE id = ?";

    private final static String SQL_INSERT_REVIEW = "INSERT INTO review (film_rvw_id, user_rvw_id, review_text, " +
            "user_mark, review_title) VALUES (?, ?, ?, ?, ?)";

    private final static String SQL_INSERT_USER_RATING = "INSERT INTO review_user_rating (film_id, user_rvw_id, user_id, "
            + "user_rating) VALUES (?, ?, ?, ?)";

    private final static String SQL_DELETE_REVIEW = "DELETE FROM review WHERE film_rvw_id = ? AND user_rvw_id = ?";

    private final static Logger LOGGER = LogManager.getLogger();

    public List<Review> findReviewsByFilmId(int id) throws DAOException {
        return findById(id, SQL_SELECT_REVIEWS_BY_FILM_ID);
    }

    public List<Review> findReviewsByUserId(int id) throws DAOException{
        return findById(id, SQL_SELECT_REVIEWS_BY_USER_ID);
    }

    @Override
    public boolean insert(Review entity) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        DBConnectionPool connectionPool = DBConnectionPool.getInstance();
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT_REVIEW);
            preparedStatement.setInt(1, entity.getFilmId());
            preparedStatement.setInt(2, entity.getUser().getId());
            preparedStatement.setString(3, entity.getText());
            preparedStatement.setInt(4, entity.getUserMark());
            preparedStatement.setString(5, entity.getTitle());
            return preparedStatement.executeUpdate() != 0;
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        } finally {
            close(preparedStatement, connectionPool, connection);
        }
    }

    private List<Review> findById(int id, String sqlStatement) throws DAOException{
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        DBConnectionPool connectionPool = DBConnectionPool.getInstance();
        List<Review> reviews = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UserDAO userDAO = new UserDAO();
                User user = new User();
                userDAO.setUserFromResultSet(resultSet, user);
                String reviewText = resultSet.getString("review_text");
                String title = resultSet.getString("review_title");
                int userMark = resultSet.getInt("user_mark");
                Review review = new Review(id, user, reviewText, title, userMark, null);
                reviews.add(review);
            }
            for (Review review : reviews) {
                review.setReviewUserRatings(findUserRatingsByReview(review));
            }
            return (reviews.isEmpty()) ? null : reviews;
        } catch (SQLException | ConnectionException exc) {
            throw new DAOException(exc);
        } finally {
            close(statement, connectionPool, connection);
        }
    }

    public boolean insertUserRating(int filmId, int userReviewId, int userId, int rating) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        DBConnectionPool connectionPool = DBConnectionPool.getInstance();
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT_USER_RATING);
            preparedStatement.setInt(1, filmId);
            preparedStatement.setInt(2, userReviewId);
            preparedStatement.setInt(3, userId);
            preparedStatement.setInt(4, rating);
            return preparedStatement.executeUpdate() != 0;
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        } finally {
            close(preparedStatement, connectionPool, connection);
        }
    }

    public boolean deleteReview(int filmId, int userId) throws DAOException{
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        DBConnectionPool connectionPool = DBConnectionPool.getInstance();
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_REVIEW);
            preparedStatement.setInt(1, filmId);
            preparedStatement.setInt(2, userId);
            int result =  preparedStatement.executeUpdate();
            if (result != 0){
                LOGGER.log(Level.INFO, "Review was deleted");
                return true;
            }
            else return false;
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        } finally {
            close(preparedStatement, connectionPool, connection);
        }
    }

    private List<ReviewUserRating> findUserRatingsByReview(Review review) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        DBConnectionPool connectionPool = DBConnectionPool.getInstance();
        List<ReviewUserRating> reviewUserRatings = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_USER_RATINGS_BY_REVIEW);
            preparedStatement.setInt(1, review.getFilmId());
            preparedStatement.setInt(2, review.getUser().getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ReviewUserRating reviewUserRating = new ReviewUserRating();
                reviewUserRating.setRating(resultSet.getInt("user_rating"));
                reviewUserRating.setUserId(resultSet.getInt("user_id"));
                reviewUserRatings.add(reviewUserRating);
            }
            return (reviewUserRatings.isEmpty()) ? null : reviewUserRatings;
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        } finally {
            close(preparedStatement, connectionPool, connection);
        }
    }
}
