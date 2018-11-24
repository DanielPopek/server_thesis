package com.daniel.popek.thesis.app.domain.service.mappers;

import com.daniel.popek.thesis.app.domain.DTO.comunication.ContextDTO;
import com.daniel.popek.thesis.app.domain.model.ClassificationQuery;
import com.daniel.popek.thesis.app.persistence.entities.Intent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICommunicationMappingService {

     //changes a list of intents potentially taken into account to query containig subintents
     ClassificationQuery  mapIntentsToQuery(List<Intent> baseIntents, String message);

     ContextDTO mapClassificationResultToContext(String intentHash, String chosenResponse, List<String> events, Object inputData);
}