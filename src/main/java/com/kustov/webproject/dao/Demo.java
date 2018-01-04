package com.kustov.webproject.dao;

import com.kustov.webproject.entity.User;
import com.kustov.webproject.exception.DAOException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        try {
            String password = "HarryPotter";
            StringBuilder code = new StringBuilder();
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte bytes[] = password.getBytes();
            byte digest[] = messageDigest.digest(bytes);
            for (byte digestByte : digest) {
                code.append(Integer.toHexString(0x0100 + (digestByte & 0x00FF)).substring(1));
            }
            System.out.println(code.toString());
        }catch (NoSuchAlgorithmException exc){
            System.out.println("lol");
        }
    }
}
