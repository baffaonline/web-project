package com.kustov.webproject.dao;

import com.kustov.webproject.entity.Review;
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

public class ReviewDAO extends AbstractReviewDAO{
    private final static String SQL_SELECT_REVIEWS_BY_FILM_ID = "SELECT film_id, review_title, review_text, user_mark,"
            + "id, username, password, email,  name, lastname, birthdate, country_id, country_name,  \n" +
            "user_rating, isAdmin, isBanned FROM film JOIN (SELECT id, username, password, email, name, lastname, " +
            "birthdate, country_id, country_name,  user_rating, isAdmin, isBanned, film_rvw_id, review_text, " +
            "review_title, user_mark FROM (SELECT id, username, password, email, name, lastname, birthdate, country_id, " +
            "country_name, user_rating, isAdmin, isBanned FROM user JOIN country ON user.user_country = country_id) " +
            "AS country_id JOIN review WHERE id = user_rvw_id) AS film_review ON film_id = film_rvw_id " +
            "WHERE film_id = ?";
    private final static String SQL_INSERT_REVIEW = "INSERT into review (film_rvw_id, user_rvw_id, review_text, " +
            "user_mark, review_title) " +
            " VALUES (?, ?, ?, ?, ?)";

    public List<Review> findReviewsByFilmId(int id) throws DAOException{
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        DBConnectionPool connectionPool = DBConnectionPool.getInstance();
        List<Review> reviews = new ArrayList<>();
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_REVIEWS_BY_FILM_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                UserDAO userDAO = new UserDAO();
                User user = new User();
                userDAO.setUserFromResultSet(resultSet, user);
                String reviewText = resultSet.getString("review_text");
                String title = resultSet.getString("review_title");
                int userMark = resultSet.getInt("user_mark");
                Review review = new Review(id, user, reviewText, title, userMark);
                reviews.add(review);
            }
            return (reviews.isEmpty()) ? null : reviews;
        }catch (SQLException | ConnectionException exc){
            throw new DAOException(exc);
        }finally {
            close(statement, connectionPool, connection);
        }
    }

    @Override
    public boolean insert(Review entity) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        DBConnectionPool connectionPool = DBConnectionPool.getInstance();
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT_REVIEW);
            preparedStatement.setInt(1, entity.getFilmId());
            preparedStatement.setInt(2, entity.getUser().getId());
            preparedStatement.setString(3, entity.getText());
            preparedStatement.setInt(4, entity.getUserMark());
            preparedStatement.setString(5, entity.getTitle());
            return preparedStatement.executeUpdate() != 0;
        }catch (ConnectionException | SQLException exc){
            throw new DAOException(exc);
        }finally {
            close(preparedStatement, connectionPool, connection);
        }
    }
}
