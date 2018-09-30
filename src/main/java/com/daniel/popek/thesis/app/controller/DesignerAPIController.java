package com.daniel.popek.thesis.app.controller;

import com.daniel.popek.thesis.app.model.DTO.design.ConversationDTO;
import com.daniel.popek.thesis.app.service.data.IConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DesignerAPIController  {

    @Autowired
    IConversationService conversationService;

    //TODO HANDLE Exceptions

    @RequestMapping(method = RequestMethod.GET, value = "/conversation/{hash}")
    public ResponseEntity<ConversationDTO> getConversationByHash(@PathVariable String hash) {

        return new ResponseEntity<ConversationDTO>(conversationService.readConversationByHash(hash), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/conversation/{hash}")
    public HttpStatus deleteConversationByHash(@PathVariable String hash) {
        conversationService.deleteConversation(hash);

        return HttpStatus.OK;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/conversation")
    public HttpStatus getSampleDTO(@RequestBody ConversationDTO conversationDTO) {
        conversationService.saveConversationDTO(conversationDTO);
        return HttpStatus.OK;
    }
}
