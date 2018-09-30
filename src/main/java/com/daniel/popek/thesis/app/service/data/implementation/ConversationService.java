package com.daniel.popek.thesis.app.service.data.implementation;

import com.daniel.popek.thesis.app.model.DTO.design.ConversationDTO;
import com.daniel.popek.thesis.app.model.entities.Conversation;
import com.daniel.popek.thesis.app.repository.ConversationRepository;
import com.daniel.popek.thesis.app.service.data.IConversationService;
import com.daniel.popek.thesis.app.service.data.IIntentService;
import com.daniel.popek.thesis.app.service.mappers.implementation.ConversationMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationService implements IConversationService{

    @Autowired
    ConversationRepository conversationRepository;

    @Autowired
    ConversationMappingService conversationMappingService;

    @Autowired
    IIntentService intentService;


    @Override
    public ConversationDTO readConversationById(int id) {
        return conversationMappingService.mapConversationEntityToDTO(conversationRepository.findById(id));
    }

    @Override
    public ConversationDTO readConversationByHash(String hash) {
        return conversationMappingService.mapConversationEntityToDTO(conversationRepository.findByHash(hash));
    }

    @Override
    public void saveConversationDTO(ConversationDTO dto) {
        Conversation conversationEntity=conversationRepository.findByHash(dto.getConversationHash());
        if(conversationEntity==null)
        {
            conversationEntity=conversationMappingService.mapConversationDTOtoEntity(dto);
            conversationEntity=conversationRepository.save(conversationEntity);
        }
        else
        {
            intentService.deleteRootIntentByConversation(conversationEntity);
        }
        intentService.saveRootIntent(dto.getRoot(),conversationEntity);
    }

    @Override
    public void deleteConversation(String hash) {
        Conversation conversationEntity=conversationRepository.findByHash(hash);
        if(conversationEntity!=null)
        {
            intentService.deleteRootIntentByConversation(conversationEntity);
            conversationRepository.delete(conversationEntity);
        }
    }
}
