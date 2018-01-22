package com.kustov.webproject.dao;

import com.kustov.webproject.entity.Actor;
import com.kustov.webproject.entity.Country;
import com.kustov.webproject.entity.Film;
import com.kustov.webproject.exception.ConnectionException;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.pool.DBConnectionPool;
import com.kustov.webproject.pool.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActorDAO extends AbstractDAO<Integer, Actor>{
    private final static String SQL_SELECT_ACTORS_BY_FILM_ID = "SELECT film_id, actor_id, actor_name, actor_surname, " +
            "actor_country, actor_image_path \n" + " FROM `filmratingdb`.film JOIN (SELECT film_act_id, actor_id, actor_name, " +
            "actor_surname, actor_country, actor_image_path\n" +
            " FROM `filmratingdb`.actor JOIN `filmratingdb`.film_actor\n" +
            " WHERE actor_id = actor_flm_id) AS actor_film\n" +
            "\tON film_id = film_act_id HAVING film_id = ?";

    @Override
    public List<Actor> findAll() throws DAOException {
        return null;
    }

    public List<Actor> findActorsByFilmId(int id) throws DAOException{
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        DBConnectionPool connectionPool = DBConnectionPool.getInstance();
        List<Actor> actors = new ArrayList<>();
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_ACTORS_BY_FILM_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int actorId = resultSet.getInt("actor_id");
                String name = resultSet.getString("actor_name");
                String surname = resultSet.getString("actor_surname");
                String imagePath = resultSet.getString("actor_image_path");
                CountryDAO countryDAO = new CountryDAO();
                int countryId = resultSet.getInt("actor_country");
                Country country = null;
                if (countryId != 0){
                    country = countryDAO.findById(countryId);
                }
                Actor actor = new Actor(actorId, name, surname, imagePath, country, null);
                actors.add(actor);
            }
            return actors;
        }catch (SQLException | ConnectionException exc){
            throw new DAOException(exc);
        }finally {
            close(statement, connectionPool, connection);
        }
    }
}
