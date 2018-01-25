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
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class FilmDAOTest {
    List<Film> films = new ArrayList<>();

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
    }

    @Test
    public void findAll() throws DAOException{
        FilmDAO dao = new FilmDAO();
        List<Film> answer = dao.findAll();
        Assert.assertEquals(films, answer);
    }
}
