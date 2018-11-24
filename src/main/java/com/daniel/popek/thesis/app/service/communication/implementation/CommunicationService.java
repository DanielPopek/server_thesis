package com.daniel.popek.thesis.app.service.communication.implementation;

import com.daniel.popek.thesis.app.component.Constants;
import com.daniel.popek.thesis.app.model.DTO.comunication.ContextDTO;
import com.daniel.popek.thesis.app.model.classification.ClassificationQuery;
import com.daniel.popek.thesis.app.model.classification.ClassificationResult;
import com.daniel.popek.thesis.app.model.entities.Answersample;
import com.daniel.popek.thesis.app.model.entities.Event;
import com.daniel.popek.thesis.app.model.entities.Intent;
import com.daniel.popek.thesis.app.model.entities.MisunderstandingStatement;
import com.daniel.popek.thesis.app.service.communication.ICommunicationService;
import com.daniel.popek.thesis.app.service.data.IIntentService;
import com.daniel.popek.thesis.app.service.mappers.ICommunicationMappingService;
import com.daniel.popek.thesis.app.service.ml_classifier.IClassifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
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
    public ContextDTO respond(ContextDTO context, String conversationHash) {


        //preparing set of potencial base intents
        Intent baseIntent=intentService.getIntentOrRootByHash(context.getIntentHash(),conversationHash);
        Intent rootIntent=intentService.getRootEntityIntentByConversationHash(conversationHash);
        List<Intent> baseIntents= new ArrayList<>();
        baseIntents.add(baseIntent);
        if(!baseIntent.getHash().equals(rootIntent.getHash())) baseIntents.add(rootIntent);
        ClassificationQuery query=communicationMappingService.mapIntentsToQuery(baseIntents,context.getMessage());

        ClassificationResult result=classifierService.classify(context.getMessage(),query.getIntents());

//        Intent intent=intentService.getIntentOrRootByHash(result.getIntentHash(),conversationHash);
//
//        String answer=overideFinalAnswerWithData(extractAnswerSample(intent),context);
//
//        List<String> events=extractEvents(intent);
//
//
//        if(intent.getIntentsById()==null||intent.getIntentsById().size()==0)
//            result.setIntentHash("");
//
//        ContextDTO response=communicationMappingService.mapClassificationResultToContext(intent,answer,
//                events,context.getData());
        return getClassificationDecision(conversationHash,result,context);
    }


    //returns message, events
    ContextDTO getClassificationDecision(String conversationHash, ClassificationResult result, ContextDTO contextDTO)
    {
       String finalAnswer="";
       List<String> events;
       String intentHash="";
       Random random=new Random();
        Intent intent=intentService.getIntentOrRootByHash(result.getIntentHash(),conversationHash);
        if(!detectNotUnderstoodStatement(result,conversationHash))
        {

            finalAnswer=extractAnswerSample(intent);

            events=extractEvents(intent);

            intentHash=intent.getHash();

            if(intent.getIntentsById()==null||intent.getIntentsById().size()==0)
                intentHash="";
        }
        else
        {
            intent=intentService.getIntentOrRootByHash(contextDTO.getIntentHash(),conversationHash);

            System.out.println(intent.getName());
            System.out.println(intent.getMisunderstandingStatementsById());

            if(intent.getMisunderstandingStatementsById().size()==0)
                intent=intentService.getRootEntityIntentByConversationHash(conversationHash);

            if(intent.getMisunderstandingStatementsById().size()>0)
            {
                int randomIndex=random.nextInt(intent.getMisunderstandingStatementsById().size());
                finalAnswer=((MisunderstandingStatement)(intent.getMisunderstandingStatementsById().toArray()[randomIndex])).getValue();
            }

            events=contextDTO.getEvents();
            intentHash=contextDTO.getIntentHash();
        }

        finalAnswer=overideFinalAnswerWithData(finalAnswer,contextDTO);


        return communicationMappingService.mapClassificationResultToContext(intentHash,finalAnswer,events,contextDTO.getData());
    }

    private boolean detectNotUnderstoodStatement(ClassificationResult result,String conversationHash)
    {
        float score=result.getScore();
        int sameScoreCounter=0;
        List<String> sameClassificationIntentHashes=new ArrayList<>();
        for (String key:result.getResultsHashes().keySet()
             ) {
            if(result.getResultsHashes().get(key).floatValue()==score){
                sameScoreCounter++;
                sameClassificationIntentHashes.add(key);
            }
        }
        if(sameScoreCounter==2)
        {
            Intent root=intentService.getRootEntityIntentByConversationHash(conversationHash);
            List<String> rootSubintents=root.getIntentsById().stream().map(intent->intent.getHash()).collect(Collectors.toList());

            System.out.println(rootSubintents);

            int rootSubintentsCount=0;
            if(rootSubintents.contains(sameClassificationIntentHashes.get(0)))rootSubintentsCount++;
            if(rootSubintents.contains(sameClassificationIntentHashes.get(1)))rootSubintentsCount++;

            System.out.println(rootSubintentsCount);

            return rootSubintentsCount!=1;
        }
        return sameScoreCounter>1;
    }

    private String extractNotUnderstoodStatement(Intent intent)
    {
        return "";
    }

    private String extractAnswerSample(Intent intent)
    {
        Random random = new Random();
        String answer="";
        if(intent.getAnswersamplesById().size()!=0)
        {
            int answerIndex=random.nextInt(intent.getAnswersamplesById().size());
            answer=((Answersample)(intent.getAnswersamplesById().toArray()[answerIndex])).getValue();
        }
        return answer;
    }

    private List<String> extractEvents(Intent intent)
    {
        List<String>events= new ArrayList<>();
        if(intent.getEventByEventId().size()!=0)
        {
            for (Event event:intent.getEventByEventId()
                 ) {
                events.add(event.getName());
            }
        }
        return events;
    }

    private String overideFinalAnswerWithData(String inputAnswer, ContextDTO context)
    {
        String answer=inputAnswer;
        if(answer.contains(Constants.INPUT_INDICATOR))
            answer=answer.replaceAll(Constants.INPUT_INDICATOR,context.getMessage());

        if(answer.contains(Constants.PARAMETER_INDICATOR))
        {
            while (answer.contains(Constants.PARAMETER_INDICATOR))
            {

                String parameterStrig;
                parameterStrig = answer.substring(answer.indexOf(Constants.PARAMETER_INDICATOR));
                parameterStrig = parameterStrig.substring(Constants.PARAMETER_INDICATOR.length(), parameterStrig.indexOf("]")+1);

                System.out.println(parameterStrig);


                String dataParamter=parameterStrig.substring(1,parameterStrig.length()-1);

                System.out.println(dataParamter);

                String value="";
                try{

                    String dataCollection=context.getData().toString();

                    String dataValue=dataCollection.substring(dataCollection.indexOf(dataParamter));

                    int index=dataValue.indexOf(',');
                    if(index<0||index<dataValue.indexOf(dataValue.substring(dataParamter.length()+1)))
                        index=dataValue.indexOf('}');
                    dataValue=dataValue.substring(dataParamter.length()+1,index);

                    value=dataValue;

                }catch (Exception ex) {ex.printStackTrace();}


                answer=answer.replaceFirst(Pattern.quote(Constants.PARAMETER_INDICATOR),"");
                answer=answer.replaceFirst(Pattern.quote(parameterStrig),value);

                System.out.println(answer);
            }
        }

        return answer;
    }




}
