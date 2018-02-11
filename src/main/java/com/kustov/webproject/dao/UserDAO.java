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

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * The Class UserDAO.
 */
public class UserDAO extends AbstractEntityDAO<Integer, User> {

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * The Constant SQL_SELECT_ALL_USERS.
     */
    private static final String SQL_SELECT_ALL_USERS = "SELECT id, username, password, email, name, lastname, " +
            "birthdate, country_id, country_name, user_rating, isAdmin, isBanned\n" +
            "    FROM user JOIN country\n" +
            "    ON user_country = country_id";

    /**
     * The Constant SQL_SELECT_USER_BY_USERNAME_AND_PASSWORD.
     */
    private static final String SQL_SELECT_USER_BY_USERNAME_AND_PASSWORD = "SELECT id, username, password, email, name, lastname, " +
            "birthdate, country_name, country_id, user_rating, isAdmin, isBanned\n" +
            "    FROM user JOIN country\n" +
            "    ON user_country = country_id WHERE username = ? AND password = ?";

    /**
     * The Constant SQL_SELECT_USER_BY_ID.
     */
    private static final String SQL_SELECT_USER_BY_ID = "SELECT id, username, password, email, name, lastname, " +
            "birthdate, country_name, country_id, user_rating, isAdmin, isBanned\n" +
            "    FROM user JOIN country\n" +
            "    ON user_country = country_id WHERE id = ?";

    /**
     * The Constant SQL_SELECT_USER_BY_USERNAME.
     */
    private static final String SQL_SELECT_USER_BY_USERNAME = "SELECT id, username, password, email, name, lastname, " +
            "birthdate, country_name, country_id, user_rating, isAdmin, isBanned\n" +
            "    FROM user JOIN country\n" +
            "    ON user_country = country_id WHERE username = ?";

    /**
     * The Constant SQL_SELECT_USER_BY_EMAIL.
     */
    private static final String SQL_SELECT_USER_BY_EMAIL = "SELECT id, username, password, email, name, lastname, " +
            "birthdate, country_name, country_id, user_rating, isAdmin, isBanned\n" +
            "    FROM user JOIN country\n" +
            "    ON user_country = country_id WHERE email = ?";

    /**
     * The Constant SQL_SELECT_ID_BY_USERNAME.
     */
    private static final String SQL_SELECT_ID_BY_USERNAME = "SELECT id, username" +
            "    FROM user" +
            "    WHERE username = ?";

    /**
     * The Constant SQL_INSERT_USER.
     */
    private static final String SQL_INSERT_USER = "INSERT INTO user (id, username, password, email, name, lastname, " +
            "birthdate, user_country, user_rating, isAdmin, isBanned)\n" +
            " VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, 0, 0, 0)";

    /**
     * The Constant SQL_UPDATE_RATING.
     */
    private static final String SQL_UPDATE_RATING = "UPDATE user SET user_rating = user_rating + ? WHERE id = ?";

    /**
     * The Constant SQL_UPDATE_BAN.
     */
    private static final String SQL_UPDATE_BAN = "UPDATE user SET isBanned = ? WHERE id = ?";

    @Override
    public List<User> findAll() throws DAOException {
        List<User> users = new ArrayList<>();
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS);
            while (resultSet.next()) {
                User user = createUserFromResultSet(resultSet);
                users.add(user);
            }
        } catch (SQLException | ConnectionException exc) {
            throw new DAOException(exc);
        }
        LOGGER.log(Level.INFO, "Find some users in database");
        return users;
    }

    @Override
    public User findById(Integer id) throws DAOException {
        User user = null;
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = createUserFromResultSet(resultSet);
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
        return user;
    }

    @Override
    public Integer insert(User user) throws DAOException {
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER)) {
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
        } catch (SQLException | ConnectionException exc) {
            throw new DAOException(exc);
        }
    }

    /**
     * Find user by username and password.
     *
     * @param username the username
     * @param password the password
     * @return the user
     * @throws DAOException the DAO exception
     */
    public User findUserByUsernameAndPassword(String username, String password) throws DAOException {
        User user = null;
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_USERNAME_AND_PASSWORD)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = createUserFromResultSet(resultSet);
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
        return user;
    }

    /**
     * Find user by email.
     *
     * @param email the email
     * @return the user
     * @throws DAOException the DAO exception
     */
    public User findUserByEmail(String email) throws DAOException {
        return findUserByProperty(email, SQL_SELECT_USER_BY_EMAIL);
    }

    /**
     * Find user by username.
     *
     * @param username the username
     * @return the user
     * @throws DAOException the DAO exception
     */
    public User findUserByUsername(String username) throws DAOException {
        return findUserByProperty(username, SQL_SELECT_USER_BY_USERNAME);
    }

    /**
     * Update rating.
     *
     * @param id     the id
     * @param rating the rating
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public boolean updateRating(int id, int rating) throws DAOException {
        try (ProxyConnection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_RATING)) {
            statement.setInt(1, rating);
            statement.setInt(2, id);
            return statement.executeUpdate() != 0;
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
    }

    /**
     * Update ban.
     *
     * @param id       the id
     * @param isBanned the is banned
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public boolean updateBan(int id, boolean isBanned) throws DAOException {
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_BAN)) {
            statement.setInt(2, id);
            int ban = isBanned ? 1 : 0;
            statement.setInt(1, ban);
            return statement.executeUpdate() != 0;
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
    }

    /**
     * Creates the user from result set.
     *
     * @param resultSet the result set
     * @return the user
     * @throws SQLException the SQL exception
     */
    User createUserFromResultSet(ResultSet resultSet) throws SQLException {
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
        if (resultSet.getBoolean(SQLConstant.USER_IS_ADMIN)) {
            user.setType(UserType.ADMIN);
        } else {
            user.setType(UserType.USER);
        }
        user.setBanned(resultSet.getBoolean(SQLConstant.USER_IS_BANNED));

        return user;
    }

    /**
     * Find user by property.
     *
     * @param property the property
     * @param query    the query
     * @return the user
     * @throws DAOException the DAO exception
     */
    private User findUserByProperty(String property, String query) throws DAOException {
        User user = null;
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, property);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = createUserFromResultSet(resultSet);
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
        return user;
    }

    /**
     * Find id by username.
     *
     * @param connection the connection
     * @param user       the user
     * @return the int
     * @throws DAOException the DAO exception
     */
    private int findIdByUsername(Connection connection, User user) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ID_BY_USERNAME)) {
            preparedStatement.setString(1, user.getUsername());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(SQLConstant.USER_ID);
        } catch (SQLException exc) {
            throw new DAOException(exc);
        }
    }
}

