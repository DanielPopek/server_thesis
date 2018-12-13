package com.daniel.popek.thesis.app.domain.service.mappers.implementation;

import com.daniel.popek.thesis.app.domain.DTO.design.ConversationDTO;
import com.daniel.popek.thesis.app.domain.DTO.design.ConversationListDTO;
import com.daniel.popek.thesis.app.persistence.entities.Conversation;
import com.daniel.popek.thesis.app.persistence.entities.Designer;
import com.daniel.popek.thesis.app.persistence.repository.DesignerRepository;
import com.daniel.popek.thesis.app.persistence.repository.IntentRepository;
import com.daniel.popek.thesis.app.domain.service.mappers.IConversationMappingService;
import com.daniel.popek.thesis.app.domain.service.mappers.IIntentMappingService;
import com.daniel.popek.thesis.app.domain.service.utils.IHashingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

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
        dto.setLastModificationDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(conversation.getLastModificationDate()));
        dto.setCreationDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(conversation.getRegistrationDate()));
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
        dto.setLastModificationDate((conversation.getLastModificationDate()==null?"":new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(conversation.getLastModificationDate())));
        dto.setCreationDate(conversation.getRegistrationDate()==null?"":new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(conversation.getRegistrationDate()));
        return dto;
    }

}
