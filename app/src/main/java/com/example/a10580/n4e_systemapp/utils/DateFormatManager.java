package com.example.a10580.n4e_systemapp.utils;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateFormatManager {
    private static DateFormatManager ourInstance;

    public static DateFormatManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new DateFormatManager();
        }
        return ourInstance;
    }

    private DateFormatManager() {
    }

    public String getCurrentDate(DateFormatSelector dateFormatSelector) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (dateFormatSelector == DateFormatSelector.LOCAL_DATE) {
            return sdf.format(Calendar.getInstance().getTime());
        } else {
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            return sdf.format(Calendar.getInstance().getTime());
        }

    }


    public enum DateFormatSelector {
        UTC_DATE, LOCAL_DATE;
    }


}
