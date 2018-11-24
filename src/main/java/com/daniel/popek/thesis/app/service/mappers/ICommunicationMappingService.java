package com.daniel.popek.thesis.app.service.mappers;

import com.daniel.popek.thesis.app.model.DTO.comunication.ContextDTO;
import com.daniel.popek.thesis.app.model.classification.ClassificationQuery;
import com.daniel.popek.thesis.app.model.classification.ClassificationResult;
import com.daniel.popek.thesis.app.model.entities.Intent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICommunicationMappingService {

     //changes a list of intents potentially taken into account to query containig subintents
     ClassificationQuery  mapIntentsToQuery(List<Intent> baseIntents, String message);

     ContextDTO mapClassificationResultToContext(String intentHash, String chosenResponse, List<String> events, Object inputData);
}