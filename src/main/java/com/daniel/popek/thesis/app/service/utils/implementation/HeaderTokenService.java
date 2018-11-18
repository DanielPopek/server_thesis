package com.daniel.popek.thesis.app.service.utils.implementation;

import com.daniel.popek.thesis.app.service.utils.IHeaderTokenService;
import org.springframework.stereotype.Service;

@Service
public class HeaderTokenService implements IHeaderTokenService {
    @Override
    public String extractAppliactionTokenFromHeader(String header) {
        String[] tokens=header.split(":");
        return tokens.length>0?tokens[0]:null;
    }

    @Override
    public String extractConversationTokenFromHeader(String header) {
        String[] tokens=header.split(":");
        return tokens.length>1?tokens[1]:null;
    }
}
