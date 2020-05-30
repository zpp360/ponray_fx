package com.ponray.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMssHHmmssSSS");

    public static String getDate(Date time){
        return dateFormat.format(time);
    }


}
