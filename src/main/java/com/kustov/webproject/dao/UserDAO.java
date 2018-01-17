package com.kustov.webproject.dao;

import com.kustov.webproject.entity.Country;
import com.kustov.webproject.entity.User;
import com.kustov.webproject.entity.UserType;
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
            "birthdate, country_name, country_id, user_rating, isAdmin, isBanned\n" +
            "    FROM `filmratingdb`.user JOIN `filmratingdb`.country\n" +
            "    WHERE user_country = country_id AND username = ? And password = ?";
    private static final String SQL_INSERT_USER = "INSERT into `filmratingdb`.user (id, username, password, email, name, lastname, " +
            "birthdate, user_country, user_rating, isAdmin, isBanned)\n" +
            " VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, 0, 0, 0)";

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

    public void insert(User user) throws DAOException{
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        DBConnectionPool connectionPool = null;
        try{
            connectionPool = DBConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_INSERT_USER);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getName());
            statement.setString(5, user.getLastname());
            java.sql.Date sqlDate = new java.sql.Date(user.getBirthday().getTime());
            statement.setDate(6, sqlDate);
            statement.setInt(7, user.getCountry().getId());
            statement.executeUpdate();
        }catch (SQLException | ConnectionException exc){
            throw new DAOException(exc);
        } finally {
            close(statement, connectionPool, connection);
        }
        LOGGER.log(Level.INFO, "Add new user to database");
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

    private void setUserFromResultSet(ResultSet resultSet, User user) throws SQLException{
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setEmail(resultSet.getString("email"));
        user.setName(resultSet.getString("name"));
        user.setLastname(resultSet.getString("lastname"));
        user.setBirthday(resultSet.getDate("birthdate"));
        user.setCountry(new Country(resultSet.getInt("country_id"),
                resultSet.getString("country_name")));
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
