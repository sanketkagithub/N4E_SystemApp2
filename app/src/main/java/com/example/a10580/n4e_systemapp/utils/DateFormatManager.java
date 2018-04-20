package com.example.a10580.n4e_systemapp.utils;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateFormatManager {
    private static DateFormatManager ourInstance;

    //This method gives DateFormatManager class's object(created only once for the first time)
    public static DateFormatManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new DateFormatManager();
        }
        return ourInstance;
    }

    //This constructor prevents further multiple objects creation of DateFormatManager class
    private DateFormatManager() {
    }

    //This method gives current date depending on params (local or utc)
    public String getCurrentDate(DateFormatSelector dateFormatSelector) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (dateFormatSelector == DateFormatSelector.LOCAL_DATE) {
            return sdf.format(Calendar.getInstance().getTime());
        } else {
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            return sdf.format(Calendar.getInstance().getTime());
        }

    }


    //This enum is used to set lo
    public enum DateFormatSelector {
        UTC_DATE, LOCAL_DATE
    }


}
