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
public class EventReceiver extends RoboBroadcastReceiver {

    /*--------------------------
       PARAMETERS
    --------------------------*/

    public static final String actionNotificationPosted = "com.appattack.phoneaddict.actions.NOTIFICATION_POSTED";
    public static final String actionNotificationRemoved = "com.appattack.phoneaddict.actions.NOTIFICATION_POSTED";

    @Inject WakeTracker tracker;

    WakeEvent currentEvent;

    /*--------------------------
        RECEIVER CONSTRUCTOR
    --------------------------*/

    public static IntentFilter getIntentFilter() {
        IntentFilter filter = new IntentFilter();
        String[] actionsArray = {
                Intent.ACTION_SCREEN_ON,
                Intent.ACTION_SCREEN_OFF,
                actionNotificationPosted,
                actionNotificationRemoved
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
            createNewEvent();
        } else if(action.equals(Intent.ACTION_SCREEN_OFF)){
            endCurrentEvent();
        } else if(action.equals(actionNotificationRemoved)){
            incrementNotification();
        } else if(action.equals(actionNotificationPosted)){
            //TODO: Functionality for notification posted?
        }
    }

    /*--------------------------
        EVENT ACTIONS
    --------------------------*/

    public void createNewEvent(){
        currentEvent = new WakeEvent();
        tracker.addEvent(currentEvent);
    }

    public void endCurrentEvent(){
        if(currentEvent != null){
            currentEvent.end();
            currentEvent = null;
        }
    }

    public void incrementNotification(){
        if(currentEvent != null){
            currentEvent.addNotification();
        }
    }
}
