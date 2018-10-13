package com.daniel.popek.thesis.app.service.mappers;

import com.daniel.popek.thesis.app.model.DTO.design.ConversationDTO;
import com.daniel.popek.thesis.app.model.DTO.design.ConversationListDTO;
import com.daniel.popek.thesis.app.model.entities.Conversation;
import org.springframework.stereotype.Service;

@Service
public interface IConversationMappingService {

    public ConversationDTO mapConversationEntityToDTO(Conversation conversation);

    public Conversation mapConversationDTOtoEntity(ConversationDTO dto);

    ConversationListDTO mapConversationEntityToListDTO(Conversation conversation);
}
