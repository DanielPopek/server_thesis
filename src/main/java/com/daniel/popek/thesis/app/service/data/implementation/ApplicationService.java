package com.daniel.popek.thesis.app.service.data.implementation;

import com.daniel.popek.thesis.app.model.DTO.design.ApplicationDTO;
import com.daniel.popek.thesis.app.model.entities.Application;
import com.daniel.popek.thesis.app.repository.ApplicationRepository;
import com.daniel.popek.thesis.app.repository.ApplicationConversationRepository;
import com.daniel.popek.thesis.app.service.data.IApplicationService;
import com.daniel.popek.thesis.app.service.mappers.IApplicationMappingService;
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
public class ApplicationService implements IApplicationService {

    @Autowired
    IApplicationMappingService applicationMappingService;

    @Autowired
    IHashingService hashingService;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    ApplicationConversationRepository applicationConversationRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException.class)
    @Override
    public void saveApplication(ApplicationDTO dto) {
        Application entity=applicationMappingService.mapApplicationDTOtoEntity(dto);
        entity.setDesignerId(1);
        entity.setToken(hashingService.createHash(entity));
        entity.setRegistrationDate(Timestamp.valueOf(LocalDateTime.now()));
        entity.setLastModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        applicationRepository.save(entity);
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
            dtos.add(applicationMappingService.mapApplicationEntityTODTO(entity));
        }
        return dtos;
    }
}
