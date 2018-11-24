package com.daniel.popek.thesis.app.domain.service.data_logic;

import com.daniel.popek.thesis.app.domain.DTO.design.IntentDTO;
import com.daniel.popek.thesis.app.persistence.entities.Conversation;
import com.daniel.popek.thesis.app.persistence.entities.Intent;
import org.springframework.stereotype.Service;

@Service
public interface IIntentService {

    public void saveRootIntent(IntentDTO intentDTO, Conversation conversation);

    public void deleteRootIntent(Intent intent);

    public void deleteRootIntentByConversation(Conversation conversation);

    public IntentDTO getRootIntentByConversationHash(String hash);

    public Intent getIntentOrRootByHash(String hash,String conversationHash);

    Intent getRootEntityIntentByConversationHash(String hash);
}
