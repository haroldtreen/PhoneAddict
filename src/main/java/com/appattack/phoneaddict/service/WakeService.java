package com.appattack.phoneaddict.service;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.appattack.phoneaddict.receiver.ScreenEventReceiver;
import com.appattack.phoneaddict.tracker.WakeTracker;
import com.google.inject.Inject;

import roboguice.service.RoboService;


public class WakeService extends RoboService {

    @Inject WakeTracker tracker;

    private final IBinder wakeBinder = new WakeServiceBinder();
    ScreenEventReceiver screenEventReceiver;

    @Override
    public void onCreate(){
        super.onCreate();

        screenEventReceiver = new ScreenEventReceiver(tracker);
        IntentFilter filter = ScreenEventReceiver.getIntentFilter();
        registerReceiver(screenEventReceiver, filter);

        Log.v("PhoneAddict", "Service Started!");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(screenEventReceiver);
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
