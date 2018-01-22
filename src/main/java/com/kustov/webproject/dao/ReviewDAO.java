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

public class ReviewDAO{
    private final static String SQL_SELECT_REVIEWS_BY_FILM_ID = "SELECT film_id, id, review_text, review_title, " +
            "user_mark \n" +" FROM `filmratingdb`.film JOIN (SELECT film_rvw_id, id, review_text, user_mark, review_title" +
            " FROM `filmratingdb`.user JOIN `filmratingdb`.review\n" +
            " WHERE id = user_rvw_id) AS film_review\n" +
            "\tON film_id = film_rvw_id HAVING film_id = ?";
    private final static Logger LOGGER = LogManager.getLogger();

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
                int userId = resultSet.getInt("id");
                UserDAO userDAO = new UserDAO();
                User user = userDAO.findById(userId);
                String reviewText = resultSet.getString("review_text");
                String title = resultSet.getString("review_title");
                int userMark = resultSet.getInt("user_mark");
                Review review = new Review(id, user, reviewText, title, userMark);
                reviews.add(review);
            }
            return reviews;
        }catch (SQLException | ConnectionException exc){
            throw new DAOException(exc);
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connectionPool != null && connection != null) {
                    connectionPool.releaseConnection(connection);
                }
            } catch (SQLException exc) {
                LOGGER.log(Level.WARN, exc.getMessage());
            } catch (ConnectionException exc) {
                LOGGER.log(Level.ERROR, exc.getMessage());
            }
        }
    }
}
