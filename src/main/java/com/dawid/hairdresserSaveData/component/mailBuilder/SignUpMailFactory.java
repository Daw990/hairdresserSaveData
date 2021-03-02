package com.dawid.hairdresserSaveData.component.mailBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@PropertySource("classpath:email.properties") //additional file in resources
public class SignUpMailFactory {

    @Value("${email.confirmation.subject}")
    private String subject;

    @Value("${email.confirmation.text}")
    private String text;

    @Value("${email.confirmation.link}")
    private String link;

    public String getConfirmationMailSubject(){
        return subject;
    }

    public String getConfirmationMailText(String token){
        return text + link + token;
    }
}
