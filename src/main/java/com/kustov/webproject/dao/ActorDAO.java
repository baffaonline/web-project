package com.kustov.webproject.dao;

import com.kustov.webproject.entity.Actor;
import com.kustov.webproject.entity.Country;
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

public class ActorDAO extends AbstractEntityDAO<Integer, Actor> {

    private final static String SQL_SELECT_ACTORS_BY_FILM_ID = "SELECT film_id, actor_id, actor_name, " +
            "actor_surname, country_id, country_name, actor_image_path FROM film JOIN (SELECT film_act_id, " +
            "actor_id, actor_name, actor_surname, country_id, country_name, actor_image_path FROM (SELECT " +
            "actor_id, actor_name, actor_surname, country_id, country_name, actor_image_path FROM actor LEFT JOIN " +
            "country ON actor_country = country_id) as actor_country JOIN film_actor WHERE actor_id = actor_flm_id) " +
            "AS actor_film ON film_id = film_act_id WHERE film_id = ?";

    private final static String SQL_SELECT_ACTOR_BY_ID = "SELECT actor_id, actor_name, actor_surname, country_id, " +
            "country_name, actor_image_path FROM actor LEFT JOIN country ON actor_country = country_id " +
            "WHERE actor_id = ?";

    private final static String SQL_SELECT_ACTOR_BY_NAME_AND_SURNAME = "SELECT actor_id, actor_name, actor_surname " +
            "FROM actor WHERE actor_name = ? AND actor_surname = ?";

    private final static String SQL_SELECT_ALL_ACTORS = "SELECT actor_id, actor_name, actor_surname, country_id, " +
            "country_name, actor_image_path FROM actor LEFT JOIN country ON actor_country = country_id";

    private final static String SQL_INSERT_ACTOR = "INSERT into actor (actor_id, actor_name, actor_surname, " +
            "actor_country, actor_image_path) VALUES (NULL, ?, ?, ?, ?) ";

    private final static String SQL_INSERT_FILM_TO_ACTOR = "INSERT into film_actor (film_act_id, actor_flm_id) " +
            "VALUES (?, ?)";

    @Override
    public List<Actor> findAll() throws DAOException{
        ProxyConnection connection = null;
        Statement statement = null;
        DBConnectionPool connectionPool = DBConnectionPool.getInstance();
        List<Actor> actors = new ArrayList<>();
        try{
            connection = connectionPool.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_ACTORS);
            while (resultSet.next()){
                Actor actor = createActorFromResultSet(resultSet);
                actors.add(actor);
            }
            return (actors.isEmpty()) ? null : actors;
        }catch (ConnectionException | SQLException exc){
            throw new DAOException(exc);
        }finally {
            close(statement, connectionPool, connection);
        }
    }

    @Override
    public Integer insert(Actor entity) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        DBConnectionPool connectionPool = DBConnectionPool.getInstance();
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_INSERT_ACTOR);
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getSurname());
            statement.setInt(3, entity.getCountry().getId());
            statement.setString(4, entity.getImagePath());
            statement.executeUpdate();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ACTOR_BY_NAME_AND_SURNAME);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getSurname());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                if (resultSet.isLast()){
                    return resultSet.getInt(SQLConstant.ACTOR_ID);
                }
            }
        }catch (ConnectionException | SQLException exc){
            throw new DAOException(exc);
        }finally {
            close(statement, connectionPool, connection);
        }
        return 0;
    }

    public boolean insertFilmsToActor(int actorId, int filmsId[]) throws DAOException{
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        DBConnectionPool connectionPool = DBConnectionPool.getInstance();
        try{
            connection = connectionPool.getConnection();
            boolean isAllInserted = true;
            for (int filmId : filmsId){
                statement = connection.prepareStatement(SQL_INSERT_FILM_TO_ACTOR);
                statement.setInt(1, filmId);
                statement.setInt(2, actorId);
                if (statement.executeUpdate() == 0){
                    isAllInserted = false;
                }
            }
            return isAllInserted;
        }catch (SQLException | ConnectionException exc){
            throw new DAOException(exc);
        }finally {
            close(statement, connectionPool, connection);
        }
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
                Actor actor = createActorFromResultSet(resultSet);
                actors.add(actor);
            }
            return (actors.isEmpty()) ? null : actors;
        }catch (SQLException | ConnectionException exc){
            throw new DAOException(exc);
        }finally {
            close(statement, connectionPool, connection);
        }
    }

    @Override
    public Actor findById(Integer id) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        DBConnectionPool connectionPool = DBConnectionPool.getInstance();
        Actor actor = null;
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_ACTOR_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                actor = createActorFromResultSet(resultSet);
            }
            return actor;
        }catch (ConnectionException | SQLException exc){
            throw new DAOException(exc);
        }finally {
            close(preparedStatement, connectionPool, connection);
        }
    }

    private Actor createActorFromResultSet(ResultSet resultSet) throws SQLException{
        Actor actor = new Actor();
        actor.setId(resultSet.getInt(SQLConstant.ACTOR_ID));
        actor.setName(resultSet.getString(SQLConstant.ACTOR_NAME));
        actor.setSurname(resultSet.getString(SQLConstant.ACTOR_SURNAME));
        actor.setImagePath(resultSet.getString(SQLConstant.ACTOR_IMAGE));
        int countryId = resultSet.getInt(SQLConstant.COUNTRY_ID);
        String countryName = resultSet.getString(SQLConstant.COUNTRY_NAME);
        if (countryId != 0){
            actor.setCountry(new Country(countryId, countryName));
        }
        return actor;
    }
}
