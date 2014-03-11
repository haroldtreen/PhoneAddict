package com.appattack.phoneaddict.receivers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.appattack.phoneaddict.tracker.WakeEvent;
import com.appattack.phoneaddict.tracker.WakeTracker;
import com.google.inject.Inject;

import roboguice.inject.ContextSingleton;
import roboguice.receiver.RoboBroadcastReceiver;

@ContextSingleton
public class ScreenEventReceiver extends RoboBroadcastReceiver {

    /*--------------------------
       PARAMETERS
    --------------------------*/

    @Inject WakeTracker tracker;

    WakeEvent currentEvent;

    /*--------------------------
        RECEIVER CONSTRUCTOR
    --------------------------*/

    public static IntentFilter getIntentFilter() {
        IntentFilter filter = new IntentFilter();
        String[] actionsArray = {
                Intent.ACTION_SCREEN_ON,
                Intent.ACTION_SCREEN_OFF
        };

        for(String action : actionsArray){
            filter.addAction(action);
        }
        return filter;
    }

    /*--------------------------
        LIFECYCLE METHODS
    --------------------------*/

    @Override
    public void handleReceive(Context context, Intent intent) {
        String action = intent.getAction();

        Log.v("PhoneAddict", "Intent Received: " + intent.getAction());

        if(action.equals(Intent.ACTION_SCREEN_ON)){
            currentEvent = new WakeEvent();
        }
        else if(action.equals(Intent.ACTION_SCREEN_OFF)){
            if(currentEvent != null){
                currentEvent.end();
                tracker.addEvent(currentEvent);
                currentEvent = null;
            }
        }
    }
}
