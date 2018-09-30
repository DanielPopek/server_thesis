package com.daniel.popek.thesis.app.service.data;

import com.daniel.popek.thesis.app.model.DTO.design.IntentDTO;
import com.daniel.popek.thesis.app.model.entities.Conversation;
import com.daniel.popek.thesis.app.model.entities.Intent;
import org.springframework.stereotype.Service;

@Service
public interface IIntentService {

    public void saveRootIntent(IntentDTO intentDTO, Conversation conversation);

    public void deleteRootIntent(Intent intent);

    public void deleteRootIntentByConversation(Conversation conversation);
}
