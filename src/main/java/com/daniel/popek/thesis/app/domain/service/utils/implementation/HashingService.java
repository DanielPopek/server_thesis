package com.daniel.popek.thesis.app.domain.service.utils.implementation;

import com.daniel.popek.thesis.app.persistence.entities.Application;
import com.daniel.popek.thesis.app.persistence.entities.Conversation;
import com.daniel.popek.thesis.app.persistence.entities.Intent;
import com.daniel.popek.thesis.app.domain.service.utils.IHashingService;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

@Service
public class HashingService implements IHashingService{

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    private static int HASH_LENGTH=36;

    @Override
    public String createHash(Intent intent) {
        //return generateRandomHash(24)+intent.getName();
        return generateHashUUID();
    }

    @Override
    public String createHash(Conversation conversation) {

        //return generateRandomHash(24)+conversation.getName();
        return generateHashUUID();
    }

    @Override
    public String createHash(Application application) {
        return generateHashUUID();
    }

    private  String generateRandomHash(int length){
        byte[] array = new byte[length];
        new Random().nextBytes(array);
        String generatedString = Base64.getUrlEncoder().encodeToString(array);
        return generatedString;
    }

    private String generateHashUUIDWithSSH()
    {
        String hash;
        try{
            MessageDigest salt = MessageDigest.getInstance("SHA-256");
            String uuidString=UUID.randomUUID().toString();
            System.out.println(uuidString);
            salt.update(uuidString.getBytes("UTF-8"));
            hash= bytesToHex(salt.digest());
        }catch (Exception ex)
        {
            hash=generateRandomHash(HASH_LENGTH);
        }
        return hash;
    }

    private String generateHashUUID()
    {
        String hash;
        try{
            String uuidString=UUID.randomUUID().toString();
            System.out.println(uuidString);
            hash= uuidString;
        }catch (Exception ex)
        {
            hash=generateRandomHash(HASH_LENGTH);
        }
        return hash;
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
