package test.com.kustov.webproject;

import com.kustov.webproject.dao.ReviewDAO;
import com.kustov.webproject.entity.Review;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.pool.DBConnectionPool;
import com.kustov.webproject.service.PropertyManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * The Country rating.
 */

public class ReviewDAOTest {
    
    /** The reviews. */
    private List<Review> reviews = new ArrayList<>();
    
    /** The id. */
    private int id = 1;

    /**
     * Inits the.
     */
    @BeforeClass
    public void init() {
        PropertyManager databaseManager = new PropertyManager("database");
        String uri = "jdbc:mysql://localhost:3306/test?autoReconnect=true&useSSL=false";
        String user = databaseManager.getProperty("db.user");
        String password = databaseManager.getProperty("db.password");
        int poolSize = 1;
        DBConnectionPool.getInstance(uri, user, password, poolSize);
//        reviews.add(new Review(id, new User(1, "kurty", "5f4dcc3b5aa765d61d8327deb882cf99",
//                "kurty872@gmail.com", "Kurt", "Cobein", LocalDate.of(1967, 2,
//                19),
//                new Country(2, "USA"), 0, false, UserType.USER),
//                "I don't know what s wrong with some people.This movie had nothing against Europeans or Europe culture/history.Cause if it had,then this wouldn't be approved by European critics.\n" +
//                "\n" +
//                "Anyway,this movie and the Logan are the grand jewels of comic book movies of this year.Logan was deep,emotional,brutal with intriguing story.Thor is pure epicness,filled with action and the comedy side fits perfectly to tone of the movie.From the trailer I thought Guardians of the Galaxy will have lots of cringe jokes and cheesy moments but they used the comedy perfectly.Same can be said for Thor 3 too.They managed to have an impact on the audience with sentimental stuffs,at the same time they made us laugh in right places. This movie has already gotten itself carved to my memory.A memorable movie for me.Just like dark knight,Logan and guardians of the galaxy 1.",
//                "Nice movie", 9, 0, 0));
//        reviews.add(new Review(id, new User(2, "coolman", "25d55ad283aa400af464c76d713c07ad",
//                "goodmail@gmail.com", "Jack", "Smith", LocalDate.of(1988, 3,
//                30),
//                new Country(1, "England"), 0, false, UserType.USER),
//                "I know some people are worried about the humour on this movie.But they used it excellently in this flick.This movie to me,is at least on the same level with Guardians of the Galaxy 1.I mean both guardians and Thor 3 made me laugh but never felt cringe or sensed any forced jokes.There were also very intense and emotional moments in this Thor movie.The fight sequences were so damn epic.When it comes to action,this movie is close to Lord of the Rings 2.They did a good job on reflecting the epicness of the Ragnarok event. The last trailer of the movie got my attention.And I made one of the best decisions of my life by watching it.\n" +
//                        "\n" +
//                        "Hela,Valkyrie,Hulk,Thor,Loki,Grandmaster they all did pretty good job.Especially Hela.She was so gorgeous and menacing at the same time. Tessa Thompson kinda surprised me with her great performance btw.",
//                        "Jokes weren't misplaced", 8, 0, 0));
    }

    /**
     * Find reviews by film id.
     *
     * @throws DAOException the DAO exception
     */
    @Test
    public void findReviewsByFilmId() throws DAOException{
        ReviewDAO dao = new ReviewDAO();
        List<Review> actual = dao.findReviewsByFilmId(id);
        Assert.assertEquals(actual, reviews);
    }
}
