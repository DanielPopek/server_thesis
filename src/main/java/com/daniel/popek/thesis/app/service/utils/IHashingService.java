package com.daniel.popek.thesis.app.service.utils;

import com.daniel.popek.thesis.app.model.entities.Application;
import com.daniel.popek.thesis.app.model.entities.Conversation;
import com.daniel.popek.thesis.app.model.entities.Intent;
import org.springframework.stereotype.Service;

@Service
public interface IHashingService {

    public String createHash(Intent intent);
    public String createHash (Conversation conversation);
    public String createHash (Application application);
}
