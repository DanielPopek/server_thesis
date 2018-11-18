package com.daniel.popek.thesis.app.service.mappers;

import com.daniel.popek.thesis.app.model.DTO.comunication.ContextDTO;
import com.daniel.popek.thesis.app.model.classification.ClassificationQuery;
import com.daniel.popek.thesis.app.model.classification.ClassificationResult;
import org.springframework.stereotype.Service;

@Service
public interface ICommunicationMappingService {

     ClassificationQuery mapContextToQuery(ContextDTO context, String conversationHash);

     ContextDTO mapClassificationResultToContext(ClassificationResult result, String chosenResponse, String chosenEvent, Object inputData);
}