package com.daniel.popek.thesis.app.communication.controller;


import com.daniel.popek.thesis.app.domain.DTO.comunication.ContextDTO;
import com.daniel.popek.thesis.app.domain.service.communication.ICommunicationService;
import com.daniel.popek.thesis.app.domain.service.utils.IHeaderTokenService;
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