package com.karanjalawrence.mentormatch.mentormatch.Util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDateConverter {
    public static Date getDateFromString(Date date) throws ParseException{
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        String inter= formater.format(date);
        Date day = DateFormat.getInstance().parse(inter);
        return day;
    }
}
