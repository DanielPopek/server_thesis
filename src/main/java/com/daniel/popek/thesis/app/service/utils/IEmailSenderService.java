package com.daniel.popek.thesis.app.service.utils;

import org.springframework.stereotype.Service;

@Service
public interface IEmailSenderService {
    void sendActivationLinkByEmail(int id, String emailAddress, String activationCode);
}
