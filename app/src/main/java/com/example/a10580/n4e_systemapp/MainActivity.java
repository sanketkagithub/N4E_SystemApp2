package com.example.a10580.n4e_systemapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        triggerServiceWithFixedInterval();
        finish();
    }



    void triggerServiceWithFixedInterval() {
        Intent ishintent = new Intent(this, AppInstallationStatusService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, ishintent, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pintent);
         alarm.setRepeating(AlarmManager.RTC_WAKEUP, 2, 2, pintent);
        //alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, 2, 2, pintent);
    }



}
