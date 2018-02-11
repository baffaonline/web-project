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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractEntityDAO<Integer, User> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SQL_SELECT_ALL_USERS = "SELECT id, username, password, email, name, lastname, " +
            "birthdate, country_id, country_name, user_rating, isAdmin, isBanned\n" +
            "    FROM user JOIN country\n" +
            "    ON user_country = country_id";

    private static final String SQL_SELECT_USER_BY_USERNAME_AND_PASSWORD = "SELECT id, username, password, email, name, lastname, " +
            "birthdate, country_name, country_id, user_rating, isAdmin, isBanned\n" +
            "    FROM user JOIN country\n" +
            "    ON user_country = country_id WHERE username = ? AND password = ?";

    private static final String SQL_SELECT_USER_BY_ID = "SELECT id, username, password, email, name, lastname, " +
            "birthdate, country_name, country_id, user_rating, isAdmin, isBanned\n" +
            "    FROM user JOIN country\n" +
            "    ON user_country = country_id WHERE id = ?";

    private static final String SQL_SELECT_USER_BY_USERNAME = "SELECT id, username, password, email, name, lastname, " +
            "birthdate, country_name, country_id, user_rating, isAdmin, isBanned\n" +
            "    FROM user JOIN country\n" +
            "    ON user_country = country_id WHERE username = ?";

    private static final String SQL_SELECT_USER_BY_EMAIL = "SELECT id, username, password, email, name, lastname, " +
            "birthdate, country_name, country_id, user_rating, isAdmin, isBanned\n" +
            "    FROM user JOIN country\n" +
            "    ON user_country = country_id WHERE email = ?";

    private static final String SQL_SELECT_ID_BY_USERNAME = "SELECT id, username" +
            "    FROM user" +
            "    WHERE username = ?";

    private static final String SQL_INSERT_USER = "INSERT into user (id, username, password, email, name, lastname, " +
            "birthdate, user_country, user_rating, isAdmin, isBanned)\n" +
            " VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, 0, 0, 0)";

    private static final String SQL_UPDATE_RATING = "UPDATE user SET user_rating = user_rating + ? WHERE id = ?";

    private static final String SQL_UPDATE_BAN="UPDATE user SET isBanned = ? WHERE id = ?";

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
                User user = createUserFromResultSet(resultSet);
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

    @Override
    public User findById(Integer id) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        DBConnectionPool connectionPool = null;
        User user = null;
        try {
            connectionPool = DBConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_USER_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = createUserFromResultSet(resultSet);
            }
        } catch (ConnectionException | SQLException exc){
            throw new DAOException(exc);
        } finally {
            close(statement, connectionPool, connection);
        }
        return user;
    }

    @Override
    public Integer insert(User user) throws DAOException{
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
            statement.setString(5, user.getSurname());
            java.sql.Date sqlDate = java.sql.Date.valueOf(user.getBirthday());
            statement.setDate(6, sqlDate);
            statement.setInt(7, user.getCountry().getId());
            statement.executeUpdate();
            LOGGER.log(Level.INFO, "Add new user to database");
            return findIdByUsername(connection, user);
        }catch (SQLException | ConnectionException exc){
            throw new DAOException(exc);
        } finally {
            close(statement, connectionPool, connection);
        }
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
                user = createUserFromResultSet(resultSet);
            }
        } catch (ConnectionException | SQLException exc){
            throw new DAOException(exc);
        } finally {
            close(statement, connectionPool, connection);
        }
        return user;
    }

    public User findUserByEmail(String email) throws DAOException{
        return findUserByProperty(email, SQL_SELECT_USER_BY_EMAIL);
    }

    public User findUserByUsername(String username) throws DAOException {
        return findUserByProperty(username, SQL_SELECT_USER_BY_USERNAME);
    }

    public boolean updateRating(int id, int rating) throws DAOException{
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        DBConnectionPool connectionPool = DBConnectionPool.getInstance();
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_RATING);
            statement.setInt(1, rating);
            statement.setInt(2, id);
            return statement.executeUpdate() != 0;
        }catch (ConnectionException | SQLException exc){
            throw new DAOException(exc);
        }finally {
            close(statement, connectionPool, connection);
        }
    }

    public boolean updateBan(int id, boolean isBanned) throws DAOException{
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        DBConnectionPool connectionPool = DBConnectionPool.getInstance();
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_BAN);
            statement.setInt(2, id);
            int ban = isBanned ? 1 : 0;
            statement.setInt(1, ban);
            return statement.executeUpdate() != 0;
        }catch (ConnectionException | SQLException exc){
            throw new DAOException(exc);
        }finally {
            close(statement, connectionPool, connection);
        }
    }

    User createUserFromResultSet(ResultSet resultSet) throws SQLException{
        User user = new User();
        user.setId(resultSet.getInt(SQLConstant.USER_ID));
        user.setUsername(resultSet.getString(SQLConstant.USERNAME));
        user.setPassword(resultSet.getString(SQLConstant.PASSWORD));
        user.setEmail(resultSet.getString(SQLConstant.EMAIL));
        user.setName(resultSet.getString(SQLConstant.USER_NAME));
        user.setSurname(resultSet.getString(SQLConstant.USER_SURNAME));
        LocalDate localDate = resultSet.getDate(SQLConstant.USER_BIRTHDAY).toLocalDate();
        user.setBirthday(localDate);
        user.setCountry(new Country(resultSet.getInt(SQLConstant.COUNTRY_ID),
                resultSet.getString(SQLConstant.COUNTRY_NAME)));
        user.setRating(resultSet.getInt(SQLConstant.USER_RATING));
        if (resultSet.getBoolean(SQLConstant.USER_IS_ADMIN)){
            user.setType(UserType.ADMIN);
        }
        else {
            user.setType(UserType.USER);
        }
        user.setBanned(resultSet.getBoolean(SQLConstant.USER_IS_BANNED));
        return user;
    }

    private User findUserByProperty(String property, String query) throws DAOException{
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        DBConnectionPool connectionPool = null;
        User user = null;
        try{
            connectionPool = DBConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, property);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = createUserFromResultSet(resultSet);
            }
        }catch (ConnectionException | SQLException exc){
            throw new DAOException(exc);
        }finally {
            close(preparedStatement, connectionPool, connection);
        }
        return user;
    }

    private int findIdByUsername(ProxyConnection connection, User user) throws DAOException{
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(SQL_SELECT_ID_BY_USERNAME);
            preparedStatement.setString(1, user.getUsername());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(SQLConstant.USER_ID);
        }catch (SQLException exc){
            throw new DAOException(exc);
        }finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }catch (SQLException exc){
                LOGGER.log(Level.ERROR, exc.getMessage());
            }
        }
    }
}

