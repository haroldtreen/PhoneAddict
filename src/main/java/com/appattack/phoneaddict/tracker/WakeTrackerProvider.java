package com.appattack.phoneaddict.tracker;


import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class WakeTrackerProvider implements Provider<WakeTracker> {

    public WakeTracker get(){
        return new WakeTracker();
    }
}
