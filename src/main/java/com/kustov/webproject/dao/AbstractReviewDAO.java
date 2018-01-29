package com.kustov.webproject.dao;

import com.kustov.webproject.entity.Review;
import com.kustov.webproject.exception.ConnectionException;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.pool.DBConnectionPool;
import com.kustov.webproject.pool.ProxyConnection;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class AbstractReviewDAO{
    private final static Logger LOGGER = LogManager.getLogger();

    public abstract boolean insert(Review entity) throws DAOException;

    public abstract List<Review> findReviewsByFilmId(int id) throws DAOException;

    public void close(Statement statement, DBConnectionPool connectionPool, ProxyConnection connection) {
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
