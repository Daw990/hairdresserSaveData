package com.dawid.hairdresserSaveData.custom;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateAndTime {

    public static LocalDateTime getDateAndTime(){
        return LocalDateTime.now();
    }

    public static String getDate(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
