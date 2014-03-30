package com.appattack.phoneaddict.receivers;

import android.content.Context;
import android.content.Intent;

import com.appattack.phoneaddict.RobolectricTestRunnerWithInjection;
import com.appattack.phoneaddict.tracker.WakeEvent;
import com.appattack.phoneaddict.tracker.WakeTracker;
import com.google.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.awt.Event;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricTestRunnerWithInjection.class)
public class EventReceiverTest {

    @Inject WakeTracker tracker;
    @Inject Context context;
    @Inject EventReceiver receiver;

    @Test
    public void addsAWakeEventWhenScreenOff(){
        int numEventsStart = tracker.getNumEvents();

        context.registerReceiver(receiver, EventReceiver.getIntentFilter());

        turnScreenOn();
        turnScreenOff();

        int numEventsEnd = tracker.getNumEvents();

        assertEquals(numEventsStart + 1, numEventsEnd);
    }

    @Test
    public void listensForRemovedNotifications(){
        context.registerReceiver(receiver, EventReceiver.getIntentFilter());

        turnScreenOn();
        removeNotification();
        removeNotification();
        turnScreenOff();

        WakeEvent event = tracker.getCurrentEvent();

        assertEquals(2, event.getNotificationCount());
    }

     /*--------------------------
        TEST HELPERS
    --------------------------*/

    private void turnScreenOn(){
        Intent screenOnIntent = new Intent(Intent.ACTION_SCREEN_ON);
        context.sendBroadcast(screenOnIntent);
    }

    private void turnScreenOff(){
        Intent screenOffIntent = new Intent(Intent.ACTION_SCREEN_OFF);
        context.sendBroadcast(screenOffIntent);
    }

    private void removeNotification(){
        Intent notificationRemovedIntent = new Intent(EventReceiver.actionNotificationRemoved);
        context.sendBroadcast(notificationRemovedIntent);
    }

    private void postNotification(){
        Intent postNotificationIntent = new Intent(EventReceiver.actionNotificationPosted);
        context.sendBroadcast(postNotificationIntent);
    }
}
