package com.daniel.popek.thesis.app.service.communication;

import org.springframework.stereotype.Service;

@Service
public interface IAuthenticationFilterService {
    //checks if header is unauthorized
    boolean apiHeaderIsUnauthorized(String header);
    //checks if header is unauthorized
    boolean menagementHeaderIsUnauthorized(String header);
}
