package com.daniel.popek.thesis.app.service.mappers.implementation;

import com.daniel.popek.thesis.app.model.DTO.design.EventDTO;
import com.daniel.popek.thesis.app.model.entities.Event;
import com.daniel.popek.thesis.app.service.mappers.IEventMapingService;
import org.springframework.stereotype.Service;

@Service
public class EventMappingService implements IEventMapingService{

    @Override
    public EventDTO mapEventEntityToDTO(Event event) {
        EventDTO dto= new EventDTO();
        dto.setName(event.getName());
        dto.setEventMessage(event.getMessage());
        return dto;
    }

    @Override
    public Event mapEventDTOtoEntity(EventDTO eventDTO) {
        Event event= new Event();
        event.setName(eventDTO.getName());
        event.setMessage(eventDTO.getEventMessage());
        return event;
    }
}
