package com.kustov.webproject.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * The Class Encryptor.
 */
public class Encryptor {

    /**
     * Encrypt password.
     *
     * @param password the password
     * @return the string
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public String encryptPassword(String password) throws NoSuchAlgorithmException {
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
