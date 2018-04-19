package com.example.a10580.n4e_systemapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.a10580.n4e_systemapp.utils.DateFormatManager;
import com.example.a10580.n4e_systemapp.utils.SharedPreferenceManager;

import java.util.LinkedList;
import java.util.List;

public class AppInstallationStatusService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private SharedPreferenceManager sharedPreferenceManager;
    private DateFormatManager dateFormatManager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        sharedPreferenceManager = SharedPreferenceManager.getInstance();
        dateFormatManager = DateFormatManager.getInstance();

        repeatInstallationChecks();

        //  doNothing();


        return START_STICKY;
    }


    void doNothing() {
        System.out.println("sdsdsdds");
    }

    // boolean isInstallationstatusChanged = true;

    private boolean newAppInstallationStatus;

    void checkOldStatusNreportToServer() {

        boolean oldAppInstallationStatus = sharedPreferenceManager.getAppInstallationStatus(this);

        newAppInstallationStatus = isN4EInstalledInChildDevice();

        // called only when n4e's installation status is changed
        if (newAppInstallationStatus != oldAppInstallationStatus) {

            /** report n4e's installation status to server**/
            reportAppInstallationStatus();
        }


    }

    Handler handler;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            checkOldStatusNreportToServer();
            repeatInstallationChecks();
        }
    };


    void repeatInstallationChecks() {
        handler = new Handler();
        handler.postDelayed(runnable, 10000);
    }


    void reportAppInstallationStatus() {

        String appStatus;
        String targetAccessToken = sharedPreferenceManager.getAccessToken(this);
        String targetId = sharedPreferenceManager.getTargetId(this);

        //save new App installation status
        sharedPreferenceManager.setAppInstallationStatus(this, newAppInstallationStatus);
        if (isN4EInstalledInChildDevice()) {
            appStatus = "installed";

            //   get N4E's Installation Time
            String n4esInstallationTime = dateFormatManager.getCurrentDate(DateFormatManager.DateFormatSelector.UTC_DATE);


            Log.i("N4Eapp=>", appStatus);
            Log.i("N4EappTime=>", n4esInstallationTime);
            Log.i("targetAccessToken=>", targetAccessToken);
            Log.i("targetId=>", targetId);

        } else {
            appStatus = "unInstalled";
            //  get N4E's unInstallation Time
            String n4esUnInstallationTime = dateFormatManager.getCurrentDate(DateFormatManager.DateFormatSelector.UTC_DATE);
            Log.i("N4Eapp=>", appStatus);
            Log.i("N4EappTime=>", n4esUnInstallationTime);
            Log.i("targetAccessToken=>", targetAccessToken);
            Log.i("targetId=>", targetId);
        }


    }


    public static final String TAG = "appName";

    boolean isN4EInstalledInChildDevice() {
        final PackageManager pm = getPackageManager();
//get a list of installed apps.
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
        if (packageNamesList.contains(n4ePackageName)) {
            // Toast.makeText(this, "N4E is Available", Toast.LENGTH_SHORT).show();
            // Log.i("N4Eapp=>", "N4E is available");
            return true;
        } else {
            // Toast.makeText(this, "N4E is not Available ", Toast.LENGTH_SHORT).show();

            // Log.i("N4Eapp=>", "N4E is NOT available");
            return false;
        }


    }


    // TODO: 19/4/18 alternate method to check n4e's installed app status (but every time need to handle exception(drawback))
    public static boolean isN4Einstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


}
