package com.daniel.popek.thesis.app.service.communication.implementation;

import com.daniel.popek.thesis.app.model.DTO.comunication.ContextDTO;
import com.daniel.popek.thesis.app.model.classification.ClassificationQuery;
import com.daniel.popek.thesis.app.model.classification.ClassificationResult;
import com.daniel.popek.thesis.app.model.entities.Answersample;
import com.daniel.popek.thesis.app.model.entities.Event;
import com.daniel.popek.thesis.app.model.entities.Intent;
import com.daniel.popek.thesis.app.service.communication.ICommunicationService;
import com.daniel.popek.thesis.app.service.data.IIntentService;
import com.daniel.popek.thesis.app.service.mappers.ICommunicationMappingService;
import com.daniel.popek.thesis.app.service.ml_classifier.IClassifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CommunicationService implements ICommunicationService {

    @Autowired
    IClassifierService classifierService;

    @Autowired
    ICommunicationMappingService communicationMappingService;

    @Autowired
    IIntentService intentService;

    @Override
    public ContextDTO respond(ContextDTO context) {
        Random random = new Random();
        ClassificationQuery query=communicationMappingService.mapContextToQuery(context);
        ClassificationResult result=classifierService.classify(context.getMessage(),query.getIntents());
        Intent intent=intentService.getIntentOrRootByHash(result.getIntentHash(),context.getConversationHash());
        String answer="";
        if(intent.getAnswersamplesById().size()!=0)
        {
            int answerIndex=random.nextInt(intent.getAnswersamplesById().size());
            answer=((Answersample)(intent.getAnswersamplesById().toArray()[answerIndex])).getValue();
        }
        String event="";
        if(intent.getEventByEventId().size()!=0)
        {
            int eventIndex=random.nextInt(intent.getEventByEventId().size());
            event=((Event)intent.getEventByEventId().toArray()[eventIndex]).getName();
        }
        if(intent.getIntentsById()==null||intent.getIntentsById().size()==0)
            result.setIntentHash("");
        ContextDTO response=communicationMappingService.mapClassificationResultToContext(result,context.getConversationHash()
                ,answer,event,context.getData());
        return response;
    }
}
