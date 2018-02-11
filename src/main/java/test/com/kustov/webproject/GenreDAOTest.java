package test.com.kustov.webproject;

import com.kustov.webproject.dao.GenreDAO;
import com.kustov.webproject.entity.Genre;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.pool.DBConnectionPool;
import com.kustov.webproject.service.PropertyManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;




/**
 * The Class GenreDAOTest.
 */
public class GenreDAOTest {
    
    /** The genres. */
    private List<Genre> genres = new ArrayList<>();
    
    /** The genre DAO. */
    private GenreDAO genreDAO = new GenreDAO();

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
        genres.add(new Genre(5, "Adventure"));
        genres.add(new Genre(6, "Action"));
    }

    /**
     * Find by id.
     *
     * @throws DAOException the DAO exception
     */
    @Test
    public void findById() throws DAOException {
        List<Genre> actual;
        int id = 2;
        actual = genreDAO.findGenresByFilmId(id);
        Assert.assertEquals(genres, actual);
    }

    /**
     * Find by wrong id.
     *
     * @throws DAOException the DAO exception
     */
    @Test
    public void findByWrongId() throws DAOException{
        List<Genre> actual;
        int id = 4;
        actual = genreDAO.findGenresByFilmId(id);
        Assert.assertEquals(actual, null);
    }
}
