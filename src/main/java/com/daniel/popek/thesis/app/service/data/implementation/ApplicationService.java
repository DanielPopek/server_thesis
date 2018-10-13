package com.daniel.popek.thesis.app.service.data.implementation;

import com.daniel.popek.thesis.app.model.DTO.design.ApplicationDTO;
import com.daniel.popek.thesis.app.model.entities.Application;
import com.daniel.popek.thesis.app.repository.ApplicationRepository;
import com.daniel.popek.thesis.app.service.data.IApplicationService;
import com.daniel.popek.thesis.app.service.mappers.IApplicationMappingService;
import com.daniel.popek.thesis.app.service.utils.IHashingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public void saveApplication(ApplicationDTO dto) {
        Application entity=applicationMappingService.mapApplicationDTOtoEntity(dto);
        entity.setDesignerId(1);
        entity.setActive(true);
        entity.setToken(hashingService.createHash(entity));
        entity.setDate(Timestamp.valueOf(LocalDateTime.now()));
        applicationRepository.save(entity);
    }

    @Override
    public void deleteApplication(ApplicationDTO dto) {
       Application application=applicationRepository.findApplicationByToken(dto.getToken());
       if(application!=null)
           applicationRepository.delete(application);
    }

    @Override
    public void deleteApplicationByToken(String token) {
        Application application=applicationRepository.findApplicationByToken(token);
        if(application!=null)
            applicationRepository.delete(application);
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
