package com.kustov.webproject.logic;

import com.kustov.webproject.dao.UserDAO;
import com.kustov.webproject.entity.User;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.service.Encryptor;

import java.security.NoSuchAlgorithmException;

public class UserReceiver extends DefaultReceiver{
    public User checkUser(String login, String password) throws ServiceException{
        UserDAO dao = new UserDAO();
        try {
            Encryptor encryptor = new Encryptor();
            return dao.findUserByUsernameAndPassword(login, encryptor.encryptPassword(password));
        }catch (NoSuchAlgorithmException | DAOException exc){
            throw new ServiceException(exc);
        }
    }

    public void addUser(User user) throws ServiceException{
        UserDAO dao = new UserDAO();
        try{
            Encryptor encryptor = new Encryptor();
            user.setPassword(encryptor.encryptPassword(user.getPassword()));
            dao.insert(user);
        }catch (NoSuchAlgorithmException | DAOException exc){
            throw new ServiceException(exc);
        }
    }
}
