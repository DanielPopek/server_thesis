package com.daniel.popek.thesis.app.service.mappers.implementation;

import com.daniel.popek.thesis.app.model.DTO.design.IntentDTO;
import com.daniel.popek.thesis.app.model.entities.*;
import com.daniel.popek.thesis.app.service.mappers.IIntentMappingService;
import com.daniel.popek.thesis.app.service.utils.IHashingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class IntentMappingService implements IIntentMappingService {

    @Autowired
    IHashingService hashingService;

    @Override
    public Intent mapIntentDTOtoEntity(IntentDTO dto, Conversation conversation)
    {
        Intent root= mapIntentDTOtoEntityRecursive(dto,null,conversation);
        root.setRoot(true);
        return root;
    }

    @Override
    public IntentDTO mapIntentEntityToDTO(Intent intent)
    {
        IntentDTO dto = new IntentDTO();
        dto.setName(intent.getName());
        dto.setEvents(mapEventEntitiesToDTOs(intent.getEventByEventId()));
        dto.setTrainingSamples(mapTrainingSampleEntitiesToDTOs(intent.getTrainingsamplesById()));
        dto.setAnswerSamples(mapAnswerEntitiesToDTOs(intent.getAnswersamplesById()));
        dto.setMisunderstandingStatements(mapMisunderstandingsEntitiesToDTOs(intent.getMisunderstandingStatementsById()));
        List<IntentDTO> subintents= new ArrayList<>();
        for (Intent subintent:intent.getIntentsById()
             ) {
            IntentDTO subintentDTO=mapIntentEntityToDTO(subintent);
            subintents.add(subintentDTO);
        }
        dto.setSubintents(subintents);
        return dto;
    }

    private Intent mapIntentDTOtoEntityRecursive(IntentDTO dto, Intent parentIntent, Conversation conversation) {
        Intent intent= new Intent();
        intent.setName(dto.getName());
        intent.setHash(hashingService.createHash(intent));
        intent.setConversationByConversationId(conversation);
        intent.setEventByEventId(mapEventsToEntities(dto.getEvents(),intent));
        intent.setAnswersamplesById(mapAnswersToEntities(dto.getAnswerSamples(),intent));
        intent.setTrainingsamplesById(mapTrainingSamplesToEntities(dto.getTrainingSamples(),intent));
        intent.setMisunderstandingStatementsById(mapMisunderstandingsDTOsToEntities(dto.getMisunderstandingStatements(),intent));
        intent.setIntentByIntentId(parentIntent);
        List<Intent> children= new ArrayList<>();
        if(dto.getSubintents()!=null)
        for (IntentDTO subintent:dto.getSubintents()
             ) {
            Intent child=mapIntentDTOtoEntityRecursive(subintent,intent,conversation);
            children.add(child);
        }
        intent.setIntentsById(children);

        return intent;
    }


    private List<MisunderstandingStatement> mapMisunderstandingsDTOsToEntities(List<String> misunderstandings, Intent intent)
    {
        List<MisunderstandingStatement> misunderstandingList=new ArrayList<>();
        if(misunderstandings!=null) {
            for (String misunderstanding : misunderstandings
                    ) {
                MisunderstandingStatement sample = new MisunderstandingStatement();
                sample.setIntentByIntentId(intent);
                sample.setValue(misunderstanding);
                misunderstandingList.add(sample);
            }
        }
        return misunderstandingList;
    }

    private List<String> mapMisunderstandingsEntitiesToDTOs(Collection<MisunderstandingStatement> misunderstandings)
    {
        List<String> answersList=new ArrayList<>();
        if(misunderstandings!=null)
        {
            for (MisunderstandingStatement sample:misunderstandings
                    ) {
                answersList.add(sample.getValue());
            }
        }
        return answersList;
    }


    private List<Answersample> mapAnswersToEntities(List<String> answers, Intent intent)
    {
        List<Answersample> answersList=new ArrayList<>();
        if(answers!=null) {
            for (String answerString : answers
                    ) {
                Answersample sample = new Answersample();
                sample.setIntentByIntentId(intent);
                sample.setValue(answerString);
                answersList.add(sample);
            }
        }
        return answersList;
    }

    private List<String> mapAnswerEntitiesToDTOs(Collection<Answersample> answers)
    {
        List<String> answersList=new ArrayList<>();
        if(answers!=null)
        {
            for (Answersample answersample:answers
                    ) {
                answersList.add(answersample.getValue());
            }
        }
        return answersList;
    }

    private List<Trainingsample> mapTrainingSamplesToEntities(List<String> trainingSamples, Intent intent)
    {
        List<Trainingsample> trainingsampleList=new ArrayList<>();
        if(trainingSamples!=null)
        {
            for (String value:trainingSamples
                    ) {
                System.out.println(value);
                Trainingsample sample=new Trainingsample();
                sample.setIntentByIntentId(intent);
                sample.setValue(value);
                trainingsampleList.add(sample);
            }
        }

        return trainingsampleList;
    }

    private List<String> mapTrainingSampleEntitiesToDTOs(Collection<Trainingsample> trainingSamples)
    {
        List<String> sampleList=new ArrayList<>();
        if(trainingSamples!=null) {
            for (Trainingsample trainingSample : trainingSamples
                    ) {
                sampleList.add(trainingSample.getValue());
            }
        }
        return sampleList;
    }

    private List<Event> mapEventsToEntities(List<String> events, Intent intent)
    {
        List<Event> eventList=new ArrayList<>();
        if(events!=null) {
            for (String value : events
                    ) {
                Event event = new Event();
                event.setIntentByIntentId(intent);
                event.setName(value);
                eventList.add(event);
            }
        }
        return eventList;
    }

    private List<String> mapEventEntitiesToDTOs(Collection<Event> events)
    {
        List<String> eventList=new ArrayList<>();
        if(events!=null) {
            for (Event event : events
                    ) {
                eventList.add(event.getName());
            }
        }
        return eventList;
    }


}
