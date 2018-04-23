package com.nischinttechnologies.n4eSystemApp.utils;


import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    //This method gives current date depending on timeZone input
    public String getCurrentDate(TimeZoneSelector timeZoneSelector) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (timeZoneSelector == TimeZoneSelector.LOCAL_DATE) {
            return sdf.format(Calendar.getInstance().getTime());
        } else {
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            return sdf.format(Calendar.getInstance().getTime());
        }

    }


    //This enum is used to set timeZone
    public enum TimeZoneSelector {
        UTC_DATE, LOCAL_DATE
    }


}
