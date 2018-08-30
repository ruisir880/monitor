package com.ray.monitor.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ray Rui on 8/30/2018.
 */
public class DateUtil {

    public static final String FORMAT = "yyyy-MM-dd hh:mm:ss";

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(FORMAT);


    public static String formatDate(Date date){
        if(date == null){
            return "";
        }
        return DATE_FORMAT.format(date);
    }
}
