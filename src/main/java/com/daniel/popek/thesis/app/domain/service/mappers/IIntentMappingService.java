package com.daniel.popek.thesis.app.domain.service.mappers;

import com.daniel.popek.thesis.app.domain.DTO.design.IntentDTO;
import com.daniel.popek.thesis.app.persistence.entities.Conversation;
import com.daniel.popek.thesis.app.persistence.entities.Intent;
import org.springframework.stereotype.Service;

@Service
public interface IIntentMappingService {

    Intent mapIntentDTOtoEntity(IntentDTO dto, Conversation conversation);

    IntentDTO mapIntentEntityToDTO(Intent intent);
}
