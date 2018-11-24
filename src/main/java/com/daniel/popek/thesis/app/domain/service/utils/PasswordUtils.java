package com.daniel.popek.thesis.app.domain.service.utils;

import org.springframework.data.util.Pair;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

public class PasswordUtils {
    public static Pair<String, String> decryptPassword(String password)
    {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            String salt = generateSalt();
            String passWithSalt = password + salt;
            byte[] passBytes = passWithSalt.getBytes();
            byte[] passHash = sha256.digest(passBytes);
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< passHash.length ;i++) {
                sb.append(Integer.toString((passHash[i] & 0xff) + 0x100, 16).substring(1));
            }
            Pair<String, String> generatedPassword = Pair.of(salt, sb.toString());
            return generatedPassword;

        } catch( NoSuchAlgorithmException e) {
            throw new RuntimeException( e );
        }
    }

    public static String generateValidationKey(){
        return generateRandomHash(6);
    }

    public static String generateSalt(){
        return generateRandomHash(9);
    }

    public static String generateRandomPassword(){
        return generateRandomHash(9);
    }

    public static String generateActivationCode(){
        return generateRandomHash(24);
    }

    public static String generateAccessToker(){
        return generateRandomHash(24);
    }

    private static String generateRandomHash(int length){
        byte[] array = new byte[length];
        new Random().nextBytes(array);
        String generatedString = Base64.getUrlEncoder().encodeToString(array);
        return generatedString;
    }

    public static String encryptPassword(String password, String salt){
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            String passWithSalt = password + salt;
            byte[] passBytes = passWithSalt.getBytes();
            byte[] passHash = sha256.digest(passBytes);
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< passHash.length ;i++) {
                sb.append(Integer.toString((passHash[i] & 0xff) + 0x100, 16).substring(1));
            }
            String generatedPassword = sb.toString();
            return generatedPassword;

        } catch( NoSuchAlgorithmException e) {
            throw new RuntimeException( e );
        }
    }
}
