package com.example.a10580.n4e_systemapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

//This class is used to save N4E Target's data in session
public class SharedPreferenceManager {
    private static SharedPreferenceManager ourInstance;
    private static final String APP_SETTINGS = "APP_SETTINGS";

    //This method gives SharedPreferenceManager class's object(created only once for the first time)
    public static SharedPreferenceManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new SharedPreferenceManager();
        }
        return ourInstance;
    }

    //This constructor prevents further multiple objects creation of SharedPreferenceManager class
    private SharedPreferenceManager() {
    }

   //This method gives main file of sharedPreference
    private SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE);
    }

    public void setTargetId(Context context, String targetId) {
        getSharedPreferences(context).edit().putString("targetId", targetId).apply();
    }

    public String getTargetId(Context context) {
        return getSharedPreferences(context).getString("targetId", "");
    }

    public void setAccessToken(Context context, String accessToken) {
        getSharedPreferences(context).edit().putString("accessToken", accessToken).apply();
    }

    public String getAccessToken(Context context) {
        return getSharedPreferences(context).getString("accessToken", "");
    }


    public void setAppInstallationStatus(Context context, boolean isAppInstalled) {
        getSharedPreferences(context).edit().putBoolean("isAppInstalled", isAppInstalled).apply();
    }

    public boolean getAppInstallationStatus(Context context) {
        return getSharedPreferences(context).getBoolean("isAppInstalled", false);
    }

}
