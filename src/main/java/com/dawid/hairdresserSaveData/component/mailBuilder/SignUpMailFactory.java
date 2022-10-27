package com.dawid.hairdresserSaveData.component.mailBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
@PropertySource(value = "file:custom-mail.properties", encoding = "UTF-8") //file on server
public class SignUpMailFactory {

    @Value("${email.confirmation.mail.smtp.auth}")
    private String authBoolean;

    @Value("${email.confirmation.mail.smtp.starttls.enable}")
    private String starttls;

    @Value("${email.confirmation.mail.smtp.host}")
    private String host;

    @Value("${email.confirmation.mail.smtp.port}")
    private String port;

    @Value("${email.confirmation.login}")
    private String loginEmail;

    @Value("${email.confirmation.password}")
    private String password;

    @Value("${email.confirmation.subject}")
    private String subject;

    @Value("${email.confirmation.text}")
    private String text;

    @Value("${email.confirmation.link}")
    private String link;

    @Value("${email.confirmation.content}")
    private String content;

    public String getConfirmationMailText(String token) {
        return text + link + token;
    }

    public void createMailContentAndSend(String email, String token) {

        Session session = getSession();
        MimeMessage message = new MimeMessage(session);

        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

            messageHelper.setTo(email);
            messageHelper.setFrom(loginEmail);
            messageHelper.setSubject(subject);

            content += getConfirmationMailText(token);

            messageHelper.setText(content, true);

            Transport.send(message);
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }

    private Session getSession() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", authBoolean);
        prop.put("mail.smtp.starttls.enable", starttls);
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", port);

        return Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(loginEmail, password);
            }
        });
    }
}
