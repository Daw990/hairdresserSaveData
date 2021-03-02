package com.dawid.hairdresserSaveData.component.mailBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SignUpMail {

    private JavaMailSender emailSender;
    private SignUpMailFactory signUpMailFactory;

    @Autowired
    public SignUpMail(JavaMailSender emailSender, SignUpMailFactory signUpMailFactory){
        this.emailSender = emailSender;
        this.signUpMailFactory = signUpMailFactory;
    }

    public void sendMessage(String to, String subject, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        emailSender.send(message);
    }

    public void sendConfirmationLink(String email, String token){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(signUpMailFactory.getConfirmationMailSubject());
        message.setText(signUpMailFactory.getConfirmationMailText(token));

        emailSender.send(message);
    }
}
