package com.daniel.popek.thesis.app.service.data.implementation;

import com.daniel.popek.thesis.app.model.DTO.design.ConversationDTO;
import com.daniel.popek.thesis.app.model.DTO.design.ConversationListDTO;
import com.daniel.popek.thesis.app.model.DTO.design.ConversationNamesHashesDTO;
import com.daniel.popek.thesis.app.model.DTO.design.IntentDTO;
import com.daniel.popek.thesis.app.model.entities.Conversation;
import com.daniel.popek.thesis.app.model.entities.Designer;
import com.daniel.popek.thesis.app.repository.ConversationRepository;
import com.daniel.popek.thesis.app.repository.ApplicationConversationRepository;
import com.daniel.popek.thesis.app.repository.DesignerRepository;
import com.daniel.popek.thesis.app.service.data.IConversationService;
import com.daniel.popek.thesis.app.service.data.IIntentService;
import com.daniel.popek.thesis.app.service.mappers.implementation.ConversationMappingService;
import com.daniel.popek.thesis.app.service.utils.IHashingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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

    @Autowired
    ApplicationConversationRepository applicationConversationRepository;

    @Autowired
    DesignerRepository designerRepository;


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

    @Override
    public List<ConversationListDTO> readListConversationsByDesignerHash(String hash) {
        Designer designer=designerRepository.findByApiKey(hash);
        List<Conversation> conversations=conversationRepository.findAllByDesignerId(designer.getId());
        List<ConversationListDTO> toReturn= new ArrayList<>();
        if(conversations!=null&&conversations.size()>0)
            for (Conversation conversation:conversations
                    ) {
                toReturn.add(conversationMappingService.mapConversationEntityToListDTO(conversation));
            }
        return toReturn;
    }

    @Override
    public ConversationNamesHashesDTO readConversationNamesAndHashesByDesignerHash(String hash) {
        Designer designer=designerRepository.findByApiKey(hash);
        List<Conversation> conversations=conversationRepository.findAllByDesignerId(designer.getId());
        List<String> names= new ArrayList<>();
        List<String> hashes= new ArrayList<>();
        ConversationNamesHashesDTO dto= new ConversationNamesHashesDTO();
        if(conversations!=null&&conversations.size()>0){
            for (Conversation conversation:conversations
                    ) {
                names.add(conversation.getName());
                hashes.add(conversation.getHash());
            }
        }
        dto.setNames(names);
        dto.setHashes(hashes);
        return dto;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException.class)
    @Override
    public void saveConversationDTO(ConversationDTO dto,String header) {
        Conversation conversationEntity=conversationRepository.findByHash(dto.getConversationHash());
        if(conversationEntity==null)
        {
            conversationEntity=conversationMappingService.mapConversationDTOtoEntity(dto,header);
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
            applicationConversationRepository.deleteAllByConversationId(conversationEntity.getId());
            intentService.deleteRootIntentByConversation(conversationEntity);
            conversationRepository.delete(conversationEntity);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException.class)
    @Override
    public void saveNewConversationDTO(ConversationListDTO conversationDTO,String header) {
        Designer designer=designerRepository.findByApiKey(header);
        Conversation conversation=new Conversation();
        conversation.setHash(hashingService.createHash(conversation));
        conversation.setName(conversationDTO.getName());
        conversation.setDescription(conversationDTO.getDescription());
        conversation.setLastModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        conversation.setRegistrationDate(Timestamp.valueOf(LocalDateTime.now()));
        conversation.setDesignerId(designer.getId());
        conversation=conversationRepository.save(conversation);
        IntentDTO root= new IntentDTO();
        root.setName("root");
        intentService.saveRootIntent(root,conversation);
    }

    @Override
    public void editConversation(ConversationListDTO conversationDTO) {
        Conversation conversation=conversationRepository.findByHash(conversationDTO.getConversationHash());
        conversation.setLastModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        conversation.setName(conversationDTO.getName());
        conversation.setDescription(conversationDTO.getDescription());
        conversationRepository.save(conversation);
    }


}
