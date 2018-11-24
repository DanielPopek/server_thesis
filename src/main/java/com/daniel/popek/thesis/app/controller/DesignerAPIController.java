package com.daniel.popek.thesis.app.controller;

import com.daniel.popek.thesis.app.model.DTO.comunication.ContextDTO;
import com.daniel.popek.thesis.app.model.DTO.design.*;
import com.daniel.popek.thesis.app.model.entities.Application;
import com.daniel.popek.thesis.app.repository.ApplicationRepository;
import com.daniel.popek.thesis.app.service.communication.ICommunicationService;
import com.daniel.popek.thesis.app.service.data.IApplicationService;
import com.daniel.popek.thesis.app.service.data.IConversationService;
import com.daniel.popek.thesis.app.service.data.IIntentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/management")
public class DesignerAPIController  {

    @Autowired
    IConversationService conversationService;

    @Autowired
    IIntentService intentService;

    @Autowired
    IApplicationService applicationService;

    @Autowired
    ICommunicationService communicationService;

    @RequestMapping(method = RequestMethod.POST, value = "/context")
    public ResponseEntity<ContextDTO> classify (@RequestBody WrappedContextDTO context) {
        return new ResponseEntity<ContextDTO>(communicationService.respond(context.getContext(),context.getConversationHash()), HttpStatus.OK);
    }

    //TODO HANDLE Exceptions

    @RequestMapping(method = RequestMethod.GET, value = "/conversation/{hash}")
    public ResponseEntity<ConversationDTO> getConversationByHash(@PathVariable String hash) {

        return new ResponseEntity<ConversationDTO>(conversationService.readConversationByHash(hash), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/conversation/designer/{id}")
    public ResponseEntity<List<ConversationListDTO>> getListConversationsByDesignerId(@PathVariable Integer id) {

        return new ResponseEntity<List<ConversationListDTO>>(conversationService.readListConversationsByDeveloperId(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/conversation/designer/hash")
    public ResponseEntity<List<ConversationListDTO>> getListConversationsByDesignerHash(@RequestHeader(value = "Authorization") String header) {

        return new ResponseEntity<List<ConversationListDTO>>(conversationService.readListConversationsByDesignerHash(header), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/conversation/names")
    public ResponseEntity<ConversationNamesHashesDTO> getConversationNamesByDesignerHash(@RequestHeader(value = "Authorization") String header) {

        return new ResponseEntity<ConversationNamesHashesDTO>(conversationService.readConversationNamesAndHashesByDesignerHash(header), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/intent/hash/{hash}")
    public ResponseEntity<IntentDTO> getIntentByConversationHash(@PathVariable String hash) {

        return new ResponseEntity<IntentDTO>(intentService.getRootIntentByConversationHash(hash), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/conversation/{hash}")
    public HttpStatus deleteConversationByHash(@PathVariable String hash) {
        conversationService.deleteConversation(hash);

        return HttpStatus.OK;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/conversation")
    public HttpStatus saveConversationTree(@RequestBody ConversationDTO conversationDTO,@RequestHeader(value = "Authorization") String header ) {
        conversationService.saveConversationDTO(conversationDTO,header);

        return HttpStatus.OK;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/conversation/new")
    public HttpStatus saveNewConversation(@RequestBody ConversationListDTO conversationDTO,@RequestHeader(value = "Authorization") String header) {
        conversationService.saveNewConversationDTO(conversationDTO,header);

        return HttpStatus.OK;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/conversation/edit")
    public HttpStatus editConversationData(@RequestBody ConversationListDTO conversationDTO) {
        conversationService.editConversation(conversationDTO);

        return HttpStatus.OK;
    }



    @RequestMapping(method = RequestMethod.GET, value = "/application/{designerId}")
    public ResponseEntity<List<ApplicationDTO>> getAllApplicationsOfDesignerById(@PathVariable Integer designerId) {

        return new ResponseEntity<List<ApplicationDTO>>(applicationService.getAllByDesignerId(designerId), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/application/hash")
    public ResponseEntity<List<ApplicationDTO>> getAllApplicationsOfDesignerByHash(@RequestHeader(value = "Authorization") String header) {

        return new ResponseEntity<List<ApplicationDTO>>(applicationService.getAllByDesignerHash(header), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/application")
    public HttpStatus saveNewAppication(@RequestBody ApplicationDTO applicationDTO,@RequestHeader(value = "Authorization") String header) {

        applicationService.saveApplication(applicationDTO,header);

        return HttpStatus.OK;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/application")
    public HttpStatus editAppication(@RequestBody ApplicationDTO applicationDTO) {
        applicationService.editApplication(applicationDTO);

        return HttpStatus.OK;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/application")
    public HttpStatus deleteConversation(@RequestBody ApplicationDTO applicationDTO) {
       applicationService.deleteApplication(applicationDTO);

       return HttpStatus.OK;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/application/{token}")
    public HttpStatus deleteConversationByToken(@PathVariable String token) {
        applicationService.deleteApplicationByToken(token);

        return HttpStatus.OK;
    }
}
