package com.daniel.popek.thesis.app.domain.service.mappers.implementation;

import com.daniel.popek.thesis.app.domain.DTO.comunication.ContextDTO;
import com.daniel.popek.thesis.app.domain.model.ClassificationInputIntent;
import com.daniel.popek.thesis.app.domain.model.ClassificationQuery;
import com.daniel.popek.thesis.app.persistence.entities.Intent;
import com.daniel.popek.thesis.app.persistence.entities.Trainingsample;
import com.daniel.popek.thesis.app.domain.service.data_logic.IIntentService;
import com.daniel.popek.thesis.app.domain.service.mappers.ICommunicationMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommunicationMappingService implements ICommunicationMappingService{

    @Autowired
    IIntentService intentService;

    @Override
    public ClassificationQuery mapIntentsToQuery(List<Intent> baseIntents, String message) {

        ClassificationQuery query= new ClassificationQuery();
        query.setSentence(message);
        query.setIntents(new ArrayList<>());
        Set<String> intentsSet= new TreeSet<>();
        for (Intent baseIntent:baseIntents
             ) {
            Collection<Intent> subintents = baseIntent.getIntentsById();
            for (Intent subintent : subintents
                    ) {
                ClassificationInputIntent inputIntent = new ClassificationInputIntent();
                inputIntent.setName(subintent.getName());
                inputIntent.setHash(subintent.getHash());
                inputIntent.setSentences(new ArrayList<>());
                for (Trainingsample sample : subintent.getTrainingsamplesById()
                        ) {
                    inputIntent.getSentences().add(sample.getValue());
                }
                if(!intentsSet.contains(inputIntent.getHash()))
                {
                    query.getIntents().add(inputIntent);
                    intentsSet.add(inputIntent.getHash());
                }
            }
        }
        return query;
    }

    @Override
    public ContextDTO mapClassificationResultToContext(String intentHash, String chosenResponse, List<String> events,Object inputData) {
        ContextDTO context = new ContextDTO();
        context.setEvents(events);
        context.setMessage(chosenResponse);
        context.setData(inputData);
        context.setIntentHash(intentHash);
        return context;
    }
}
