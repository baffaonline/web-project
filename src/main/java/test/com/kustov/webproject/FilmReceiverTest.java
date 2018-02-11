package test.com.kustov.webproject;

import com.kustov.webproject.entity.*;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.FilmReceiver;
import com.kustov.webproject.pool.DBConnectionPool;
import com.kustov.webproject.service.PropertyManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * The Class FilmReceiverTest.
 */
public class FilmReceiverTest {
    
    /** The film for test. */
    private Film filmForTest;
    
    /** The id. */
    private int id = 3;

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
        List<Actor> actorList = new ArrayList<>();
        actorList.add(new Actor(6, "Tim", "Robbins", "/img/actors/robbins.jpg",
                new Country(1, "England"), null));
        actorList.add(new Actor(7, "Morgan", "Freeman", "/img/actors/freeman.jpg",
                new Country(2, "USA"), null));
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(3, "Drama"));
        genres.add(new Genre(4, "Criminal"));
        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review(id, new User(1, "kurty", "5f4dcc3b5aa765d61d8327deb882cf99",
                "kurty872@gmail.com", "Kurt", "Cobein",
                LocalDate.of(1967, 2, 19), new Country(2, "USA"),
                0, false, UserType.USER, null), "Whenever I talk about this movie with my friends, I do not " +
                "even refer to it by title, but rather as \"The Movie\". When I say \"The Movie\", my friends " +
                "know exactly what I'm talking about. In fact the main reason that me and my friends don't refer to it " +
                "by title is because this movie is so great, that we do not feel worthy enough to say it's name in " +
                "vain! I still remember the first time I saw The Shawshank Redemption. Some friends of mine and I went" +
                " to see it at one of those \"Budget Theaters\" over the summer of 1995. None of us really knew anything" +
                " about the movie, but we had heard that it was pretty good. So, not having any real expectations, " +
                "we saw it. When the final credits rolled and the lights came on in the theater, all of us just sat " +
                "there with our jaws hanging down on the floor. I turned my head towards my friend Bob and said \"" +
                "That was the greatest movie I have ever seen in my entire life!\" There's really no other way I can " +
                "put it in words. Every moment of this movie captivated me and inspired me to believe in the one true" +
                " thing in life...... \"HOPE\". This movie is sad, uplifting, inspiring, harsh, cold, funny (at the" +
                " right times), jaw-dropping, and heart-warming all at the same time. I get chills every time I watch" +
                " this film and this film contains the greatest ending (which takes place over the last 30 minutes or" +
                " so)in movie history! How it all just \"comes together\" is so incredible and uplifting. I should also" +
                " mention the music in this movie is nearly flawless as well, and the soundtrack is a \"must buy\"" +
                " for any music fan. The only bad thing about this movie is the fact that I know I will never see a" +
                " better movie, no matter how many I may see. I've seen many movies over the past few years, many" +
                " excellent films. However, every time I walk out of the theater, I turn to my friend or family member" +
                " that I saw it with and I say, \"That was a great movie, but it wasn't nearly as good as 'you know" +
                " what'!\" There have been many movies over the past few years that thought would really move me or" +
                " that I might one day call my \"favorite of all time\", but they've all fallen far short of \"The " +
                "Shawshank Redemption\". So, if you have yet to see this movie, please run to the video store immediately." +
                " You're guaranteed to find it in the \"employee picks\" section. And if it isn't in that section, then" +
                " you should tell the employees there that there is something seriously wrong with them. If you have seen" +
                " this movie, go see it again. If you haven't bought your own copy yet, buy one. In fact buy two, and put" +
                " one in a fire-proof safe, just in case of an emergency. :o) So, you want a 1-10 rating??? I give it" +
                " a 13!!!!!!", "Simply known as \"The Movie\"", 8, null));
        reviews.add(new Review(id, new User(2, "coolman", "25d55ad283aa400af464c76d713c07ad",
                "goodmail@gmail.com", "Jack", "Smith", LocalDate.of(1988, 3, 30),
                new Country(1, "England"), 0, false, UserType.USER, null), "I have never seen such " +
                "an amazing film since I saw The Shawshank Redemption. Shawshank encompasses friendships, hardships," +
                " hopes, and dreams. And what is so great about the movie is that it moves you, it gives you hope. Even" +
                " though the circumstances between the characters and the viewers are quite different, you don't feel " +
                "that far removed from what the characters are going through.\n" + "\n" +
                "It is a simple film, yet it has an everlasting message. Frank Darabont didn't need to put any kind " +
                "of outlandish special effects to get us to love this film, the narration and the acting does that for" +
                " him. Why this movie didn't win all seven Oscars is beyond me, but don't let that sway you to not see" +
                " this film, let its ranking on the IMDb's top 250 list sway you, let your friends recommendation about" +
                " the movie sway you.\n" + "\n" + "Set aside a little over two hours tonight and rent this movie. You " +
                "will finally understand what everyone is talking about and you will understand why this is my all time " +
                "favorite movie.", "Prepare to be moved", 9, null));
        filmForTest = new Film(id, "The Shawshank Redemption", new Country(2, "USA"),
                "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts " +
                        "of common decency.", 16, LocalDate.of(1994, 10, 13),
                "img/shawshank.jpg", 8.5, actorList, genres, reviews);
    }



    /**
     * Find film by id.
     *
     * @throws ServiceException the service exception
     */
    @Test
    public void findFilmById() throws ServiceException{
        FilmReceiver receiver = new FilmReceiver();
        Film actual = receiver.findFilmById(id);
        Assert.assertEquals(actual, filmForTest);
    }

    /**
     * Find film by I wrong id.
     *
     * @throws ServiceException the service exception
     */
    @Test
    public void findFilmByIWrongId() throws ServiceException{
        FilmReceiver receiver = new FilmReceiver();
        Film actual = receiver.findFilmById(-1);
        Assert.assertEquals(actual, null);
    }
}
