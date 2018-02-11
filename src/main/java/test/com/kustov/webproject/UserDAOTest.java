package test.com.kustov.webproject;

import com.kustov.webproject.dao.UserDAO;
import com.kustov.webproject.entity.Country;
import com.kustov.webproject.entity.User;
import com.kustov.webproject.entity.UserType;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.pool.DBConnectionPool;
import com.kustov.webproject.service.Encryptor;
import com.kustov.webproject.service.PropertyManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.jws.soap.SOAPBinding;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * The Class UserDAOTest.
 */

public class UserDAOTest {
    
    /** The all users. */
    private List<User> allUsers;
    
    /** The user DAO. */
    private UserDAO userDAO = new UserDAO();

    /**
     * Inits the.
     */
    @BeforeClass
    public void init() {
        allUsers = new ArrayList<>();
        PropertyManager databaseManager = new PropertyManager("database");
        String uri = "jdbc:mysql://localhost:3306/test?autoReconnect=true&useSSL=false";
        String user = databaseManager.getProperty("db.user");
        String password = databaseManager.getProperty("db.password");
        int poolSize = 1;
        DBConnectionPool.getInstance(uri, user, password, poolSize);
        allUsers.add(new User(1, "kurty", "5f4dcc3b5aa765d61d8327deb882cf99",
                "kurty872@gmail.com", "Kurt", "Cobein", LocalDate.of(1967, 2,
                19),
                new Country(2, "USA"), 0, false, UserType.USER, null));
        allUsers.add(new User(2, "coolman", "25d55ad283aa400af464c76d713c07ad",
                "goodmail@gmail.com", "Jack", "Smith", LocalDate.of(1988, 3,
                30),
                new Country(1, "England"), 0, false, UserType.USER, null));
        allUsers.add(new User(3, "baffa", "f6cae8770df084aa2de88b5ae642ba9f", "baffalo98@gmail.com", "Ivan",
                "Kustov", LocalDate.of(1998, 6, 8), new Country(3, "Belarus"),
                0, false, UserType.ADMIN, null));
    }

    /**
     * Find all.
     *
     * @throws DAOException the DAO exception
     */
    @Test
    public void findAll() throws DAOException {
        List<User> actual = userDAO.findAll();
        Assert.assertEquals(actual, allUsers);
    }

    /**
     * Find by id.
     *
     * @throws DAOException the DAO exception
     */
    @Test
    public void findById() throws DAOException {
        int id = 3;
        User user = userDAO.findById(id);
        Assert.assertEquals(allUsers.get(id - 1), user);
    }

    /**
     * Find user by username and password.
     *
     * @throws DAOException the DAO exception
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    @Test
    public void findUserByUsernameAndPassword() throws DAOException, NoSuchAlgorithmException {
        String username = "baffa";
        String password = "Chelsea5214";
        Encryptor encryptor = new Encryptor();
        String encryptPassword = encryptor.encryptPassword(password);
        User expectedUser = allUsers.get(2);
        User actualUser = userDAO.findUserByUsernameAndPassword(username, encryptPassword);
        Assert.assertEquals(expectedUser, actualUser);
    }

    /**
     * Find user by wrong username or password.
     *
     * @throws DAOException the DAO exception
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    @Test
    public void findUserByWrongUsernameOrPassword() throws DAOException, NoSuchAlgorithmException {
        String username = "WrongUsername";
        String password = "WrongPassword";
        Encryptor encryptor = new Encryptor();
        String encryptPassword = encryptor.encryptPassword(password);
        User actualUser = userDAO.findUserByUsernameAndPassword(username, encryptPassword);
        Assert.assertEquals(actualUser, null);
    }

    /**
     * Find user by email.
     *
     * @throws DAOException the DAO exception
     */
    @Test
    public void findUserByEmail() throws DAOException {
        String email = "kurty872@gmail.com";
        User expectedUser = allUsers.get(0);
        User actualUser = userDAO.findUserByEmail(email);
        Assert.assertEquals(actualUser, expectedUser);
    }
}
