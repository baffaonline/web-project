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

public class UserReceiver {
    public User checkUser(String login, String password) throws ServiceException {
        UserDAO dao = new UserDAO();
        try {
            Encryptor encryptor = new Encryptor();
            return dao.findUserByUsernameAndPassword(login, encryptor.encryptPassword(password));
        } catch (NoSuchAlgorithmException | DAOException exc) {
            throw new ServiceException(exc);
        }
    }

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

    public User findUserByUsername(String username) throws ServiceException {
        UserDAO dao = new UserDAO();
        try {
            return dao.findUserByUsername(username);
        } catch (DAOException exc) {
            throw new ServiceException(exc);
        }
    }

    public User findUserByEmail(String email) throws ServiceException {
        UserDAO dao = new UserDAO();
        try {
            return dao.findUserByEmail(email);
        } catch (DAOException exc) {
            throw new ServiceException(exc);
        }
    }

    public List<User> findAllUsers() throws ServiceException {
        UserDAO dao = new UserDAO();
        try {
            return dao.findAll();
        } catch (DAOException exc) {
            throw new ServiceException(exc);
        }
    }

    public void findReviewsForUser(User user) throws ServiceException {
        ReviewDAO reviewDAO = new ReviewDAO();
        try {
            user.setReviews(reviewDAO.findReviewsByUserId(user.getId()));
        } catch (DAOException exc) {
            throw new ServiceException(exc);
        }
    }

    public User findUserById(int id) throws ServiceException {
        UserDAO userDAO = new UserDAO();
        try {
            return userDAO.findById(id);
        } catch (DAOException exc) {
            throw new ServiceException(exc);
        }
    }

    public boolean updateBan(int id, boolean isBanned) throws ServiceException{
        UserDAO userDAO = new UserDAO();
        try{
            return userDAO.updateBan(id, isBanned);
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }
}
