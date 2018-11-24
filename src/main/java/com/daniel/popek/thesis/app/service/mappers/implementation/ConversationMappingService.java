package com.daniel.popek.thesis.app.service.mappers.implementation;

import com.daniel.popek.thesis.app.model.DTO.design.ConversationDTO;
import com.daniel.popek.thesis.app.model.DTO.design.ConversationListDTO;
import com.daniel.popek.thesis.app.model.entities.Conversation;
import com.daniel.popek.thesis.app.model.entities.Designer;
import com.daniel.popek.thesis.app.repository.DesignerRepository;
import com.daniel.popek.thesis.app.repository.IntentRepository;
import com.daniel.popek.thesis.app.service.data.IDesignerService;
import com.daniel.popek.thesis.app.service.mappers.IConversationMappingService;
import com.daniel.popek.thesis.app.service.mappers.IIntentMappingService;
import com.daniel.popek.thesis.app.service.utils.IHashingService;
import org.assertj.core.util.Strings;
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

    @Autowired
    DesignerRepository designerRepository;


    @Override
    public ConversationDTO mapConversationEntityToDTO(Conversation conversation) {
        ConversationDTO dto = new ConversationDTO();
        dto.setConversationId(conversation.getId());
        dto.setConversationHash(conversation.getHash());
        dto.setName(conversation.getName());
        dto.setLastModificationDate(conversation.getLastModificationDate().toString());
        dto.setCreationDate(conversation.getRegistrationDate().toString());
        try{
            dto.setRoot(intentMappingService.mapIntentEntityToDTO(intentRepository.findByConversationByConversationIdAndRootIsTrue(conversation).get(0)));
        }catch (Exception ex)
        {
         dto.setRoot(null);
        }
        return dto;
    }

    @Override
    public Conversation mapConversationDTOtoEntity(ConversationDTO dto,String apiKey) {
        Designer designer=designerRepository.findByApiKey(apiKey);
        Conversation conversation= new Conversation();
        conversation.setName(dto.getName());
        conversation.setHash(hashingService.createHash(conversation));
        conversation.setDesignerId(designer.getId());
        return conversation;
    }

    @Override
    public ConversationListDTO mapConversationEntityToListDTO(Conversation conversation) {
        ConversationListDTO dto = new ConversationListDTO();
        dto.setConversationId(conversation.getId());
        dto.setConversationHash(conversation.getHash());
        dto.setName(conversation.getName());
        dto.setDescription(conversation.getDescription());
        dto.setLastModificationDate((conversation.getLastModificationDate()==null?"":conversation.getLastModificationDate().toString()));
        dto.setCreationDate(conversation.getRegistrationDate()==null?"":conversation.getRegistrationDate().toString());
        return dto;
    }

}
