package com.kustov.webproject.dao;

import com.kustov.webproject.entity.Country;
import com.kustov.webproject.entity.Film;
import com.kustov.webproject.exception.ConnectionException;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.pool.DBConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * The Class FilmDAO.
 */

public class FilmDAO extends AbstractEntityDAO<Integer, Film> {

    /**
     * The Constant SQL_SELECT_FILMS.
     */
    private final static String SQL_SELECT_FILMS = "SELECT film_id, film_title, country_id, " +
            "country_name, film_description, film_age_restriction, film_date_of_release, " +
            "film_poster_path FROM film JOIN country " +
            "WHERE film_country = country_id";

    /**
     * The Constant SQL_SELECT_RATING.
     */
    private final static String SQL_SELECT_RATING = "SELECT film_id, flm.rating \n" +
            "    FROM film LEFT JOIN (SELECT review.film_rvw_id, AVG(review.user_mark) AS rating \n" +
            "    FROM review \n" +
            "    GROUP BY review.film_rvw_id) AS flm\n" +
            "    ON film_id = flm.film_rvw_id HAVING film_id = ?";

    /**
     * The Constant SQL_SELECT_FILM_BY_ID.
     */
    private final static String SQL_SELECT_FILM_BY_ID = "SELECT film_id, film_title, country_id, " +
            "country_name, film_description, film_age_restriction, film_date_of_release, " +
            "film_poster_path FROM film JOIN country " +
            "ON film_country = country_id WHERE film_id = ?";

    /**
     * The Constant SQL_SELECT_FILMS_BY_ACTOR_ID.
     */
    private final static String SQL_SELECT_FILMS_BY_ACTOR_ID = "SELECT actor_id, film_id, film_title, country_id, " +
            "country_name, film_description, film_age_restriction,\n" +
            "film_date_of_release, film_poster_path FROM actor LEFT JOIN (SELECT  actor_flm_id, film_id, film_title,\n" +
            "country_id, country_name, film_description, film_age_restriction, film_date_of_release, \n" +
            "film_poster_path FROM (SELECT film_id, film_title, country_id, country_name, film_description, \n" +
            "film_age_restriction, film_date_of_release, film_poster_path FROM film LEFT JOIN country\n" +
            "ON film_country = country_id) AS film_count LEFT JOIN film_actor ON film_id = film_act_id) \n" +
            "AS film_act ON actor_id = actor_flm_id WHERE actor_id = ?";

    /**
     * The Constant SQL_SELECT_ID_BY_FILM.
     */
    private final static String SQL_SELECT_ID_BY_FILM = "SELECT film_id, film_title," +
            " film_description FRO" +
            "M film WHERE film_title = ? AND film_description = ?";

    /**
     * The Constant SQL_INSERT_FILM.
     */
    private final static String SQL_INSERT_FILM = "INSERT INTO film (film_id, film_title, film_country," +
            "film_description, film_age_restriction, film_date_of_release, " +
            "film_poster_path) VALUES (NULL, ?, ?, ?, ?, ?, ?)";

    /**
     * The Constant SQL_INSERT_ACTORS_TO_FILM.
     */
    private final static String SQL_INSERT_ACTORS_TO_FILM = "INSERT into film_actor (film_act_id, actor_flm_id) " +
            "VALUES (?, ?)";

    /**
     * The Constant SQL_INSERT_GENRES_TO_FILM.
     */
    private final static String SQL_INSERT_GENRES_TO_FILM = "INSERT into film_genre (film_gnr_id, genre_flm_id) " +
            "VALUES (?, ?)";

    /**
     * The Constant SQL_UPDATE_FILM.
     */
    private final static String SQL_UPDATE_FILM = "UPDATE film SET film_title = ?, film_country = ?, " +
            "film_description = ?, film_age_restriction = ?, film_date_of_release = ?, " +
            "film_poster_path = ? WHERE film_id = ?";

    /**
     * The Constant SQL_DELETE_FILM.
     */
    private final static String SQL_DELETE_FILM = "DELETE FROM film WHERE film_id = ?";

    /**
     * The Constant SQL_DELETE_GENRES_FROM_FILM.
     */
    private final static String SQL_DELETE_GENRES_FROM_FILM = "DELETE FROM film_genre WHERE film_gnr_id = ? " +
            "AND genre_flm_id = ?";

    /**
     * The Constant SQL_DELETE_ACTORS_FROM_FILM.
     */
    private final static String SQL_DELETE_ACTORS_FROM_FILM = "DELETE FROM film_actor WHERE film_act_id = ? " +
            "AND actor_flm_id = ?";

    /**
     * The Constant LOGGER.
     */
    private final static Logger LOGGER = LogManager.getLogger();



    @Override
    public Film findById(Integer id) throws DAOException {
        Film film = null;
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_FILM_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                film = createFilmFromResultSet(resultSet);
                film.setRating(findFilmRatingById(film.getId(), connection));
            }
            return film;
        } catch (SQLException | ConnectionException exc) {
            throw new DAOException(exc);
        }
    }

    @Override
    public Integer insert(Film entity) throws DAOException {
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_FILM)) {
            statement.setString(1, entity.getTitle());
            statement.setInt(2, entity.getCountry().getId());
            statement.setString(3, entity.getDescription());
            statement.setInt(4, entity.getAgeRestriction());
            statement.setDate(5, java.sql.Date.valueOf(entity.getReleaseDate()));
            statement.setString(6, entity.getPosterPath());
            statement.executeUpdate();
            LOGGER.log(Level.INFO, "Add new film to database");
            return findIdByFilm(entity, connection);
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
    }

    @Override
    public List<Film> findAll() throws DAOException {
        List<Film> films = new ArrayList<>();
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_FILMS);
            while (resultSet.next()) {
                Film film = createFilmFromResultSet(resultSet);
                films.add(film);
            }
            for (Film film : films) {
                film.setRating(findFilmRatingById(film.getId(), connection));
            }
            return films;
        } catch (SQLException | ConnectionException exc) {
            throw new DAOException(exc);
        }
    }

    /**
     * Insert actors to film.
     *
     * @param filmId   the film id
     * @param actorsId the actors id
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public boolean insertActorsToFilm(int filmId, int actorsId[]) throws DAOException {
        return insertOrDeleteInformationFromFilm(filmId, actorsId, SQL_INSERT_ACTORS_TO_FILM);
    }

    /**
     * Insert genres to film.
     *
     * @param filmId   the film id
     * @param genresId the genres id
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public boolean insertGenresToFilm(int filmId, int genresId[]) throws DAOException {
        return insertOrDeleteInformationFromFilm(filmId, genresId, SQL_INSERT_GENRES_TO_FILM);
    }

    /**
     * Find films by actor id.
     *
     * @param id the id
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Film> findFilmsByActorId(int id) throws DAOException {
        List<Film> films = new ArrayList<>();
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_FILMS_BY_ACTOR_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next() && resultSet.getInt(SQLConstant.FILM_ID) != 0) {
                Film film = createFilmFromResultSet(resultSet);
                films.add(film);
            }
            for (Film film : films) {
                film.setRating(findFilmRatingById(film.getId(), connection));
            }
            return (films.isEmpty()) ? null : films;
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
    }

    /**
     * Delete film.
     *
     * @param filmId the film id
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public boolean deleteFilm(int filmId) throws DAOException {
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_FILM)) {
            preparedStatement.setInt(1, filmId);
            return preparedStatement.executeUpdate() != 0;
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
    }

    /**
     * Update film.
     *
     * @param filmId             the film id
     * @param filmTitle          the film title
     * @param countryId          the country id
     * @param filmDescription    the film description
     * @param filmAgeRestriction the film age restriction
     * @param filmReleaseDate    the film release date
     * @param filmPosterPath     the film poster path
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public boolean updateFilm(int filmId, String filmTitle, int countryId, String filmDescription,
                              int filmAgeRestriction, LocalDate filmReleaseDate, String filmPosterPath)
            throws DAOException {
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_FILM)) {
            statement.setString(1, filmTitle);
            statement.setInt(2, countryId);
            statement.setString(3, filmDescription);
            statement.setInt(4, filmAgeRestriction);
            statement.setDate(5, java.sql.Date.valueOf(filmReleaseDate));
            statement.setString(6, filmPosterPath);
            statement.setInt(7, filmId);
            if (statement.executeUpdate() != 0) {
                LOGGER.log(Level.INFO, "Update film " + filmId);
                return true;
            } else {
                return false;
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
    }

    /**
     * Delete genres from film.
     *
     * @param filmId   the film id
     * @param genresId the genres id
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public boolean deleteGenresFromFilm(int filmId, int genresId[]) throws DAOException {
        return insertOrDeleteInformationFromFilm(filmId, genresId, SQL_DELETE_GENRES_FROM_FILM);
    }

    /**
     * Delete actors from film.
     *
     * @param filmId   the film id
     * @param actorsId the actors id
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public boolean deleteActorsFromFilm(int filmId, int actorsId[]) throws DAOException {
        return insertOrDeleteInformationFromFilm(filmId, actorsId, SQL_DELETE_ACTORS_FROM_FILM);
    }

    /**
     * Insert or delete information from film.
     *
     * @param filmId the film id
     * @param infoId the info id
     * @param sql    the sql
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    private boolean insertOrDeleteInformationFromFilm(int filmId, int infoId[], String sql) throws DAOException {
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            return ActorDAO.insertOrDeleteEntities(statement, infoId, filmId);
        } catch (SQLException | ConnectionException exc) {
            throw new DAOException(exc);
        }
    }


    /**
     * Creates the film from result set.
     *
     * @param resultSet the result set
     * @return the film
     * @throws SQLException the SQL exception
     */
    private Film createFilmFromResultSet(ResultSet resultSet) throws SQLException {
        Film film;
        int id = resultSet.getInt(SQLConstant.FILM_ID);
        String title = resultSet.getString(SQLConstant.FILM_TITLE);
        int countryId = resultSet.getInt(SQLConstant.COUNTRY_ID);
        String countryName = resultSet.getString(SQLConstant.COUNTRY_NAME);
        Country country = new Country(countryId, countryName);
        String description = resultSet.getString(SQLConstant.FILM_DESCRIPTION);
        int ageRestriction = resultSet.getInt(SQLConstant.FILM_AGE_RESTRICTION);
        LocalDate localDate = resultSet.getDate(SQLConstant.FILM_DATE_OF_RELEASE).toLocalDate();
        String posterPath = resultSet.getString(SQLConstant.FILM_POSTER);
        film = new Film(id, title, country, description, ageRestriction, localDate, posterPath, 0,
                null, null, null);
        return film;
    }

    /**
     * Find film rating by id.
     *
     * @param id         the id
     * @param connection the connection
     * @return the double
     * @throws DAOException the DAO exception
     */
    private double findFilmRatingById(int id, Connection connection) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_RATING)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getDouble("flm.rating");
        } catch (SQLException exc) {
            throw new DAOException(exc);
        }
    }

    /**
     * Find id by film.
     *
     * @param film       the film
     * @param connection the connection
     * @return the int
     * @throws DAOException the DAO exception
     */
    private int findIdByFilm(Film film, Connection connection) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ID_BY_FILM)) {
            statement.setString(1, film.getTitle());
            statement.setString(2, film.getDescription());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(SQLConstant.FILM_ID);
            }
            return 0;
        } catch (SQLException exc) {
            throw new DAOException(exc);
        }
    }
}
