package com.kustov.webproject.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryptor {

    public String encryptPassword(String password) throws NoSuchAlgorithmException{
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
