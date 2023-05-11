package com.karanjalawrence.mentormatch.mentormatch.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDateConverter {
    public static Date getDateFromString(String date){
        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date convertedDate = new Date();
        try {
            convertedDate = formater.parse(date);
        } catch (ParseException e) {
            e.getMessage();
        }
        return convertedDate;
    }
}
