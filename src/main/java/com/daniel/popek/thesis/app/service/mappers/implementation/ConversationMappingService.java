package com.daniel.popek.thesis.app.service.mappers.implementation;

import com.daniel.popek.thesis.app.model.DTO.design.ConversationDTO;
import com.daniel.popek.thesis.app.model.entities.Conversation;
import com.daniel.popek.thesis.app.repository.IntentRepository;
import com.daniel.popek.thesis.app.service.mappers.IConversationMappingService;
import com.daniel.popek.thesis.app.service.mappers.IIntentMappingService;
import com.daniel.popek.thesis.app.service.utils.IHashingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationMappingService implements IConversationMappingService {

    @Autowired
    IIntentMappingService intentMappingService;

    @Autowired
    IntentRepository intentRepository;

    @Autowired
    IHashingService hashingService;


    @Override
    public ConversationDTO mapConversationEntityToDTO(Conversation conversation) {
        ConversationDTO dto = new ConversationDTO();
        dto.setConversationId(conversation.getId());
        dto.setConversationHash(conversation.getHash());
        dto.setName(conversation.getName());
        try{
            dto.setRoot(intentMappingService.mapIntentEntityToDTO(intentRepository.findByConversationByConversationIdAndRootIsTrue(conversation).get(0)));
        }catch (Exception ex)
        {
         dto.setRoot(null);
        }
        return dto;
    }

    @Override
    public Conversation mapConversationDTOtoEntity(ConversationDTO dto) {
        Conversation conversation= new Conversation();
        conversation.setName(dto.getName());
        conversation.setHash(hashingService.createHash(conversation));
        conversation.setDesigner_id(1);
        return conversation;
    }
}
