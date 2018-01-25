package test.com.kustov.webproject;

import com.kustov.webproject.dao.CountryDAO;
import com.kustov.webproject.entity.Country;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.pool.DBConnectionPool;
import com.kustov.webproject.service.PropertyManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CountryDAOTest {
    private List<Country> countries = new ArrayList<>();
    private CountryDAO countryDAO = new CountryDAO();

    @BeforeClass
    public void init(){
        PropertyManager databaseManager = new PropertyManager("database");
        String uri = "jdbc:mysql://localhost:3306/test?autoReconnect=true&useSSL=false";
        String user = databaseManager.getProperty("db.user");
        String password = databaseManager.getProperty("db.password");
        int poolSize = 1;
        DBConnectionPool.getInstance(uri, user, password, poolSize);
        countries.add(new Country(1, "England"));
        countries.add(new Country(2, "USA"));
        countries.add(new Country(3, "Belarus"));
        countries.add(new Country(4, "Russia"));
        countries.add(new Country(5, "France"));
        countries.add(new Country(6, "Italy"));
        countries.add(new Country(7, "Canada"));
        countries.add(new Country(8, "Australia"));
        countries.add(new Country(9, "Austria"));
        countries.sort(Comparator.comparing(Country::getName));
    }

    @Test
    public void findAll() throws DAOException{
        List<Country> actual;
        actual = countryDAO.findAll();
        Assert.assertEquals(countries, actual);
    }

    @Test
    public void findById() throws DAOException{
        int id = 3;
        Country expected = new Country(3, "Belarus");
        Country actual = countryDAO.findById(id);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByWrongId() throws DAOException {
        int id = 10;
        Country actual = countryDAO.findById(id);
        Assert.assertEquals(actual, null);
    }
}
