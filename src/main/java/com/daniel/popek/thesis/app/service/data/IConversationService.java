package com.daniel.popek.thesis.app.service.data;

import com.daniel.popek.thesis.app.model.DTO.design.ConversationDTO;
import com.daniel.popek.thesis.app.model.DTO.design.ConversationListDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/*
This service is used to extract/update Conversation entities from/in database
 */
@Service
public interface IConversationService {

    public ConversationDTO readConversationById(int id);

    public ConversationDTO readConversationByHash(String hash);

    List<ConversationListDTO> readListConversationsByDeveloperId(Integer id);

    public void saveConversationDTO(ConversationDTO dto);

    public void deleteConversation(String hash);

    public void saveNewConversationDTO(ConversationListDTO conversationDTO);

}
