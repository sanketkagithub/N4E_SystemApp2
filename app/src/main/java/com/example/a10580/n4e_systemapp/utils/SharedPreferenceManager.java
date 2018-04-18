package com.example.a10580.n4e_systemapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {
    private static SharedPreferenceManager ourInstance = null;
    private static final String APP_SETTINGS = "APP_SETTINGS";
    private SharedPreferences.Editor editor;

  public   static SharedPreferenceManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new SharedPreferenceManager();
        }
        return ourInstance;
    }

    private SharedPreferenceManager() {
    }


    private SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE);
    }

   public void setTargetId(Context context, String targetId) {
        editor = getSharedPreferences(context).edit();
        editor.putString("targetId", targetId);
        editor.apply();
    }

   public String getTargetId(Context context) {
        return getSharedPreferences(context).getString("targetId","");
    }

  public   void setAccessToken(Context context, String accessToken) {
        editor = getSharedPreferences(context).edit();
        editor.putString("accessToken", accessToken);
        editor.apply();
    }

  public   String getAccessToken(Context context) {
        return getSharedPreferences(context).getString("accessToken","");
    }


    public   void setAppInstallationStatus(Context context, boolean isAppInstalled) {
        editor = getSharedPreferences(context).edit();
        editor.putBoolean("isAppInstalled", isAppInstalled);
        editor.apply();
    }

  public boolean getAppInstallationStatus(Context context) {
        return getSharedPreferences(context).getBoolean("isAppInstalled",false);
    }

}
