package com.example.a10580.n4e_systemapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.a10580.n4e_systemapp.utils.DateFormatManager;
import com.example.a10580.n4e_systemapp.utils.SharedPreferenceManager;

import java.util.LinkedList;
import java.util.List;

public class ReportAppInstallationStatusIntentService extends IntentService {

    private SharedPreferenceManager mSharedPreferenceManager;
    private DateFormatManager mDateFormatManager;

    public ReportAppInstallationStatusIntentService() {
        super("n4e");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSharedPreferenceManager = SharedPreferenceManager.getInstance();
        mDateFormatManager = DateFormatManager.getInstance();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        checkOldStatusNreportToServer();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    /**
     * reports app installation status to server
     **/
    void reportAppInstallationStatus() {
        String appStatus;
        boolean newAppInstallationStatus = isN4EInstalledInChildDevice();
        //save new App installation status
        mSharedPreferenceManager.setAppInstallationStatus(this, newAppInstallationStatus);
        if (isN4EInstalledInChildDevice()) {
            appStatus = "installed";

            //   get N4E's Installation Time
            String n4esInstallationTime = mDateFormatManager.getCurrentDate(DateFormatManager.TimeZoneSelector.UTC_DATE);

            Log.i("N4Eapp=>", appStatus);
            Log.i("N4EappTime=>", n4esInstallationTime);

        } else {
            appStatus = "unInstalled";

            //  get N4E's unInstallation Time
            String n4esUnInstallationTime = mDateFormatManager.getCurrentDate(DateFormatManager.TimeZoneSelector.UTC_DATE);
            Log.i("N4Eapp=>", appStatus);
            Log.i("N4EappTime=>", n4esUnInstallationTime);
        }

        String accessToken = mSharedPreferenceManager.getAccessToken(this);
        String targetId = mSharedPreferenceManager.getTargetId(this);

        Log.i("accessToken", accessToken);
        Log.i("targetId", targetId);
    }


    public static final String TAG = "systemAppsDetails";

    //This method checks whether N4E Target is installed
    boolean isN4EInstalledInChildDevice() {
        final PackageManager pm = getPackageManager();

        //get a list of all installed apps.
        List<ApplicationInfo> applicationInfoList = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo packageInfo : applicationInfoList) {
            Log.d(TAG, "Installed package :" + packageInfo.packageName);
            Log.d(TAG, "Source dir : " + packageInfo.sourceDir);
            Log.d(TAG, "Launch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName));
        }

        // add only installed app's package names in list
        List<String> packageNamesList = new LinkedList<>();
        for (ApplicationInfo applicationInfo :
                applicationInfoList) {
            packageNamesList.add(applicationInfo.packageName);
        }


        String n4ePackageName = "com.nischinttechnologies.n4e";
        Log.i("MyPackageNames ", String.valueOf(packageNamesList));

        //check n4e's package name available or not
        // Toast.makeText(this, "N4E is Available", Toast.LENGTH_SHORT).show();
// Log.i("N4Eapp=>", "N4E is available");
// Toast.makeText(this, "N4E is not Available ", Toast.LENGTH_SHORT).show();
// Log.i("N4Eapp=>", "N4E is NOT available");
        return packageNamesList.contains(n4ePackageName);


    }


    public static final String TAG2 = "reporting";

    void checkOldStatusNreportToServer() {

        boolean oldAppInstallationStatus = mSharedPreferenceManager.getAppInstallationStatus(this);

        boolean newAppInstallationStatus = isN4EInstalledInChildDevice();

        // called only when n4e's installation status is changed
        if (newAppInstallationStatus != oldAppInstallationStatus) {

            /** report n4e's installation status to server**/
            reportAppInstallationStatus();

            Log.i(TAG2, "hitServer");

        } else {
            Log.i(TAG2, "noServerCall");
        }


    }


}

