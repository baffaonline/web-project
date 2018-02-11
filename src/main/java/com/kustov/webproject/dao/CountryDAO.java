package com.kustov.webproject.dao;

import com.kustov.webproject.entity.Country;
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

public class CountryDAO extends AbstractEntityDAO<Integer, Country> {
    private final static String SQL_SELECT_ALL_COUNTRIES = "SELECT country_name, country_id" +
            "    FROM country";

    private final static String SQL_SELECT_COUNTRY_BY_ID = "SELECT country_name, country_id " +
            "FROM country WHERE country_id = ?";

    private final static String SQL_INSERT_COUNTRY = "INSERT into country (country_id, country_name) VALUES " +
            "(NULL, ?)";

    private final static String SQL_SELECT_ID_BY_COUNTRY_NAME = "SELECT country_name, country_id " +
            "FROM country WHERE country_name = ?";

    private final static Logger LOGGER = LogManager.getLogger();

    @Override
    public Integer insert(Country entity) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        DBConnectionPool connectionPool = DBConnectionPool.getInstance();
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_INSERT_COUNTRY);
            statement.setString(1, entity.getName());
            return findIdByCountryName(entity.getName(), connection);
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        } finally {
            close(statement, connectionPool, connection);
        }
    }

    @Override
    public List<Country> findAll() throws DAOException {
        List<Country> countries = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement = null;
        DBConnectionPool connectionPool = null;
        try{
            connectionPool = DBConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_COUNTRIES);
            while (resultSet.next()){
                int id = resultSet.getInt(SQLConstant.COUNTRY_ID);
                String country = resultSet.getString(SQLConstant.COUNTRY_NAME);
                countries.add(new Country(id, country));
            }
        } catch (SQLException | ConnectionException exc){
            throw new DAOException(exc);
        } finally {
            close(statement, connectionPool, connection);
        }
        LOGGER.log(Level.INFO, "Find countries in database");
        return countries;
    }

    @Override
    public Country findById(Integer id) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        DBConnectionPool connectionPool = DBConnectionPool.getInstance();
        Country country = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_COUNTRY_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString(SQLConstant.COUNTRY_NAME);
                country = new Country(id, name);
            }
            return country;
        }catch (SQLException | ConnectionException exc){
            throw new DAOException(exc);
        }finally {
            close(statement, connectionPool, connection);
        }
    }

    private int findIdByCountryName(String name, ProxyConnection connection)
            throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ID_BY_COUNTRY_NAME);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.getInt(SQLConstant.COUNTRY_ID);
        } catch (SQLException exc) {
            throw new DAOException(exc);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException exc) {
                LOGGER.log(Level.ERROR, exc.getMessage());
            }
        }
    }
}
