package com.appattack.phoneaddict.service;

import android.app.ActivityManager;
import android.content.Context;

import com.google.inject.Inject;

import roboguice.inject.ContextSingleton;

@ContextSingleton
public class ServiceUtilities {

    @Inject Context context;

    public boolean isServiceRunning(Class serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo runningService : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(runningService.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
