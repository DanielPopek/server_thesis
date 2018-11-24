package com.daniel.popek.thesis.app.domain.service.mappers;

import com.daniel.popek.thesis.app.domain.DTO.design.ConversationDTO;
import com.daniel.popek.thesis.app.domain.DTO.design.ConversationListDTO;
import com.daniel.popek.thesis.app.persistence.entities.Conversation;
import org.springframework.stereotype.Service;

@Service
public interface IConversationMappingService {

    public ConversationDTO mapConversationEntityToDTO(Conversation conversation);

    public Conversation mapConversationDTOtoEntity(ConversationDTO dto,String apiKey);

    ConversationListDTO mapConversationEntityToListDTO(Conversation conversation);
}
