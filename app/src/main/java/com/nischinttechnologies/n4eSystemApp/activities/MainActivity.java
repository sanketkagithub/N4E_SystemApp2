package com.nischinttechnologies.n4eSystemApp.activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.a10580.n4e_systemapp.R;
import com.nischinttechnologies.n4eSystemApp.services.ReportAppInstallationStatusIntentService;

///This Activity will be hidden once app gets launched for the first time
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        confirmSystemAppInstallationInTarget();
        keepReportingInstallationStatus();

        //This Method destroys MainActivity, required to hide app
        finish();
    }

    //This method checks old and new N4E's installation status, if changes found,
    // reports to server using intent service.
    void keepReportingInstallationStatus() {
        long triggerAtMillis = 2;
        long intervalMillis = 2;

        Intent reportAppInstallationIntentServiceIntent = new Intent(this, ReportAppInstallationStatusIntentService.class);
        PendingIntent reportingStatusPendingIntent = PendingIntent.getService(this, 0, reportAppInstallationIntentServiceIntent, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        assert alarm != null;
        alarm.cancel(reportingStatusPendingIntent);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis, intervalMillis, reportingStatusPendingIntent);
        //alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, 2, 2, pintent);
    }


    /**
     * This method reports this (SystemApp) app's installation to server,
     * after getting installed successfully
     **/
    void confirmSystemAppInstallationInTarget() {
        // TODO: 23/4/18 need to integrate appInstallationConfirmation api
        Log.i("appInstalled", "done");
    }


}
