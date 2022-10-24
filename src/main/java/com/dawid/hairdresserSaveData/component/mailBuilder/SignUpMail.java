package com.dawid.hairdresserSaveData.component.mailBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class SignUpMail {

    private final String EMAIL_ADRES_FROM = "information.no.reply.email@gmail.com";

    private SignUpMailFactory signUpMailFactory;

    @Autowired
    public SignUpMail(SignUpMailFactory signUpMailFactory){
        this.signUpMailFactory = signUpMailFactory;
    }

    public void sendConfirmationLink(String email, String token){
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(email);
//        message.setSubject(signUpMailFactory.getConfirmationMailSubject());
//        message.setText(signUpMailFactory.getConfirmationMailText(token));
//
//        emailSender.send(message);


    }

    public void sendConfirmationLinkBySes(String email, String token) throws MessagingException {
        Session session = getSession();

        MimeMessage mimeMessage = new MimeMessage(session);
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        messageHelper.setTo(email);
        messageHelper.setFrom(EMAIL_ADRES_FROM);
        messageHelper.setSubject(signUpMailFactory.getConfirmationMailSubject());

        String mailContent = "<p><b>Dzień Dobry </b>";
        mailContent += "<p>poniżej znajduje się link do aktywacji konta w serwisie - Nożyce w dłoni</p><br>";
        mailContent += signUpMailFactory.getConfirmationLink(token);

        messageHelper.setText(mailContent, true);


        Transport transport = session.getTransport();
        transport.connect("email-smtp.eu-central-1.amazonaws.com",
                "AKIARCGBBOM6UB7M4J4O",
                "BLhKOdM1VtZu+inosugUdtVOFA9YYihJWEBEOTsSIrOv");

        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
    }



    private Session getSession() {
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        return Session.getDefaultInstance(props);
    }
}
