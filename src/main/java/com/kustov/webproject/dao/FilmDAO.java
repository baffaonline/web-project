package com.kustov.webproject.dao;

import com.kustov.webproject.entity.Country;
import com.kustov.webproject.entity.Film;
import com.kustov.webproject.exception.ConnectionException;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.pool.DBConnectionPool;
import com.kustov.webproject.pool.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    @Override
    public List<Film> findAll() throws DAOException {
        ProxyConnection connection;
        Statement statement;
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
            PreparedStatement preparedStatement;
            for (Film film : films){
                preparedStatement = connection.prepareStatement(SQL_SELECT_RATING);
                preparedStatement.setInt(1, film.getId());
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                film.setRating(resultSet.getDouble("flm.rating"));
            }
            return films;
        }catch (SQLException | ConnectionException exc){
            throw new DAOException(exc);
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
        Date date = resultSet.getDate("film_date_of_release");
        String posterPath = resultSet.getString("film_poster_path");
        film = new Film(id, title, country, description, ageRestriction, date, posterPath, 0,
                null, null, null);
        return film;
    }
}
