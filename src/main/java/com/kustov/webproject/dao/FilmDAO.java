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
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FilmDAO extends AbstractDAO<Integer, Film>{
    private final static String SQL_SELECT_FILMS = "SELECT film_id, film_title, film_country, " +
            "country_name, film_description, film_age_restriction, film_date_of_release, " +
            "film_poster_path FROM `filmratingdb`.film JOIN `filmratingdb`.country " +
            "WHERE film_country = country_id";
    private final static String SQL_SELECT_RATING = "SELECT film_id, flm.rating \n" +
            "    FROM `filmratingdb`.film LEFT JOIN (SELECT review.film_rvw_id, AVG(review.user_mark) AS rating \n" +
            "                                                FROM `filmratingdb`.review \n" +
            "                                                GROUP BY review.film_rvw_id) as flm\n" +
            "    ON film_id = flm.film_rvw_id HAVING film_id = ?";

    private final static String SQL_SELECT_FILM_BY_ID = "SELECT film_id, film_title, film_country, " +
            "country_name, film_description, film_age_restriction, film_date_of_release, " +
            "film_poster_path FROM `filmratingdb`.film JOIN `filmratingdb`.country " +
            "WHERE film_country = country_id and film_id = ?";

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
                ActorDAO actorDAO = new ActorDAO();
                List<Actor> actors = actorDAO.findActorsByFilmId(film.getId());
                film.setActors(actors);
                GenreDAO genreDAO = new GenreDAO();
                List<Genre> genres = genreDAO.findGenresByFilmId(film.getId());
                film.setGenres(genres);
                ReviewDAO reviewDAO = new ReviewDAO();
                List<Review> reviews = reviewDAO.findReviewsByFilmId(film.getId());
                film.setReviews(reviews);
            }
            return film;
        }catch (SQLException | ConnectionException exc){
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

    private Film createFilmFromResultSet(ResultSet resultSet) throws SQLException{
        Film film;
        int id = resultSet.getInt("film_id");
        String title = resultSet.getString("film_title");
        int countryId = resultSet.getInt("film_country");
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
}
