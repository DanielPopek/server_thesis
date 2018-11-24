package com.daniel.popek.thesis.app.domain.service.utils;

import com.daniel.popek.thesis.app.persistence.entities.Application;
import com.daniel.popek.thesis.app.persistence.entities.Conversation;
import com.daniel.popek.thesis.app.persistence.entities.Intent;
import org.springframework.stereotype.Service;

@Service
public interface IHashingService {

    public String createHash(Intent intent);
    public String createHash (Conversation conversation);
    public String createHash (Application application);
}
