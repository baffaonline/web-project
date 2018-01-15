package com.kustov.webproject.dao;

import com.kustov.webproject.exception.ConnectionException;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.pool.DBConnectionPool;
import com.kustov.webproject.pool.ProxyConnection;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CountryDAO extends AbstractFindDAO<String>{
    private final static String SQL_SELECT_ALL_COUNTRIES = "SELECT country_name" +
            "    FROM `filmratingdb`.country";
    private final static Logger LOGGER = LogManager.getLogger();

    @Override
    public List<String> findAll() throws DAOException {
        List<String> countries = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement = null;
        DBConnectionPool connectionPool = null;
        try{
            connectionPool = DBConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_COUNTRIES);
            while (resultSet.next()){
                String country = resultSet.getString("country_name");
                countries.add(country);
            }
        } catch (SQLException | ConnectionException exc){
            throw new DAOException(exc);
        } finally {
            close(statement, connectionPool, connection);
        }
        LOGGER.log(Level.INFO, "Find countries in database");
        return countries;
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
}
