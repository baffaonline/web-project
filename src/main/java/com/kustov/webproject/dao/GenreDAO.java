package com.kustov.webproject.dao;

import com.kustov.webproject.entity.Genre;
import com.kustov.webproject.exception.ConnectionException;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.pool.DBConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The Class GenreDAO.
 */
public class GenreDAO extends AbstractEntityDAO<Integer, Genre> {

    /**
     * The Constant SQL_SELECT_GENRES_BY_FILM_ID.
     */
    private final static String SQL_SELECT_GENRES_BY_FILM_ID = "SELECT film_id, genre_id, genre_name " +
            " FROM film JOIN (SELECT film_gnr_id, genre_id, genre_name " +
            " FROM genre JOIN film_genre \n" +
            " WHERE genre_id = genre_flm_id) AS genre_film\n" +
            " ON film_id = film_gnr_id WHERE film_id = ?";

    /**
     * The Constant SQL_SELECT_ALL_GENRES.
     */
    private final static String SQL_SELECT_ALL_GENRES = "SELECT genre_id, genre_name FROM genre";

    /**
     * The Constant SQL_INSERT_GENRE.
     */
    private final static String SQL_INSERT_GENRE = "INSERT INTO genre (genre_id, genre_name) VALUES " +
            "(NULL, ?)";

    /**
     * The Constant SQL_SELECT_ID_BY_NAME.
     */
    private final static String SQL_SELECT_ID_BY_NAME = "SELECT genre_id, genre_name FROM genre WHERE " +
            "genre_name = ?";

    @Override
    public Integer insert(Genre entity) throws DAOException {
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_GENRE)) {
            statement.setString(1, entity.getName());
            return findIdByGenreName(entity.getName(), connection);
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
    }


    @Override
    public Genre findById(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Genre> findAll() throws DAOException {
        List<Genre> genres = new ArrayList<>();
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_GENRES);
            while (resultSet.next()) {
                Genre genre = createGenreFromResultSet(resultSet);
                genres.add(genre);
            }
            return (genres.isEmpty()) ? null : genres;
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
    }

    /**
     * Find genres by film id.
     *
     * @param id the id
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Genre> findGenresByFilmId(int id) throws DAOException {
        List<Genre> genres = new ArrayList<>();
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_GENRES_BY_FILM_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Genre genre = createGenreFromResultSet(resultSet);
                genres.add(genre);
            }
            return (genres.isEmpty()) ? null : genres;
        } catch (SQLException | ConnectionException exc) {
            throw new DAOException(exc);
        }
    }

    /**
     * Creates the genre from result set.
     *
     * @param resultSet the result set
     * @return the genre
     * @throws SQLException the SQL exception
     */
    private Genre createGenreFromResultSet(ResultSet resultSet) throws SQLException {
        int genreId = resultSet.getInt(SQLConstant.GENRE_ID);
        String name = resultSet.getString(SQLConstant.GENRE_NAME);
        return new Genre(genreId, name);
    }

    /**
     * Find id by genre name.
     *
     * @param name       the name
     * @param connection the connection
     * @return the int
     * @throws DAOException the DAO exception
     */
    private int findIdByGenreName(String name, Connection connection)
            throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ID_BY_NAME)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.getInt(SQLConstant.ACTOR_ID);
        } catch (SQLException exc) {
            throw new DAOException(exc);
        }
    }
}
