package com.kustov.webproject.logic;

import com.kustov.webproject.dao.CountryDAO;
import com.kustov.webproject.dao.ReviewDAO;
import com.kustov.webproject.dao.UserDAO;
import com.kustov.webproject.entity.Country;
import com.kustov.webproject.entity.User;
import com.kustov.webproject.entity.UserType;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.service.Encryptor;
import com.kustov.webproject.service.StringDateFormatter;

import java.security.NoSuchAlgorithmException;
import java.util.List;


/**
 * The Class UserReceiver.
 */
public class UserReceiver {

    /**
     * Check user.
     *
     * @param login    the login
     * @param password the password
     * @return the user
     * @throws ServiceException the service exception
     */
    public User checkUser(String login, String password) throws ServiceException {
        UserDAO dao = new UserDAO();
        try {
            Encryptor encryptor = new Encryptor();
            return dao.findUserByUsernameAndPassword(login, encryptor.encryptPassword(password));
        } catch (NoSuchAlgorithmException | DAOException exc) {
            throw new ServiceException(exc);
        }
    }

    /**
     * Adds the user.
     *
     * @param username  the username
     * @param password  the password
     * @param email     the email
     * @param name      the name
     * @param surname   the surname
     * @param birthday  the birthday
     * @param countryId the country id
     * @return the user
     * @throws ServiceException the service exception
     */
    public User addUser(String username, String password, String email, String name, String surname,
                        String birthday, int countryId) throws ServiceException {
        UserDAO dao = new UserDAO();
        CountryDAO countryDAO = new CountryDAO();
        try {
            Encryptor encryptor = new Encryptor();
            String encryptedPassword = encryptor.encryptPassword(password);
            Country country = countryDAO.findById(countryId);
            User user = new User(0, username, encryptedPassword, email, name, surname,
                    StringDateFormatter.getDateFromString(birthday), country, 0, false,
                    UserType.USER, null);
            int id = dao.insert(user);
            user.setId(id);
            return user;
        } catch (NoSuchAlgorithmException | DAOException exc) {
            throw new ServiceException(exc);
        }
    }

    /**
     * Find user by username.
     *
     * @param username the username
     * @return the user
     * @throws ServiceException the service exception
     */
    public User findUserByUsername(String username) throws ServiceException {
        UserDAO dao = new UserDAO();
        try {
            return dao.findUserByUsername(username);
        } catch (DAOException exc) {
            throw new ServiceException(exc);
        }
    }

    /**
     * Find user by email.
     *
     * @param email the email
     * @return the user
     * @throws ServiceException the service exception
     */
    public User findUserByEmail(String email) throws ServiceException {
        UserDAO dao = new UserDAO();
        try {
            return dao.findUserByEmail(email);
        } catch (DAOException exc) {
            throw new ServiceException(exc);
        }
    }

    /**
     * Find all users.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<User> findAllUsers() throws ServiceException {
        UserDAO dao = new UserDAO();
        try {
            return dao.findAll();
        } catch (DAOException exc) {
            throw new ServiceException(exc);
        }
    }

    /**
     * Find reviews for user.
     *
     * @param user the user
     * @throws ServiceException the service exception
     */
    public void findReviewsForUser(User user) throws ServiceException {
        ReviewDAO reviewDAO = new ReviewDAO();
        try {
            user.setReviews(reviewDAO.findReviewsByUserId(user.getId()));
        } catch (DAOException exc) {
            throw new ServiceException(exc);
        }
    }

    /**
     * Find user by id.
     *
     * @param id the id
     * @return the user
     * @throws ServiceException the service exception
     */
    public User findUserById(int id) throws ServiceException {
        UserDAO userDAO = new UserDAO();
        try {
            return userDAO.findById(id);
        } catch (DAOException exc) {
            throw new ServiceException(exc);
        }
    }

    /**
     * Update ban.
     *
     * @param id       the id
     * @param isBanned the is banned
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    public boolean updateBan(int id, boolean isBanned) throws ServiceException {
        UserDAO userDAO = new UserDAO();
        try {
            return userDAO.updateBan(id, isBanned);
        } catch (DAOException exc) {
            throw new ServiceException(exc);
        }
    }
}
