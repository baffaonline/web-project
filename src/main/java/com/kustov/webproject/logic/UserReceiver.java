package com.kustov.webproject.logic;

import com.kustov.webproject.dao.ReviewDAO;
import com.kustov.webproject.dao.UserDAO;
import com.kustov.webproject.entity.User;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.service.Encryptor;

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

    public int addUser(User user) throws ServiceException {
        UserDAO dao = new UserDAO();
        try {
            Encryptor encryptor = new Encryptor();
            user.setPassword(encryptor.encryptPassword(user.getPassword()));
            return dao.insert(user);
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
