package test.com.kustov.webproject;

import com.kustov.webproject.dao.FilmDAO;
import com.kustov.webproject.entity.Country;
import com.kustov.webproject.entity.Film;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.pool.DBConnectionPool;
import com.kustov.webproject.service.PropertyManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * The Class FilmDAOTest.
 */
public class FilmDAOTest {
    
    /** The films. */
    private List<Film> films = new ArrayList<>();
    
    /** The dao. */
    private FilmDAO dao = new FilmDAO();

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
        films.add(new Film(1, "Thor 3 : Ragnarok", new Country(2, "USA"), "Imprisoned, " +
                "the almighty Thor finds himself in a lethal gladiatorial contest against the Hulk, " +
                "his former ally. Thor must fight for survival and race against time to prevent the all-powerful Hela " +
                "from destroying his home and the Asgardian civilization.", 16,
                LocalDate.of(2017, 11, 1), "img/thor3.jpg", 8.5, null,
                null, null));
        films.add(new Film(2, "Spider-Man: Homecoming", new Country(2, "USA"), "Peter" +
                " Parker balances his life as an ordinary high school student in Queens with his superhero alter-ego" +
                " Spider-Man, and finds himself on the trail of a new menace prowling the skies of New York City.",
                16, LocalDate.of(2017, 7, 6), "img/spider-man.jpg",
                8, null, null, null));
        films.add(new Film(3, "The Shawshank Redemption", new Country(2, "USA"), "Two imprisoned men bond over" +
                " a number of years, finding solace and eventual redemption through acts of common decency.", 16,
                LocalDate.of(1994, 10, 13), "img/shawshank.jpg", 8.5,
                null, null, null));
        films.add(new Film(4, "The Avengers", new Country(2, "USA"), "Earth's " +
                "mightiest heroes must come together and learn to fight as a team if they are going to stop the" +
                " mischievous Loki and his alien army from enslaving humanity.",
                12, LocalDate.of(2012, 5, 3),
                "img/avengers.jpg", 0, null, null, null));
    }

    /**
     * Find all.
     *
     * @throws DAOException the DAO exception
     */
    @Test
    public void findAll() throws DAOException{
        List<Film> answer = dao.findAll();
        Assert.assertEquals(films, answer);
    }

    /**
     * Find by id.
     *
     * @throws DAOException the DAO exception
     */
    @Test
    public void findById() throws DAOException{
        int id = 2;
        Film expected = films.get(id - 1);
        Film actual = dao.findById(id);
        Assert.assertEquals(expected, actual);
    }

    /**
     * Find by wrong id.
     *
     * @throws DAOException the DAO exception
     */
    @Test
    public void findByWrongId() throws DAOException{
        int id = -1;
        Film actual = dao.findById(id);
        Assert.assertEquals(actual, null);
    }

    /**
     * Find films by actor id.
     *
     * @throws DAOException the DAO exception
     */
    @Test
    public void findFilmsByActorId() throws DAOException{
        int id = 4;
        List<Film> expectedFilms = new ArrayList<>();
        expectedFilms.add(films.get(1));
        expectedFilms.add(films.get(3));
        List<Film> actualFilms = dao.findFilmsByActorId(id);
        Assert.assertEquals(actualFilms, expectedFilms);
    }

    /**
     * Find films by actor id with no films.
     *
     * @throws DAOException the DAO exception
     */
    @Test
    public void findFilmsByActorIdWithNoFilms() throws DAOException{
        List<Film> actualFilms = dao.findFilmsByActorId(9);
        Assert.assertEquals(actualFilms, null);
    }
}
