package com.daniel.popek.thesis.app.domain.service.data_logic;

import com.daniel.popek.thesis.app.domain.DTO.design.ConversationDTO;
import com.daniel.popek.thesis.app.domain.DTO.design.ConversationListDTO;
import com.daniel.popek.thesis.app.domain.DTO.design.ConversationNamesHashesDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/*
This service is used to extract/update Conversation entities from/in database
 */
@Service
public interface IConversationService {

    public ConversationDTO readConversationById(int id);

    public ConversationDTO readConversationByHash(String hash) throws Exception;

    List<ConversationListDTO> readListConversationsByDeveloperId(Integer id);

    List<ConversationListDTO> readListConversationsByDesignerHash(String hash);

    ConversationNamesHashesDTO readConversationNamesAndHashesByDesignerHash(String hash);

    public void saveConversationDTO(ConversationDTO dto, String header);

    public void deleteConversation(String hash);

    public void saveNewConversationDTO(ConversationListDTO conversationDTO,String header);

    void editConversation(ConversationListDTO conversationDTO);
}
