package com.daniel.popek.thesis.app.service.mappers;

import com.daniel.popek.thesis.app.model.DTO.design.ApplicationDTO;
import com.daniel.popek.thesis.app.model.entities.Application;
import org.springframework.stereotype.Service;

@Service
public interface IApplicationMappingService {

    public Application mapApplicationDTOtoEntity(ApplicationDTO dto);
    public ApplicationDTO mapApplicationEntityTODTO(Application entity, int designerId);
}
