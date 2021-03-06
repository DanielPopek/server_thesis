package com.daniel.popek.thesis.app.communication.controller;

import com.daniel.popek.thesis.app.domain.DTO.comunication.ContextDTO;
import com.daniel.popek.thesis.app.domain.DTO.design.*;
import com.daniel.popek.thesis.app.domain.constants.Constants;
import com.daniel.popek.thesis.app.domain.service.communication.ICommunicationService;
import com.daniel.popek.thesis.app.domain.service.data_logic.IApplicationService;
import com.daniel.popek.thesis.app.domain.service.data_logic.IConversationService;
import com.daniel.popek.thesis.app.domain.service.data_logic.IIntentService;
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


    @ExceptionHandler({ Exception.class })
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<String>(Constants.INTERNAL_SERVER_ERROR+":"+ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/context")
    public ResponseEntity<ContextDTO> classify (@RequestBody WrappedContextDTO context) throws Exception{
        return new ResponseEntity<ContextDTO>(communicationService.respond(context.getContext(),context.getConversationHash()), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/conversation/{hash}")
    public ResponseEntity<ConversationDTO> getConversationByHash(@PathVariable String hash) throws Exception{

        return new ResponseEntity<ConversationDTO>(conversationService.readConversationByHash(hash), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/conversation/designer/{id}")
    public ResponseEntity<List<ConversationListDTO>> getListConversationsByDesignerId(@PathVariable Integer id) throws Exception{

        return new ResponseEntity<List<ConversationListDTO>>(conversationService.readListConversationsByDeveloperId(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/conversation/designer/hash")
    public ResponseEntity<List<ConversationListDTO>> getListConversationsByDesignerHash(@RequestHeader(value = "Authorization") String header) throws Exception{

        return new ResponseEntity<List<ConversationListDTO>>(conversationService.readListConversationsByDesignerHash(header), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/conversation/names")
    public ResponseEntity<ConversationNamesHashesDTO> getConversationNamesByDesignerHash(@RequestHeader(value = "Authorization") String header) throws Exception{

        return new ResponseEntity<ConversationNamesHashesDTO>(conversationService.readConversationNamesAndHashesByDesignerHash(header), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/intent/hash/{hash}")
    public ResponseEntity<IntentDTO> getIntentByConversationHash(@PathVariable String hash) throws Exception{

        return new ResponseEntity<IntentDTO>(intentService.getRootIntentByConversationHash(hash), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/conversation/{hash}")
    public HttpStatus deleteConversationByHash(@PathVariable String hash) throws Exception{
        conversationService.deleteConversation(hash);

        return HttpStatus.OK;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/conversation")
    public HttpStatus saveConversationTree(@RequestBody ConversationDTO conversationDTO,@RequestHeader(value = "Authorization") String header ) throws Exception{
        conversationService.saveConversationDTO(conversationDTO,header);

        return HttpStatus.OK;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/conversation/new")
    public HttpStatus saveNewConversation(@RequestBody ConversationListDTO conversationDTO,@RequestHeader(value = "Authorization") String header) throws Exception{
        conversationService.saveNewConversationDTO(conversationDTO,header);

        return HttpStatus.OK;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/conversation/edit")
    public HttpStatus editConversationData(@RequestBody ConversationListDTO conversationDTO) throws Exception{
        conversationService.editConversation(conversationDTO);

        return HttpStatus.OK;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/application/{designerId}")
    public ResponseEntity<List<ApplicationDTO>> getAllApplicationsOfDesignerById(@PathVariable Integer designerId)throws Exception {

        return new ResponseEntity<List<ApplicationDTO>>(applicationService.getAllByDesignerId(designerId), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/application/hash")
    public ResponseEntity<List<ApplicationDTO>> getAllApplicationsOfDesignerByHash(@RequestHeader(value = "Authorization") String header) throws Exception{

        return new ResponseEntity<List<ApplicationDTO>>(applicationService.getAllByDesignerHash(header), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/application")
    public HttpStatus saveNewAppication(@RequestBody ApplicationDTO applicationDTO,@RequestHeader(value = "Authorization") String header) throws Exception{

        applicationService.saveApplication(applicationDTO,header);

        return HttpStatus.OK;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/application")
    public HttpStatus editAppication(@RequestBody ApplicationDTO applicationDTO) throws Exception{
        applicationService.editApplication(applicationDTO);

        return HttpStatus.OK;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/application")
    public HttpStatus deleteConversation(@RequestBody ApplicationDTO applicationDTO) throws Exception{
       applicationService.deleteApplication(applicationDTO);

       return HttpStatus.OK;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/application/{token}")
    public HttpStatus deleteConversationByToken(@PathVariable String token) throws Exception{
        applicationService.deleteApplicationByToken(token);

        return HttpStatus.OK;
    }
}
