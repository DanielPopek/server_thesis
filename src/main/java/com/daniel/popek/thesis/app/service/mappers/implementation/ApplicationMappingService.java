package com.daniel.popek.thesis.app.service.mappers.implementation;

import com.daniel.popek.thesis.app.model.DTO.design.ApplicationDTO;
import com.daniel.popek.thesis.app.model.entities.Application;
import com.daniel.popek.thesis.app.model.entities.ApplicationConversation;
import com.daniel.popek.thesis.app.model.entities.Conversation;
import com.daniel.popek.thesis.app.repository.ConversationRepository;
import com.daniel.popek.thesis.app.repository.ApplicationConversationRepository;
import com.daniel.popek.thesis.app.service.mappers.IApplicationMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        dto.setDate(entity.getRegistrationDate().toLocalDateTime().toString());
        dto.setLastModificationDate(entity.getLastModificationDate()==null?"":entity.getLastModificationDate().toLocalDateTime().toString());
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
