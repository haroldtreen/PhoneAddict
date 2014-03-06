package com.appattack.phoneaddict;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.appattack.phoneaddict.tracker.WakeEvent;
import com.appattack.phoneaddict.tracker.WakeTracker;


public class EventReceiver extends BroadcastReceiver {

    WakeTracker tracker;
    WakeEvent currentEvent;

    public EventReceiver(WakeTracker tracker){
        super();
        this.tracker = tracker;
    }

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

    public void onReceive(Context context, Intent intent) {
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
