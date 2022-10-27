package com.dawid.hairdresserSaveData.component.mailBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SignUpMail {

    private SignUpMailFactory signUpMailFactory;

    @Autowired
    public SignUpMail(SignUpMailFactory signUpMailFactory){
        this.signUpMailFactory = signUpMailFactory;
    }

    public void sendConfirmationLinkByWp(String email, String token) {
        signUpMailFactory.createMailContentAndSend(email, token);
    }
}
