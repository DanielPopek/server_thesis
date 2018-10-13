package com.daniel.popek.thesis.app.service.mappers.implementation;

import com.daniel.popek.thesis.app.model.DTO.design.ApplicationDTO;
import com.daniel.popek.thesis.app.model.entities.Application;
import com.daniel.popek.thesis.app.service.mappers.IApplicationMappingService;
import org.springframework.stereotype.Service;

@Service
public class ApplicationMappingService implements IApplicationMappingService {

    //maps in oder to create a new application
    @Override
    public Application mapApplicationDTOtoEntity(ApplicationDTO dto) {
        Application application= new Application();
        application.setName(dto.getName());
        application.setDescription(dto.getDescription());
        return application;
    }

    //maps in order to display appliction info
    //TODO requires improvement when OAuth is attached
    @Override
    public ApplicationDTO mapApplicationEntityTODTO(Application entity) {
        ApplicationDTO dto= new ApplicationDTO();
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setToken(entity.getToken());
        dto.setDate(entity.getDate().toLocalDateTime().toString());
        dto.setActive(entity.getActive());
        return dto;
    }
}
