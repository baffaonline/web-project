package com.kustov.webproject.dao;

import com.kustov.webproject.entity.*;
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

public class FilmDAO extends AbstractEntityDAO<Integer, Film> {
    private final static String SQL_SELECT_FILMS = "SELECT film_id, film_title, country_id, " +
            "country_name, film_description, film_age_restriction, film_date_of_release, " +
            "film_poster_path FROM film JOIN country " +
            "WHERE film_country = country_id";

    private final static String SQL_SELECT_RATING = "SELECT film_id, flm.rating \n" +
            "    FROM film LEFT JOIN (SELECT review.film_rvw_id, AVG(review.user_mark) AS rating \n" +
            "    FROM review \n" +
            "    GROUP BY review.film_rvw_id) as flm\n" +
            "    ON film_id = flm.film_rvw_id HAVING film_id = ?";

    private final static String SQL_SELECT_FILM_BY_ID = "SELECT film_id, film_title, country_id, " +
            "country_name, film_description, film_age_restriction, film_date_of_release, " +
            "film_poster_path FROM film JOIN country " +
            "ON film_country = country_id WHERE film_id = ?";

    private final static String SQL_SELECT_FILMS_BY_ACTOR_ID = "SELECT actor_id, film_id, film_title, country_id, " +
            "country_name, film_description, film_age_restriction,\n" +
            "film_date_of_release, film_poster_path FROM actor LEFT JOIN (SELECT  actor_flm_id, film_id, film_title,\n" +
            "country_id, country_name, film_description, film_age_restriction, film_date_of_release, \n" +
            "film_poster_path FROM (SELECT film_id, film_title, country_id, country_name, film_description, \n" +
            "film_age_restriction, film_date_of_release, film_poster_path FROM film LEFT JOIN country\n" +
            "ON film_country = country_id) AS film_count LEFT JOIN film_actor ON film_id = film_act_id) \n" +
            "AS film_act ON actor_id = actor_flm_id WHERE actor_id = ?";

    private final static String SQL_SELECT_ID_BY_FILM = "SELECT film_id, film_title," +
            " film_description FROM film WHERE film_title = ? AND film_description = ?";

    private final static String SQL_INSERT_FILM = "INSERT into film (film_id, film_title, film_country," +
            "film_description, film_age_restriction, film_date_of_release, " +
            "film_poster_path) VALUES (NULL, ?, ?, ?, ?, ?, ?)";

    private final static String SQL_INSERT_ACTORS_TO_FILM = "INSERT into film_actor (film_act_id, actor_flm_id) " +
            "VALUES (?, ?)";

    private final static String SQL_INSERT_GENRES_TO_FILM = "INSERT into film_genre (film_gnr_id, genre_flm_id) " +
            "VALUES (?, ?)";

    private final static Logger LOGGER = LogManager.getLogger();

    @Override
    public Film findById(Integer id) throws DAOException{
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        DBConnectionPool connectionPool = DBConnectionPool.getInstance();
        Film film = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_FILM_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                film = createFilmFromResultSet(resultSet);
                film.setRating(findFilmRatingById(film.getId(), connection));
            }
            return film;
        }catch (SQLException | ConnectionException exc){
            throw new DAOException(exc);
        }finally {
            close(statement, connectionPool, connection);
        }
    }

    @Override
    public Integer insert(Film entity) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        DBConnectionPool connectionPool = DBConnectionPool.getInstance();
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_INSERT_FILM);
            statement.setString(1, entity.getTitle());
            statement.setInt(2, entity.getCountry().getId());
            statement.setString(3, entity.getDescription());
            statement.setInt(4, entity.getAgeRestriction());
            statement.setDate(5, java.sql.Date.valueOf(entity.getReleaseDate()));
            statement.setString(6, entity.getPosterPath());
            statement.executeUpdate();
            LOGGER.log(Level.INFO, "Add new film to database");
            return findIdByFilm(entity, connection);
        }catch (ConnectionException | SQLException exc){
            throw new DAOException(exc);
        }finally {
            close(statement, connectionPool, connection);
        }
    }

    @Override
    public List<Film> findAll() throws DAOException {
        ProxyConnection connection = null;
        Statement statement = null;
        DBConnectionPool connectionPool = DBConnectionPool.getInstance();
        List<Film> films = new ArrayList<>();
        try{
            connection = connectionPool.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_FILMS);
            while (resultSet.next()){
                Film film = createFilmFromResultSet(resultSet);
                films.add(film);
            }
            for (Film film : films){
                film.setRating(findFilmRatingById(film.getId(), connection));
            }
            return films;
        }catch (SQLException | ConnectionException exc){
            throw new DAOException(exc);
        } finally {
            close(statement, connectionPool, connection);
        }
    }

    public boolean insertActorsToFilm(int filmId, int actorsId[]) throws DAOException{
        return insertInformationToFilm(filmId, actorsId, SQL_INSERT_ACTORS_TO_FILM);
    }

    public boolean insertGenresToFilm(int filmId, int genresId[]) throws DAOException{
        return insertInformationToFilm(filmId, genresId, SQL_INSERT_GENRES_TO_FILM);
    }

    private boolean insertInformationToFilm(int filmId, int infoId[], String sql) throws DAOException{
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        DBConnectionPool connectionPool = DBConnectionPool.getInstance();
        try{
            connection = connectionPool.getConnection();
            boolean isAllInserted = true;
            for (int id : infoId) {
                statement = connection.prepareStatement(sql);
                statement.setInt(1, filmId);
                statement.setInt(2, id);
                if (statement.executeUpdate() == 0) {
                    isAllInserted = false;
                }
            }
            return isAllInserted;
        }catch (SQLException | ConnectionException exc){
            throw new DAOException(exc);
        } finally {
            close(statement, connectionPool, connection);
        }
    }

    public List<Film> findFilmsByActorId(int id) throws DAOException{
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        DBConnectionPool connectionPool = DBConnectionPool.getInstance();
        List<Film> films = new ArrayList<>();
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_FILMS_BY_ACTOR_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Film film = createFilmFromResultSet(resultSet);
                films.add(film);
            }
            for (Film film : films){
                film.setRating(findFilmRatingById(film.getId(), connection));
            }
            return (films.isEmpty()) ? null : films;
        }catch (ConnectionException | SQLException exc){
            throw new DAOException(exc);
        }finally {
            close(statement, connectionPool, connection);
        }
    }

    private Film createFilmFromResultSet(ResultSet resultSet) throws SQLException{
        Film film;
        int id = resultSet.getInt("film_id");
        String title = resultSet.getString("film_title");
        int countryId = resultSet.getInt("country_id");
        String countryName = resultSet.getString("country_name");
        Country country = new Country(countryId, countryName);
        String description = resultSet.getString("film_description");
        int ageRestriction = resultSet.getInt("film_age_restriction");
        LocalDate localDate = resultSet.getDate("film_date_of_release").toLocalDate();
        String posterPath = resultSet.getString("film_poster_path");
        film = new Film(id, title, country, description, ageRestriction, localDate, posterPath, 0,
                null, null, null);
        return film;
    }

    private double findFilmRatingById(int id, ProxyConnection connection) throws DAOException{
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SQL_SELECT_RATING);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getDouble("flm.rating");
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

    private int findIdByFilm(Film film, ProxyConnection connection) throws DAOException{
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SQL_SELECT_ID_BY_FILM);
            statement.setString(1, film.getTitle());
            statement.setString(2, film.getDescription());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("film_id");
            }
            return 0;
        }catch (SQLException exc){
            throw new DAOException(exc);
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            }catch (SQLException exc){
                LOGGER.log(Level.ERROR, exc.getMessage());
            }
        }
    }
}
