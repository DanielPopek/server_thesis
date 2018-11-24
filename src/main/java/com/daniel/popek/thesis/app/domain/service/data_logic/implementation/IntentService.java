package com.daniel.popek.thesis.app.domain.service.data_logic.implementation;

import com.daniel.popek.thesis.app.domain.DTO.design.IntentDTO;
import com.daniel.popek.thesis.app.persistence.entities.Conversation;
import com.daniel.popek.thesis.app.persistence.entities.Intent;
import com.daniel.popek.thesis.app.persistence.repository.ConversationRepository;
import com.daniel.popek.thesis.app.persistence.repository.IntentRepository;
import com.daniel.popek.thesis.app.domain.service.data_logic.IIntentService;
import com.daniel.popek.thesis.app.domain.service.mappers.IIntentMappingService;
import org.assertj.core.util.Strings;
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

    @Override
    public Intent getIntentOrRootByHash(String hash, String conversationHash) {
        Intent intent;
        if(Strings.isNullOrEmpty(hash))
        {
            Conversation conversation=conversationRepository.findByHash(conversationHash);
            intent=intentRepository.findByConversationByConversationIdAndRootIsTrue(conversation).get(0);
        }
        else
            intent=intentRepository.findByHash(hash);

        return intent;
    }

    @Override
    public Intent getRootEntityIntentByConversationHash(String hash) {
        Conversation conversation=conversationRepository.findByHash(hash);
        return intentRepository.findByConversationByConversationIdAndRootIsTrue(conversation).get(0);
    }


}
