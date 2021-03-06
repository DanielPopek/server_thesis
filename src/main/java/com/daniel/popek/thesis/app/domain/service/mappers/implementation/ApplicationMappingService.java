package com.daniel.popek.thesis.app.domain.service.mappers.implementation;

import com.daniel.popek.thesis.app.domain.DTO.design.ApplicationDTO;
import com.daniel.popek.thesis.app.persistence.entities.Application;
import com.daniel.popek.thesis.app.persistence.entities.ApplicationConversation;
import com.daniel.popek.thesis.app.persistence.entities.Conversation;
import com.daniel.popek.thesis.app.persistence.repository.ConversationRepository;
import com.daniel.popek.thesis.app.persistence.repository.ApplicationConversationRepository;
import com.daniel.popek.thesis.app.domain.service.mappers.IApplicationMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationMappingService implements IApplicationMappingService {

    @Autowired
    ApplicationConversationRepository applicationConversationRepository;

    @Autowired
    ConversationRepository conversationRepository;

    //maps in oder to create a new application
    @Override
    public Application mapApplicationDTOtoEntity(ApplicationDTO dto) {
        Application application= new Application();
        application.setName(dto.getName());
        application.setDescription(dto.getDescription());
        return application;
    }

    //maps in order to display appliction info
    @Override
    public ApplicationDTO mapApplicationEntityTODTO(Application entity, int designerId) {
        ApplicationDTO dto= new ApplicationDTO();
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setToken(entity.getToken());
        dto.setDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(entity.getRegistrationDate()));
        dto.setLastModificationDate(entity.getLastModificationDate()==null?"":new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(entity.getLastModificationDate()));
        List<String> conversations = new ArrayList<>();
        for (ApplicationConversation pair: applicationConversationRepository.findAllByApplicationId(entity.getId())
             ) {
            conversations.add(conversationRepository.findById(pair.getConversationId()).get().getName());
        }
        List<String>designerConversationNames=new ArrayList<>();
        List<String>designerConversationHashes=new ArrayList<>();

        List<Conversation> designerConversations=conversationRepository.findAllByDesignerId(designerId);
        for (Conversation conv: designerConversations
             ) {
            designerConversationHashes.add(conv.getHash());
            designerConversationNames.add(conv.getName());
        }
        dto.setConversations(conversations);
        dto.setDesignerConversationHashes(designerConversationHashes);
        dto.setDesignerConversationNames(designerConversationNames);
        return dto;
    }
}
