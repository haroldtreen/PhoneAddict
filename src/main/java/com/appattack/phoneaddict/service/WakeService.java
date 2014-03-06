package com.appattack.phoneaddict.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.appattack.phoneaddict.EventReceiver;
import com.appattack.phoneaddict.tracker.WakeTracker;
import com.google.inject.Inject;

import roboguice.service.RoboService;


public class WakeService extends RoboService {

    @Inject WakeTracker tracker;

    private final IBinder wakeBinder = new WakeServiceBinder();
    EventReceiver eventReceiver;

    @Override
    public void onCreate(){
        super.onCreate();

        eventReceiver = new EventReceiver(tracker);
        IntentFilter filter = EventReceiver.getIntentFilter();
        registerReceiver(eventReceiver, filter);

        Log.v("PhoneAddict", "Service Started!");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(eventReceiver);
        Log.v("PhoneAddict", "Service Destroyed!");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return wakeBinder;
    }

    public class WakeServiceBinder extends Binder {
        public WakeService getService(){
            return WakeService.this;
        }
    }

    public WakeTracker getTracker(){
        return tracker;
    }
}
