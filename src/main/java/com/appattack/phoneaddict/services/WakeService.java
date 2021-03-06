package com.appattack.phoneaddict.services;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.appattack.phoneaddict.receivers.EventReceiver;
import com.appattack.phoneaddict.tracker.WakeTracker;
import com.google.inject.Inject;

import roboguice.inject.ContextSingleton;
import roboguice.service.RoboService;

@ContextSingleton
public class WakeService extends RoboService {

    /*--------------------------
        PARAMETERS
    --------------------------*/

    @Inject WakeTracker tracker;
    @Inject
    EventReceiver eventReceiver;

    private final IBinder wakeBinder = new WakeServiceBinder();

    /*--------------------------
        LIFECYCLE METHODS
    --------------------------*/

    @Override
    public void onCreate(){
        super.onCreate();

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

    /*--------------------------
        BINDER
    --------------------------*/

    public class WakeServiceBinder extends Binder {
        public WakeService getService(){
            return WakeService.this;
        }
    }

    /*--------------------------
        LIFECYCLE METHODS
    --------------------------*/

    public WakeTracker getTracker(){
        return tracker;
    }
}
