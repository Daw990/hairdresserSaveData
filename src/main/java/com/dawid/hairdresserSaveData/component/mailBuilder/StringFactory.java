package com.dawid.hairdresserSaveData.component.mailBuilder;

import java.util.Random;

public class StringFactory {

    private static final String chars = "1234567890qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM";

    public static String getRandomString(int length){
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i <length; i++){
            builder.append(chars.charAt(random.nextInt(chars.length())));
        }
        return builder.toString();
    }
}
