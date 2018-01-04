package com.kustov.tagexample.dao;

import com.kustov.tagexample.entity.User;
import com.kustov.tagexample.entity.UserType;
import com.kustov.tagexample.exception.ConnectionException;
import com.kustov.tagexample.exception.DAOException;
import com.kustov.tagexample.pool.DBConnectionPool;
import com.kustov.tagexample.pool.ProxyConnection;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractDAO<Integer, User>{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SQL_SELECT_ALL_USERS = "SELECT id, username, password, email, name, lastname, " +
            "birthdate, country_name, user_rating, isAdmin, isBanned\n" +
            "    FROM `filmratingdb`.user JOIN `filmratingdb`.country\n" +
            "    WHERE user_country = country_id";
    private static final String SQL_SELECT_USER_BY_USERNAME_AND_PASSWORD = "SELECT id, username, password, email, name, lastname, " +
            "birthdate, country_name, user_rating, isAdmin, isBanned\n" +
            "    FROM `filmratingdb`.user JOIN `filmratingdb`.country\n" +
            "    WHERE user_country = country_id AND username = ? And password = ?";

    @Override
    public List<User> findAll() throws DAOException{
        List<User> users = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement = null;
        DBConnectionPool connectionPool = null;
        try{
            connectionPool = DBConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS);
            while (resultSet.next()){
                User user = new User();
                setUserFromResultSet(resultSet, user);
                users.add(user);
            }
        } catch (SQLException | ConnectionException exc){
            throw new DAOException(exc);
        } finally {
            close(statement, connectionPool, connection);
        }
        LOGGER.log(Level.INFO, "Find some users in database");
        return users;
    }

    public User findUserByUsernameAndPassword(String username, String password) throws DAOException{
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        DBConnectionPool connectionPool = null;
        User user = null;
        try {
            connectionPool = DBConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_USER_BY_USERNAME_AND_PASSWORD);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                setUserFromResultSet(resultSet, user);
            }
        } catch (ConnectionException | SQLException exc){
            throw new DAOException(exc);
        } finally {
            close(statement, connectionPool, connection);
        }
        return user;
    }

    private void close(Statement statement, DBConnectionPool connectionPool,
                       ProxyConnection connection){
        try {
            if(statement != null) {
                statement.close();
            }
            if (connectionPool != null && connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }catch (SQLException exc){
            LOGGER.log(Level.WARN, exc.getMessage());
        }catch (ConnectionException exc){
            LOGGER.log(Level.ERROR, exc.getStackTrace());
        }
    }

    private void setUserFromResultSet(ResultSet resultSet, User user) throws SQLException{
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setEmail(resultSet.getString("email"));
        user.setName(resultSet.getString("name"));
        user.setLastname(resultSet.getString("lastname"));
        user.setBirthday(resultSet.getDate("birthdate"));
        user.setCountry(resultSet.getString("country_name"));
        user.setRating(resultSet.getInt("user_rating"));
        if (resultSet.getBoolean("isAdmin")){
            user.setType(UserType.ADMIN);
        }
        else {
            user.setType(UserType.USER);
        }
        user.setBanned(resultSet.getBoolean("isBanned"));
    }
}

