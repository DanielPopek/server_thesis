package com.daniel.popek.thesis.app.service.data.implementation;

import com.daniel.popek.thesis.app.model.DTO.design.ConversationDTO;
import com.daniel.popek.thesis.app.model.DTO.design.ConversationListDTO;
import com.daniel.popek.thesis.app.model.DTO.design.IntentDTO;
import com.daniel.popek.thesis.app.model.entities.Conversation;
import com.daniel.popek.thesis.app.model.entities.Intent;
import com.daniel.popek.thesis.app.repository.ConversationRepository;
import com.daniel.popek.thesis.app.service.data.IConversationService;
import com.daniel.popek.thesis.app.service.data.IIntentService;
import com.daniel.popek.thesis.app.service.mappers.implementation.ConversationMappingService;
import com.daniel.popek.thesis.app.service.utils.IHashingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationService implements IConversationService{

    @Autowired
    ConversationRepository conversationRepository;

    @Autowired
    ConversationMappingService conversationMappingService;

    @Autowired
    IIntentService intentService;

    @Autowired
    IHashingService hashingService;


    @Override
    public ConversationDTO readConversationById(int id) {
        return conversationMappingService.mapConversationEntityToDTO(conversationRepository.findById(id));
    }

    @Override
    public ConversationDTO readConversationByHash(String hash) {
        return conversationMappingService.mapConversationEntityToDTO(conversationRepository.findByHash(hash));
    }

    @Override
    public List<ConversationListDTO> readListConversationsByDeveloperId(Integer id) {
        List<Conversation> conversations=conversationRepository.findAllByDesignerId(id);
        List<ConversationListDTO> toReturn= new ArrayList<>();
        if(conversations!=null&&conversations.size()>0)
            for (Conversation conversation:conversations
                 ) {
                toReturn.add(conversationMappingService.mapConversationEntityToListDTO(conversation));
            }
        return toReturn;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException.class)
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

    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException.class)
    @Override
    public void deleteConversation(String hash) {
        Conversation conversationEntity=conversationRepository.findByHash(hash);
        if(conversationEntity!=null)
        {
            intentService.deleteRootIntentByConversation(conversationEntity);
            conversationRepository.delete(conversationEntity);
        }
    }

    //TODO improve when desigers are distinguished
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException.class)
    @Override
    public void saveNewConversationDTO(ConversationListDTO conversationDTO) {
        Conversation conversation=new Conversation();
        conversation.setHash(hashingService.createHash(conversation));
        conversation.setName(conversationDTO.getName());
        conversation.setDescription(conversationDTO.getDescription());
        conversation.setDesignerId(1);
        conversation=conversationRepository.save(conversation);
        IntentDTO root= new IntentDTO();
        root.setName("root");
        intentService.saveRootIntent(root,conversation);
    }


}
