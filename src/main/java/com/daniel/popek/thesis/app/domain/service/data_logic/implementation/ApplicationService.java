package com.daniel.popek.thesis.app.domain.service.data_logic.implementation;

import com.daniel.popek.thesis.app.domain.DTO.design.ApplicationDTO;
import com.daniel.popek.thesis.app.persistence.entities.Application;
import com.daniel.popek.thesis.app.persistence.entities.ApplicationConversation;
import com.daniel.popek.thesis.app.persistence.entities.Conversation;
import com.daniel.popek.thesis.app.persistence.entities.Designer;
import com.daniel.popek.thesis.app.persistence.repository.ApplicationRepository;
import com.daniel.popek.thesis.app.persistence.repository.ApplicationConversationRepository;
import com.daniel.popek.thesis.app.persistence.repository.ConversationRepository;
import com.daniel.popek.thesis.app.persistence.repository.DesignerRepository;
import com.daniel.popek.thesis.app.domain.service.data_logic.IApplicationService;
import com.daniel.popek.thesis.app.domain.service.mappers.IApplicationMappingService;
import com.daniel.popek.thesis.app.domain.service.utils.IHashingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationService implements IApplicationService {

    @Autowired
    IApplicationMappingService applicationMappingService;

    @Autowired
    IHashingService hashingService;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    ConversationRepository conversationRepository;

    @Autowired
    ApplicationConversationRepository applicationConversationRepository;

    @Autowired
    DesignerRepository designerRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException.class)
    @Override
    public void saveApplication(ApplicationDTO dto,String header) {
        Designer designer=designerRepository.findByApiKey(header);
        Application entity=applicationMappingService.mapApplicationDTOtoEntity(dto);
        entity.setDesignerId(designer.getId());
        entity.setToken(hashingService.createHash(entity));
        entity.setRegistrationDate(Timestamp.valueOf(LocalDateTime.now()));
        entity.setLastModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        Application savedApplication=applicationRepository.save(entity);
        for (String conversationHash: dto.getHashes()
             ) {
            Conversation conversation=conversationRepository.findByHash(conversationHash);
            ApplicationConversation applicationConversation= new ApplicationConversation();
            applicationConversation.setApplicationId(savedApplication.getId());
            applicationConversation.setConversationId(conversation.getId());
            applicationConversationRepository.save(applicationConversation);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException.class)
    @Override
    public void deleteApplication(ApplicationDTO dto) {
       Application application=applicationRepository.findApplicationByToken(dto.getToken());
       if(application!=null)
       {
           applicationConversationRepository.deleteAllByApplicationId(application.getId());
           applicationRepository.delete(application);
       }
       }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException.class)
    @Override
    public void deleteApplicationByToken(String token) {
        Application application=applicationRepository.findApplicationByToken(token);
        if(application!=null)
        {
            applicationConversationRepository.deleteAllByApplicationId(application.getId());
            applicationRepository.delete(application);
        }
    }

    @Override
    public List<ApplicationDTO> getAllByDesignerId(Integer id) {
        List<ApplicationDTO> dtos= new ArrayList<>();
        List<Application> entities=applicationRepository.findAllByDesignerId(id);
        for (Application entity: entities
             ) {
            dtos.add(applicationMappingService.mapApplicationEntityTODTO(entity,id));
        }
        return dtos;
    }

    @Override
    public List<ApplicationDTO> getAllByDesignerHash(String hash) {
        List<ApplicationDTO> dtos= new ArrayList<>();
        Designer designer=designerRepository.findByApiKey(hash);
        List<Application> entities=applicationRepository.findAllByDesignerId(designer.getId());
        for (Application entity: entities
                ) {
            dtos.add(applicationMappingService.mapApplicationEntityTODTO(entity,designer.getId()));
        }
        return dtos;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException.class)
    @Override
    public void editApplication(ApplicationDTO applicationDTO) {
        Application entity=applicationRepository.findApplicationByToken(applicationDTO.getToken());
        entity.setLastModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        entity.setName(applicationDTO.getName());
        entity.setDescription(applicationDTO.getDescription());
        applicationRepository.save(entity);
        applicationConversationRepository.deleteAllByApplicationId(entity.getId());

        for (String conversationHash: applicationDTO.getHashes()
                ) {
            Conversation conversation=conversationRepository.findByHash(conversationHash);
            ApplicationConversation applicationConversation= new ApplicationConversation();
            applicationConversation.setApplicationId(entity.getId());
            applicationConversation.setConversationId(conversation.getId());
            applicationConversationRepository.save(applicationConversation);
        }
    }
}
