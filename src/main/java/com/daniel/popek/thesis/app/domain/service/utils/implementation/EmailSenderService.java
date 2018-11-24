package com.daniel.popek.thesis.app.domain.service.utils.implementation;

import com.daniel.popek.thesis.app.domain.constants.Constants;
import com.daniel.popek.thesis.app.domain.service.utils.IEmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderService implements IEmailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;


    @Override
    public void sendActivationLinkByEmail(int id, String emailAddress, String activationCode) {
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(emailAddress);
            helper.setReplyTo("appinz.thesis@gmail.com");
            helper.setFrom("appinz.thesis@gmail.com");
            helper.setSubject("Chatrooster potwierdzenie rejestracji");
            Context context = new Context();
            context.setVariable("activationLink", Constants.URL + "/authentication/activate/" + id + "?code=" + activationCode);
            String body = templateEngine.process("EmailTemplate", context);
            helper.setText(body, true);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(mail);
    }
}
