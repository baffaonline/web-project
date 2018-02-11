package com.kustov.webproject.dao;

import com.kustov.webproject.entity.Actor;
import com.kustov.webproject.entity.Country;
import com.kustov.webproject.exception.ConnectionException;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.pool.DBConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The Class ActorDAO.
 */
public class ActorDAO extends AbstractEntityDAO<Integer, Actor> {

    /**
     * The Constant SQL_SELECT_ACTORS_BY_FILM_ID.
     */
    private final static String SQL_SELECT_ACTORS_BY_FILM_ID = "SELECT film_id, actor_id, actor_name, " +
            "actor_surname, country_id, country_name, actor_image_path FROM film JOIN (SELECT film_act_id, " +
            "actor_id, actor_name, actor_surname, country_id, country_name, actor_image_path FROM (SELECT " +
            "actor_id, actor_name, actor_surname, country_id, country_name, actor_image_path FROM actor LEFT JOIN " +
            "country ON actor_country = country_id) AS actor_country JOIN film_actor WHERE actor_id = actor_flm_id) " +
            "AS actor_film ON film_id = film_act_id WHERE film_id = ?";

    /**
     * The Constant SQL_SELECT_ACTOR_BY_ID.
     */
    private final static String SQL_SELECT_ACTOR_BY_ID = "SELECT actor_id, actor_name, actor_surname, country_id, " +
            "country_name, actor_image_path FROM actor LEFT JOIN country ON actor_country = country_id " +
            "WHERE actor_id = ?";

    /**
     * The Constant SQL_SELECT_ACTOR_BY_NAME_AND_SURNAME.
     */
    private final static String SQL_SELECT_ACTOR_BY_NAME_AND_SURNAME = "SELECT actor_id, actor_name, actor_surname " +
            "FROM actor WHERE actor_name = ? AND actor_surname = ?";

    /**
     * The Constant SQL_SELECT_ALL_ACTORS.
     */
    private final static String SQL_SELECT_ALL_ACTORS = "SELECT actor_id, actor_name, actor_surname, country_id, " +
            "country_name, actor_image_path FROM actor LEFT JOIN country ON actor_country = country_id";

    /**
     * The Constant SQL_INSERT_ACTOR.
     */
    private final static String SQL_INSERT_ACTOR = "INSERT INTO actor (actor_id, actor_name, actor_surname, " +
            "actor_country, actor_image_path) VALUES (NULL, ?, ?, ?, ?) ";

    /**
     * The Constant SQL_INSERT_FILM_TO_ACTOR.
     */
    private final static String SQL_INSERT_FILM_TO_ACTOR = "INSERT INTO film_actor (film_act_id, actor_flm_id) " +
            "VALUES (?, ?)";

    @Override
    public List<Actor> findAll() throws DAOException {
        List<Actor> actors = new ArrayList<>();
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_ACTORS);
            while (resultSet.next()) {
                Actor actor = createActorFromResultSet(resultSet);
                actors.add(actor);
            }
            return (actors.isEmpty()) ? null : actors;
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
    }

    @Override
    public Integer insert(Actor entity) throws DAOException {
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_ACTOR)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getSurname());
            statement.setInt(3, entity.getCountry().getId());
            statement.setString(4, entity.getImagePath());
            statement.executeUpdate();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ACTOR_BY_NAME_AND_SURNAME);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getSurname());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.isLast()) {
                    return resultSet.getInt(SQLConstant.ACTOR_ID);
                }
            }
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
        return 0;
    }

    /**
     * Insert films to actor.
     *
     * @param actorId the actor id
     * @param filmsId the films id
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public boolean insertFilmsToActor(int actorId, int filmsId[]) throws DAOException {
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_FILM_TO_ACTOR)){
            return insertOrDeleteEntities(statement, filmsId, actorId);
        } catch (SQLException | ConnectionException exc) {
            throw new DAOException(exc);
        }
    }

    /**
     * Find actors by film id.
     *
     * @param id the id
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Actor> findActorsByFilmId(int id) throws DAOException {
        List<Actor> actors = new ArrayList<>();
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ACTORS_BY_FILM_ID)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Actor actor = createActorFromResultSet(resultSet);
                actors.add(actor);
            }
            return (actors.isEmpty()) ? null : actors;
        } catch (SQLException | ConnectionException exc) {
            throw new DAOException(exc);
        }
    }

    @Override
    public Actor findById(Integer id) throws DAOException {
        Actor actor = null;
        try (Connection connection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ACTOR_BY_ID)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                actor = createActorFromResultSet(resultSet);
            }
            return actor;
        } catch (ConnectionException | SQLException exc) {
            throw new DAOException(exc);
        }
    }

    /**
     * Creates the actor from result set.
     *
     * @param resultSet the result set
     * @return the actor
     * @throws SQLException the SQL exception
     */
    private Actor createActorFromResultSet(ResultSet resultSet) throws SQLException {
        Actor actor = new Actor();
        actor.setId(resultSet.getInt(SQLConstant.ACTOR_ID));
        actor.setName(resultSet.getString(SQLConstant.ACTOR_NAME));
        actor.setSurname(resultSet.getString(SQLConstant.ACTOR_SURNAME));
        actor.setImagePath(resultSet.getString(SQLConstant.ACTOR_IMAGE));
        int countryId = resultSet.getInt(SQLConstant.COUNTRY_ID);
        String countryName = resultSet.getString(SQLConstant.COUNTRY_NAME);
        if (countryId != 0) {
            actor.setCountry(new Country(countryId, countryName));
        }
        return actor;
    }

    static boolean insertOrDeleteEntities(PreparedStatement statement, int infoId[], int entityId) throws SQLException{
        boolean isAllInserted = true;
        for (int id : infoId) {
            statement.setInt(1, id);
            statement.setInt(2, entityId);
            if (statement.executeUpdate() == 0) {
                isAllInserted = false;
            }
        }
        return isAllInserted;
    }
}
