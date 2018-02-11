package com.kustov.webproject.dao;

import com.kustov.webproject.entity.Country;
import com.kustov.webproject.exception.ConnectionException;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.pool.DBConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The Class CountryDAO.
 */
public class CountryDAO extends AbstractEntityDAO<Integer, Country> {

    /**
     * The Constant SQL_SELECT_ALL_COUNTRIES.
     */
    private final static String SQL_SELECT_ALL_COUNTRIES = "SELECT country_name, country_id" +
            "    FROM country";

    /**
     * The Constant SQL_SELECT_COUNTRY_BY_ID.
     */
    private final static String SQL_SELECT_COUNTRY_BY_ID = "SELECT country_name, country_id " +
            "FROM country WHERE country_id = ?";

    /**
     * The Constant SQL_INSERT_COUNTRY.
     */
    private final static String SQL_INSERT_COUNTRY = "INSERT INTO country (country_id, country_name) VALUES " +
            "(NULL, ?)";

    /**
     * The Constant SQL_SELECT_ID_BY_COUNTRY_NAME.
     */
    private final static String SQL_SELECT_ID_BY_COUNTRY_NAME = "SELECT country_name, country_id " +
            "FROM country WHERE country_name = ?";

    /**
     * The Constant LOGGER.
     */
    private final static Logger LOGGER = LogManager.getLogger();

    @Override
    public Integer insert(Country entity) throws DAOException {
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_COUNTRY)) {
            statement.setString(1, entity.getName());
            return findIdByCountryName(entity.getName(), connection);
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
    }

    @Override
    public List<Country> findAll() throws DAOException {
        List<Country> countries = new ArrayList<>();
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_COUNTRIES);
            while (resultSet.next()) {
                int id = resultSet.getInt(SQLConstant.COUNTRY_ID);
                String country = resultSet.getString(SQLConstant.COUNTRY_NAME);
                countries.add(new Country(id, country));
            }
        } catch (SQLException | ConnectionException exc) {
            throw new DAOException(exc);
        }
        LOGGER.log(Level.INFO, "Find countries in database");
        return countries;
    }


    @Override
    public Country findById(Integer id) throws DAOException {
        Country country = null;
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_COUNTRY_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString(SQLConstant.COUNTRY_NAME);
                country = new Country(id, name);
            }
            return country;
        } catch (SQLException | ConnectionException exc) {
            throw new DAOException(exc);
        }
    }

    /**
     * Find id by country name.
     *
     * @param name       the name
     * @param connection the connection
     * @return the int
     * @throws DAOException the DAO exception
     */
    private int findIdByCountryName(String name, Connection connection)
            throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ID_BY_COUNTRY_NAME)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.getInt(SQLConstant.COUNTRY_ID);
        } catch (SQLException exc) {
            throw new DAOException(exc);
        }
    }
}
