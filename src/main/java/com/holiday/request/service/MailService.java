package com.holiday.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender emailSender;
    private final String SUBJECT = "Odmowa urlopu";
    private final String BODY = "Witam, niestety nie możemy zatwierdzić Twojego urlopu w tym terminie.";


    @Value("${spring.mail.username}")
    private String sender;

    public void sendEmail(String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(sender);
        message.setTo(toEmail);
        message.setSubject(SUBJECT);
        message.setText(BODY);
        try {
            emailSender.send(message);

        } catch(Exception e) {
            System.out.println(e);
        }
        System.out.println("dziala");
    }
}
