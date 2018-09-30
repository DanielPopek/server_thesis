package com.daniel.popek.thesis.app.controller;

import com.daniel.popek.thesis.app.model.DTO.design.EventDTO;
import com.daniel.popek.thesis.app.model.DTO.design.IntentDTO;
import com.daniel.popek.thesis.app.model.entities.Answersample;
import com.daniel.popek.thesis.app.model.entities.Conversation;
import com.daniel.popek.thesis.app.model.entities.Event;
import com.daniel.popek.thesis.app.model.entities.Intent;
import com.daniel.popek.thesis.app.repository.ConversationRepository;
import com.daniel.popek.thesis.app.repository.IntentRepository;
import com.daniel.popek.thesis.app.service.data.IConversationService;
import com.daniel.popek.thesis.app.service.mappers.implementation.IntentMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class SampleRestController {

    @Autowired
    IntentRepository intentRepository;

    @Autowired
    ConversationRepository conversationRepository;

    @Autowired
    IntentMappingService intentMappingService;

    @Autowired
    IConversationService conversationService;

    @RequestMapping(method = RequestMethod.GET, value = "/info")
    public ResponseEntity<String> info() {
        return new ResponseEntity<String>("OKIDOKI", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/save")
    public ResponseEntity<String> saveSampleIntent() {

        Conversation conversation= conversationRepository.findById(1);
        conversation.setHash("test_conversation");

        Intent grandson= new Intent();
        grandson.setName("sonII");

        Intent son= new Intent();
        son.setName("daughter");

        List<Answersample> answers= new ArrayList<>();
        Answersample sample= new Answersample();
        sample.setValue("TEST");
        sample.setIntentByIntentId(grandson);
        answers.add(sample);
        grandson.setAnswersamplesById(answers);

        Event event= new Event();
        event.setName("TEST");
        event.setMessage("MESSAGE");

        grandson.setEventByEventId(event);

        son.setIntentsById(new ArrayList<>());
        son.getIntentsById().add(grandson);
        grandson.setIntentByIntentId(son);

        Intent me= new Intent();
        me.setName("me");

        me.setIntentsById(new ArrayList<>());
        me.getIntentsById().add(son);
        me.setRoot(true);
        son.setIntentByIntentId(me);


        me.setConversationByConversationId(conversation);
        son.setConversationByConversationId(conversation);
        grandson.setConversationByConversationId(conversation);

        intentRepository.save(me);

        return new ResponseEntity<String>("OKIDOKI", HttpStatus.OK);
    }


    //edits by adding a new member to node children
    @RequestMapping(method = RequestMethod.GET, value = "/edit")
    public Intent editSampleIntent() {
        Intent intent=intentRepository.findById(35);

        Intent newChild= new Intent();
        newChild.setName("newChild");
        Conversation conversation= conversationRepository.findById(1);
        newChild.setConversationByConversationId(conversation);
        //newChild.setIntentByIntentId(intent);
        newChild.setIntentsById(new ArrayList<>());

        newChild=intentRepository.save(newChild);
        newChild.setIntentByIntentId(intent);

//
        Collection<Intent> subs=intent.getIntentsById();

        System.out.println(subs);
        System.out.println(subs.add(newChild));
        System.out.println(subs);

        intent.setIntentsById(subs);


        //System.out.println(intent.getIntentsById());


        //intentRepository.save(newChild);

        //intentRepository.save(intent);
        intentRepository.save(newChild);

        return intent;
    }




    //edits by adding a new member to node children
    @RequestMapping(method = RequestMethod.GET, value = "/delete/{id}")
    public Intent deleteSampleIntent(@PathVariable int id) {
        Intent intent=intentRepository.findById(id);
        intentRepository.delete(intent);

        return intent;
    }

    //saves or udates a conversation object form JSON
    @RequestMapping(method = RequestMethod.POST, value = "/parse")
    public Intent deleteSampleIntent(@PathVariable Intent intent) {

        intentRepository.save(intent);

        return intent;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity<String> findAll() {


        return new ResponseEntity<String>(intentRepository.findAll().toString(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/intent/{id}")
    public ResponseEntity<IntentDTO> findConversationRoot(@PathVariable int id) {
        Conversation conversation = conversationRepository.findById(id);
        return new ResponseEntity<IntentDTO>(intentMappingService.mapIntentEntityToDTO(intentRepository.findByConversationByConversationIdAndRootIsTrue(conversation).get(1)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/show/{id}")
    public ResponseEntity<IntentDTO> findIntentById(@PathVariable int id) {
        IntentDTO dto=intentMappingService.mapIntentEntityToDTO(intentRepository.findById(id));

        return new ResponseEntity<IntentDTO>(dto, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/all2")
    public List<Intent> findAllAsJSON() {


        return intentRepository.findAll();
    }




    @RequestMapping(method = RequestMethod.GET, value = "/dto")
    public IntentDTO getSampleDTO() {
        //intents
        IntentDTO parent=new IntentDTO();
        IntentDTO son1=new IntentDTO();
        IntentDTO son2=new IntentDTO();
        IntentDTO grandson=new IntentDTO();
        //field objects
        EventDTO event= new EventDTO();
        event.setName("sample event");
        event.setEventMessage("sample event message");

        List<String> sampleAnswers=new ArrayList<>();
        sampleAnswers.add("It's great.");
        sampleAnswers.add("Life is beautiful");
        sampleAnswers.add("Come on, don't cry");

        List<String> trainingSet=new ArrayList<>();
        trainingSet.add("How's your life");
        trainingSet.add("How is it going?");
        trainingSet.add("How are you doing?");
        //filed assigning
        parent.setName("parent");
        parent.setSubintents(new ArrayList<>());
        parent.getSubintents().add(son1);
        parent.getSubintents().add(son2);
        parent.setAnswerSamples(sampleAnswers);
        parent.setEvent(event);
        parent.setTrainingSamples(trainingSet);
        //
        son1.setName("son1");
        son1.setSubintents(new ArrayList<>());
        son1.getSubintents().add(grandson);
        son1.setAnswerSamples(sampleAnswers);
        son1.setEvent(event);
        son1.setTrainingSamples(trainingSet);
        //
        son2.setName("son2");
        son2.setSubintents(new ArrayList<>());
        son2.setAnswerSamples(sampleAnswers);
        son2.setEvent(event);
        son2.setTrainingSamples(trainingSet);
        //
        grandson.setName("grandson");
        grandson.setSubintents(new ArrayList<>());
        grandson.setAnswerSamples(sampleAnswers);
        grandson.setEvent(event);
        grandson.setTrainingSamples(trainingSet);

        return parent;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/load")
    public Intent getSampleDTO(@RequestBody IntentDTO intentDTO) {
        System.out.println(intentDTO.getTrainingSamples().size());
        Conversation conversation= conversationRepository.findById(2);
        Intent intent= intentMappingService.mapIntentDTOtoEntity(intentDTO,conversation);
        intentRepository.save(intent);
        return intent;
    }




//    @RequestMapping(method = RequestMethod.GET, value = "/info/{id}")
//    public ResponseEntity<Intent> showIntent(@PathVariable int id) {
//        return new ResponseEntity<Intent>(intentRepository.findById(id), HttpStatus.OK);
//    }
}
