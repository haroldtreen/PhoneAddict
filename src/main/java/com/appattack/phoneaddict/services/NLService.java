package com.appattack.phoneaddict.services;

import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.appattack.phoneaddict.receivers.EventReceiver;
import com.appattack.phoneaddict.tracker.WakeEvent;
import com.appattack.phoneaddict.tracker.WakeTracker;

public class NLService extends NotificationListenerService{

    //WakeTracker tracker;

    @Override
    public void onNotificationPosted(StatusBarNotification statusBarNotification) {
        Log.v("PhoneAddict", "Notification Posted!");
        //Log.v("PhoneAddict", "Tracker:" + tracker);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification statusBarNotification) {
        Log.v("PhoneAddict", "Notification Removed!");

        Intent removedNotificationIntent = new Intent(EventReceiver.actionNotificationRemoved);
        sendBroadcast(removedNotificationIntent);
    }
}
