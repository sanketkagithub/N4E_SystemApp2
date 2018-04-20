package com.example.a10580.n4e_systemapp.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.a10580.n4e_systemapp.utils.SharedPreferenceManager;
public class N4ETargetDataReceiver extends BroadcastReceiver {
    private SharedPreferenceManager sharedPreferenceManager;

//this method is called when n4e target enters home screen
    @Override
    public void onReceive(Context context, Intent intent)
    {
       Bundle bundle = intent.getExtras();
       sharedPreferenceManager = SharedPreferenceManager.getInstance();
        if (bundle!=null)
       {
                  String targetAccessToken =  bundle.getString("targetAccessToken");
                  String targetId =  bundle.getString("targetId");
    //       Toast.makeText(context, targetId +  "  =System App Called=  " +targetAccessToken, Toast.LENGTH_SHORT).show();
           Log.i("targetAccessToken",targetAccessToken);
           Log.i("targetId",targetId);
           updateN4EsDataInSession(context,targetAccessToken,targetId);
       }
    }


    // TODO: 18/4/18 insertion params may be added in future
    //this method updates ne4 targets data (received from N4E Target App) in session
    void updateN4EsDataInSession(Context context, String...data)
    {
       String targetAccessToken = data[0];
       String targetId = data[1];
        sharedPreferenceManager.setAccessToken(context,targetAccessToken);
        sharedPreferenceManager.setTargetId(context,targetId);
    }



}
