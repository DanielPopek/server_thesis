package com.daniel.popek.thesis.app.service.utils.implementation;

import com.daniel.popek.thesis.app.model.entities.Conversation;
import com.daniel.popek.thesis.app.model.entities.Intent;
import com.daniel.popek.thesis.app.service.utils.IHashingService;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Random;

@Service
public class HashingService implements IHashingService{

    @Override
    public String createHash(Intent intent) {
        return generateRandomHash(24)+intent.getName();
    }

    @Override
    public String createHash(Conversation conversation) {
        return generateRandomHash(24)+conversation.getName();
    }

    private  String generateRandomHash(int length){
        byte[] array = new byte[length];
        new Random().nextBytes(array);
        String generatedString = Base64.getUrlEncoder().encodeToString(array);
        return generatedString;
    }
}
