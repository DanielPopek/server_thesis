package com.daniel.popek.thesis.app.domain.service.mappers;

import com.daniel.popek.thesis.app.domain.DTO.design.ApplicationDTO;
import com.daniel.popek.thesis.app.persistence.entities.Application;
import org.springframework.stereotype.Service;

@Service
public interface IApplicationMappingService {

    public Application mapApplicationDTOtoEntity(ApplicationDTO dto);
    public ApplicationDTO mapApplicationEntityTODTO(Application entity, int designerId);
}
