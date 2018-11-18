package com.daniel.popek.thesis.app.service.mappers.implementation;

import com.daniel.popek.thesis.app.model.DTO.comunication.ContextDTO;
import com.daniel.popek.thesis.app.model.classification.ClassificationInputIntent;
import com.daniel.popek.thesis.app.model.classification.ClassificationQuery;
import com.daniel.popek.thesis.app.model.classification.ClassificationResult;
import com.daniel.popek.thesis.app.model.entities.Intent;
import com.daniel.popek.thesis.app.model.entities.Trainingsample;
import com.daniel.popek.thesis.app.service.data.IIntentService;
import com.daniel.popek.thesis.app.service.mappers.ICommunicationMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class CommunicationMappingService implements ICommunicationMappingService{

    @Autowired
    IIntentService intentService;

    @Override
    public ClassificationQuery mapContextToQuery(ContextDTO context, String conversationHash) {
        Intent baseIntent=intentService.getIntentOrRootByHash(context.getIntentHash(),conversationHash);
        ClassificationQuery query= new ClassificationQuery();
        query.setSentence(context.getMessage());
        query.setIntents(new ArrayList<>());
        Collection<Intent> subintents=baseIntent.getIntentsById();
        for (Intent subintent:subintents
             ) {
            ClassificationInputIntent inputIntent= new ClassificationInputIntent();
            inputIntent.setName(subintent.getName());
            inputIntent.setHash(subintent.getHash());
            inputIntent.setSentences(new ArrayList<>());
            for (Trainingsample sample:subintent.getTrainingsamplesById()
                 ) {
                inputIntent.getSentences().add(sample.getValue());
            }
            query.getIntents().add(inputIntent);
        }
        return query;
    }

    @Override
    public ContextDTO mapClassificationResultToContext(ClassificationResult result, String chosenResponse, String chosenEvent,Object inputData) {
        ContextDTO context = new ContextDTO();
        context.setEvent(chosenEvent);
        context.setMessage(chosenResponse);
        context.setData(inputData);
        context.setIntentHash(result.getIntentHash());
        return context;
    }
}
