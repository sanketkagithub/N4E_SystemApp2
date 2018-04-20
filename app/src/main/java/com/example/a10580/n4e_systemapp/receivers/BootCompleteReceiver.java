package com.example.a10580.n4e_systemapp.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.a10580.n4e_systemapp.services.ReportAppInstallationStatusIntentService;

public class BootCompleteReceiver extends BroadcastReceiver {
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        // after BOOT_COMPLETED, start Service
        if (intent.getAction().equals(ACTION)) {
            restartReportingInstallationStatus(context);
            Log.i("bootCompleted", "SystemAppBooted");

        }
    }






    void restartReportingInstallationStatus(Context context) {
        Intent ishintent = new Intent(context, ReportAppInstallationStatusIntentService.class);
        PendingIntent pintent = PendingIntent.getService(context, 0, ishintent, 0);
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pintent);
        // alarm.setRepeating(AlarmManager.RTC_WAKEUP, 2, 2, pintent);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, 2, 2, pintent);
    }

}