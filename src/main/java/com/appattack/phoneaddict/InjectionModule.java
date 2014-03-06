package com.appattack.phoneaddict;


import com.appattack.phoneaddict.service.WakeService;
import com.appattack.phoneaddict.service.WakeServiceProvider;
import com.appattack.phoneaddict.tracker.WakeTracker;
import com.appattack.phoneaddict.tracker.WakeTrackerProvider;
import com.google.inject.AbstractModule;

public class InjectionModule extends AbstractModule {

    protected void configure(){
        bind(WakeTracker.class).toProvider(WakeTrackerProvider.class);
        bind(WakeService.class).toProvider(WakeServiceProvider.class);
    }
}
