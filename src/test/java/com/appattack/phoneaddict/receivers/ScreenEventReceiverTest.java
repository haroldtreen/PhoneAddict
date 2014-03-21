package com.appattack.phoneaddict.receivers;

import android.content.Context;
import android.content.Intent;

import com.appattack.phoneaddict.RobolectricTestRunnerWithInjection;
import com.appattack.phoneaddict.tracker.WakeTracker;
import com.google.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricTestRunnerWithInjection.class)
public class ScreenEventReceiverTest {

    @Inject WakeTracker tracker;
    @Inject Context context;
    @Inject ScreenEventReceiver receiver;

    @Test
    public void addsAWakeEventWhenScreenOff(){
        Intent screenOnIntent = new Intent(Intent.ACTION_SCREEN_ON);
        Intent screenOffIntent = new Intent(Intent.ACTION_SCREEN_OFF);

        int numEventsStart = tracker.getNumEvents();

        context.registerReceiver(receiver, ScreenEventReceiver.getIntentFilter());

        context.sendBroadcast(screenOnIntent);
        context.sendBroadcast(screenOffIntent);

        int numEventsEnd = tracker.getNumEvents();

        assertEquals(numEventsStart + 1, numEventsEnd);
    }
}
