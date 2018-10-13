package com.daniel.popek.thesis.app.service.data.implementation;

import com.daniel.popek.thesis.app.model.DTO.design.IntentDTO;
import com.daniel.popek.thesis.app.model.entities.Conversation;
import com.daniel.popek.thesis.app.model.entities.Intent;
import com.daniel.popek.thesis.app.repository.ConversationRepository;
import com.daniel.popek.thesis.app.repository.IntentRepository;
import com.daniel.popek.thesis.app.service.data.IIntentService;
import com.daniel.popek.thesis.app.service.mappers.IIntentMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntentService  implements IIntentService{

    @Autowired
    IntentRepository intentRepository;

    @Autowired
    ConversationRepository conversationRepository;

    @Autowired
    IIntentMappingService intentMappingService;

    @Override
    public void saveRootIntent(IntentDTO intentDTO, Conversation conversation) {
        Intent intentToSave=intentMappingService.mapIntentDTOtoEntity(intentDTO,conversation);
        intentRepository.save(intentToSave);
    }

    @Override
    public void deleteRootIntent(Intent intent) {
        intentRepository.delete(intent);
    }

    @Override
    public void deleteRootIntentByConversation(Conversation conversation) {
        Intent intent=intentRepository.findByConversationByConversationIdAndRootIsTrue(conversation).get(0);
        intentRepository.delete(intent);
    }

    @Override
    public IntentDTO getRootIntentByConversationHash(String hash) {
        Conversation conversation=conversationRepository.findByHash(hash);
        Intent intent=intentRepository.findByConversationByConversationIdAndRootIsTrue(conversation).get(0);
        return intentMappingService.mapIntentEntityToDTO(intent);
    }






}
