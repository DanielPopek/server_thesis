package com.daniel.popek.thesis.app.controller;


import com.daniel.popek.thesis.app.model.DTO.comunication.ContextDTO;
import com.daniel.popek.thesis.app.service.communication.ICommunicationService;
import com.daniel.popek.thesis.app.service.data.IConversationService;
import com.daniel.popek.thesis.app.service.ml_classifier.IClassifierService;
import com.daniel.popek.thesis.app.service.ml_classifier.ITokenizerService;
import com.daniel.popek.thesis.app.service.utils.IHeaderTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class CommunicationController {

   @Autowired
   ICommunicationService communicationService;

   @Autowired
    IHeaderTokenService headerTokenService;

    @RequestMapping(method = RequestMethod.POST, value = "/context")
    public ResponseEntity<ContextDTO> classify (@RequestHeader(value = "Authorization") String header,@RequestBody ContextDTO context) {
        return new ResponseEntity<ContextDTO>(communicationService.respond(context,headerTokenService.extractConversationTokenFromHeader(header)), HttpStatus.OK);
    }



}
