package com.appattack.phoneaddict.tracker;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class WakeTrackerTest {

    @Test
    public void canAddWakeEvents(){
        WakeTracker tracker = new WakeTracker();
        WakeEvent event = new WakeEvent(10);

        tracker.addEvent(event);
        WakeEvent[] trackerEvents = tracker.getEvents();

        assertTrue(trackerEvents.length == 1);
        assertTrue(trackerEvents[0].getNotificationCount() == event.getNotificationCount());
    }

    @Test
    public void canCalculateAverageNumberOfNotifications(){
        WakeTracker tracker = new WakeTracker();

        int totalNotifications = 0;
        int numEvents = 1 + (int)(Math.random() * 50);

        for(int i=0; i < numEvents; i++){
            WakeEvent event = new WakeEvent(i);
            tracker.addEvent(event);
            totalNotifications += i;
        }

        int count = tracker.getAverageNotificationCount();
        assertTrue(count == Math.round(totalNotifications/numEvents));
    }

    @Test
    public void canCalculateAverageDuration(){
        WakeTracker tracker = new WakeTracker();

        long totalDuration = 0;
        int numEvents = 1 + (int)(Math.random() * 50);

        for(int i=0; i < numEvents; i++){
            WakeEvent event = new WakeEvent();
            tracker.addEvent(event);
            event.end();

            totalDuration += event.getEventDuration();
        }

        long duration = tracker.getAverageDurationMs();
        assertTrue(duration == Math.round(totalDuration/numEvents));
    }
}
