package com.daniel.popek.thesis.app.domain.service.communication;

import com.daniel.popek.thesis.app.domain.DTO.comunication.ContextDTO;
import org.springframework.stereotype.Service;

@Service
public interface ICommunicationService {

    ContextDTO respond(ContextDTO context, String conversationHash);
}
