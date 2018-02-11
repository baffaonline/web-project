package test.com.kustov.webproject;

import com.kustov.webproject.dao.ActorDAO;
import com.kustov.webproject.entity.Actor;
import com.kustov.webproject.entity.Country;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.pool.DBConnectionPool;
import com.kustov.webproject.service.PropertyManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * The Class ActorDAOTest.
 */

public class ActorDAOTest {
    
    /** The actor DAO. */
    private ActorDAO actorDAO = new ActorDAO();
    
    /** The actors. */
    private List<Actor> actors = new ArrayList<>();
    
    /** The all actors. */
    private List<Actor> allActors;
    
    /** The id. */
    private int id;

    /**
     * Inits the.
     */
    @BeforeClass
    public void init(){
        PropertyManager databaseManager = new PropertyManager("database");
        String uri = "jdbc:mysql://localhost:3306/test?autoReconnect=true&useSSL=false";
        String user = databaseManager.getProperty("db.user");
        String password = databaseManager.getProperty("db.password");
        int poolSize = 1;
        DBConnectionPool.getInstance(uri, user, password, poolSize);
        actors.add(new Actor(1, "Benedict", "Cumberbatch", "/img/actors/cumberbatch.jpg",
                null, null));
        actors.add(new Actor(2, "Chris", "Hemsworth", "/img/actors/hemsworth.jpg",
                new Country(8, "Australia"), null));
        actors.add(new Actor(3, "Tom", "Hiddleston", "/img/actors/hiddleston.png",
                new Country(1, "England"), null));
        allActors = new ArrayList<>(actors);
        allActors.add(new Actor(4, "Robert", "Downey Jr.", "/img/actors/downey.jpg",
                new Country(2, "USA"), null));
        allActors.add(new Actor(5, "Tom", "Holland", "/img/actors/holland.jpg",
                new Country(1, "England"), null));
        allActors.add(new Actor(6, "Tim", "Robbins", "/img/actors/robbins.jpg",
                new Country(1, "England"), null));
        allActors.add(new Actor(7, "Morgan", "Freeman", "/img/actors/freeman.jpg",
                new Country(2, "USA"), null));
        allActors.add(new Actor(8, "Michael", "Keaton", "/img/actors/keaton.jpg",
                new Country(2, "USA"), null));
        allActors.add(new Actor(9, "Александр", "Невский", "/img/actors/nevskiy.png",
                new Country(4, "Russia"), null));
        id = 1;
    }

    /**
     * Find actors by film id.
     *
     * @throws DAOException the DAO exception
     */
    @Test
    public void findActorsByFilmId() throws DAOException{
        List<Actor> actual = actorDAO.findActorsByFilmId(id);
        Assert.assertEquals(actors, actual);
    }

    /**
     * Find all.
     *
     * @throws DAOException the DAO exception
     */
    @Test
    public void findAll() throws DAOException {
        List<Actor> actual = actorDAO.findAll();
        Assert.assertEquals(actual, allActors);
    }

    /**
     * Find by id.
     *
     * @throws DAOException the DAO exception
     */
    @Test
    public void findById() throws DAOException {
        int id = 9;
        Actor expectedActor = allActors.get(id - 1);
        Actor actual = actorDAO.findById(id);
        Assert.assertEquals(expectedActor, actual);
    }

    /**
     * Find by wrong id.
     *
     * @throws DAOException the DAO exception
     */
    @Test
    public void findByWrongId() throws DAOException{
        int id = 12;
        Actor actual = actorDAO.findById(id);
        Assert.assertEquals(actual, null);
    }
}
