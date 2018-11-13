package com.daniel.popek.thesis.app.service.communication;

import com.daniel.popek.thesis.app.model.DTO.comunication.ContextDTO;
import org.springframework.stereotype.Service;

@Service
public interface ICommunicationService {

    ContextDTO respond(ContextDTO context);
}
