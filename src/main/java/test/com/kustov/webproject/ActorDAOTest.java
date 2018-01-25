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

public class ActorDAOTest {
    private List<Actor> actors = new ArrayList<>();
    private int id;

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
        id = 1;
    }

    @Test
    public void findActorsByFilmId() throws DAOException{
        ActorDAO actorDAO = new ActorDAO();
        List<Actor> actual = actorDAO.findActorsByFilmId(id);
        Assert.assertEquals(actors, actual);
    }
}
