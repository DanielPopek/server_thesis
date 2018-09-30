package com.daniel.popek.thesis.app.service.mappers;

import com.daniel.popek.thesis.app.model.DTO.design.IntentDTO;
import com.daniel.popek.thesis.app.model.entities.Conversation;
import com.daniel.popek.thesis.app.model.entities.Intent;
import org.springframework.stereotype.Service;

@Service
public interface IIntentMappingService {

    Intent mapIntentDTOtoEntity(IntentDTO dto, Conversation conversation);

    IntentDTO mapIntentEntityToDTO(Intent intent);
}
