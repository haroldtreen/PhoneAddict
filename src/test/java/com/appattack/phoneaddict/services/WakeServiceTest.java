package com.appattack.phoneaddict.services;

import android.content.Intent;

import com.appattack.phoneaddict.RobolectricTestRunnerWithInjection;
import com.appattack.phoneaddict.receivers.EventReceiver;
import com.google.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.shadows.ShadowApplication;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunnerWithInjection.class)
public class WakeServiceTest {

    @Inject WakeService service;

    ShadowApplication application;

    @Before
    public void setup(){
        application = Robolectric.getShadowApplication();
        service.onCreate();
    }

    @Test
    public void shouldBeInjectedWithAWakeTracker(){
        assertNotNull(service.tracker);
    }

    @Test
    public void shouldRegisterAnEventReceiver(){
        EventReceiver eventReceiver = service.eventReceiver;
        Intent screenOff = new Intent(Intent.ACTION_SCREEN_OFF);
        Intent screenOn = new Intent(Intent.ACTION_SCREEN_ON);

        List<?> offReceivers = application.getReceiversForIntent(screenOff);
        List<?> onReceivers = application.getReceiversForIntent(screenOn);

        assertNotNull(eventReceiver);
        assertEquals(EventReceiver.class, eventReceiver.getClass());
        assertEquals(1, offReceivers.size());
        assertEquals(1, onReceivers.size());
    }


}
