package com.daniel.popek.thesis.app.domain.service.utils;

import org.springframework.stereotype.Service;

@Service
public interface IHeaderTokenService {

    public String extractAppliactionTokenFromHeader(String header);
    public String extractConversationTokenFromHeader(String header);
}
