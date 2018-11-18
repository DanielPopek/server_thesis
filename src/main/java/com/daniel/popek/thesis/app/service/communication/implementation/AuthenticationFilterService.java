package com.daniel.popek.thesis.app.service.communication.implementation;

import com.daniel.popek.thesis.app.model.entities.Application;
import com.daniel.popek.thesis.app.model.entities.Conversation;
import com.daniel.popek.thesis.app.repository.ApplicationConversationRepository;
import com.daniel.popek.thesis.app.repository.ApplicationRepository;
import com.daniel.popek.thesis.app.repository.ConversationRepository;
import com.daniel.popek.thesis.app.repository.DesignerRepository;
import com.daniel.popek.thesis.app.service.communication.IAuthenticationFilterService;
import com.daniel.popek.thesis.app.service.utils.IHeaderTokenService;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationFilterService implements IAuthenticationFilterService{

    @Autowired
    DesignerRepository designerRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    ConversationRepository conversationRepository;

    @Autowired
    ApplicationConversationRepository applicationConversationRepository;

    @Autowired
    IHeaderTokenService headerTokenService;


    @Override
    public boolean apiHeaderIsUnauthorized(String header) {
        if(header==null||!header.contains(":")) return true;

        String applicationToken=headerTokenService.extractAppliactionTokenFromHeader(header);
        String conversationToken=headerTokenService.extractConversationTokenFromHeader(header);

        if(Strings.isNullOrEmpty(applicationToken)||Strings.isNullOrEmpty(conversationToken)) return true;

        Application application=applicationRepository.findApplicationByToken(applicationToken);
        Conversation conversation=conversationRepository.findByHash(conversationToken);

        if(application==null||conversation==null) return true;

        if(applicationConversationRepository.findAllByApplicationIdAndConversationId(application.getId(),conversation.getId()).size()==0) return true;

        return false;
    }

    @Override
    public boolean menagementHeaderIsUnauthorized(String header) {
        return (header == null||header != null && designerRepository.findByApiKey(header)==null)?true:false;
    }
}
