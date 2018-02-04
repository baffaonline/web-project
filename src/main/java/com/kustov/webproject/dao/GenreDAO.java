package com.kustov.webproject.dao;

import com.kustov.webproject.entity.Genre;
import com.kustov.webproject.exception.ConnectionException;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.pool.DBConnectionPool;
import com.kustov.webproject.pool.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO extends AbstractEntityDAO<Integer, Genre> {
    private final static String SQL_SELECT_GENRES_BY_FILM_ID = "SELECT film_id, genre_id, genre_name " +
            " FROM film JOIN (SELECT film_gnr_id, genre_id, genre_name " +
            " FROM genre JOIN film_genre \n" +
            " WHERE genre_id = genre_flm_id) AS genre_film\n" +
            " ON film_id = film_gnr_id WHERE film_id = ?";

    private final static String SQL_SELECT_ALL_GENRES = "SELECT genre_id, genre_name FROM genre";

    @Override
    public Genre findById(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Genre> findAll() throws DAOException{
        ProxyConnection connection = null;
        Statement statement = null;
        DBConnectionPool connectionPool = DBConnectionPool.getInstance();
        List<Genre> genres = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_GENRES);
            while (resultSet.next()) {
                Genre genre = createGenreFromResultSet(resultSet);
                genres.add(genre);
            }
            return (genres.isEmpty()) ? null : genres;
        }catch (ConnectionException | SQLException exc){
            throw new DAOException(exc);
        }finally {
            close(statement, connectionPool, connection);
        }
    }

    public List<Genre> findGenresByFilmId(int id) throws DAOException{
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        DBConnectionPool connectionPool = DBConnectionPool.getInstance();
        List<Genre> genres = new ArrayList<>();
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_GENRES_BY_FILM_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Genre genre = createGenreFromResultSet(resultSet);
                genres.add(genre);
            }
            return (genres.isEmpty()) ? null : genres;
        }catch (SQLException | ConnectionException exc){
            throw new DAOException(exc);
        }finally {
            close(statement, connectionPool, connection);
        }
    }

    private Genre createGenreFromResultSet(ResultSet resultSet) throws SQLException{
        int genreId = resultSet.getInt("genre_id");
        String name = resultSet.getString("genre_name");
        return new Genre(genreId, name);
    }
}
