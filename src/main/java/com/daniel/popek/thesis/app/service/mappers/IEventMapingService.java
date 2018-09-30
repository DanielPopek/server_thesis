package com.daniel.popek.thesis.app.service.mappers;

import com.daniel.popek.thesis.app.model.DTO.design.EventDTO;
import com.daniel.popek.thesis.app.model.entities.Event;
import org.springframework.stereotype.Service;

@Service
public interface IEventMapingService {

    EventDTO mapEventEntityToDTO(Event event);

    Event mapEventDTOtoEntity(EventDTO eventDTO);
}
