package com.kustov.webproject.logic;

import com.kustov.webproject.dao.UserDAO;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.exception.ServiceException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserReceiver extends DefaultReceiver{
    public boolean checkUser(String login, String password) throws ServiceException{
        UserDAO dao = new UserDAO();
        try {
            return dao.findUserByUsernameAndPassword(login, decodePassword(password)) != null;
        }catch (NoSuchAlgorithmException | DAOException exc){
            throw new ServiceException(exc);
        }
    }

    private String decodePassword(String password) throws NoSuchAlgorithmException{
        StringBuilder code = new StringBuilder();
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte bytes[] = password.getBytes();
        byte digest[] = messageDigest.digest(bytes);
        for (byte digestByte : digest) {
            code.append(Integer.toHexString(0x0100 + (digestByte & 0x00FF)).substring(1));
        }
        return code.toString();
    }
}
