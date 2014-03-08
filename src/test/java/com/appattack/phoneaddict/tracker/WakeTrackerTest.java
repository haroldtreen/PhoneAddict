package com.appattack.phoneaddict.tracker;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class WakeTrackerTest {

    /*--------------------------
        TESTS
    --------------------------*/

    @Test
    public void shouldAddWakeEvents(){
        WakeTracker tracker = new WakeTracker();
        WakeEvent eventOne = new WakeEvent(10);
        WakeEvent eventTwo = new WakeEvent(12);

        tracker.addEvent(eventOne);
        tracker.addEvent(eventTwo);

        WakeEvent[] wakeEvents = { eventOne, eventTwo };
        WakeEvent[] trackerEvents = tracker.getEvents();

        assertEquals(2, trackerEvents.length);
        assertArrayEquals(wakeEvents, trackerEvents);
    }

    @Test
    public void emptyTrackerShouldReturnEmptyValues(){
        WakeTracker tracker = new WakeTracker();
        WakeEvent[] emptyArray = new WakeEvent[0];

        assertArrayEquals(emptyArray, tracker.getEvents());
        assertEquals(0, tracker.getAverageNotificationCount());
        assertNull(tracker.getLastEventCalendar());
        assertEquals(0, tracker.getAverageDurationMs());
        assertEquals(0, tracker.getLastEventDuration());
    }

    @Test
    public void shouldCalculateAverageNumberOfNotifications(){
        WakeTracker tracker = new WakeTracker();
        int totalNotifications = 0;

        fillTrackerWithEvents(tracker);
        WakeEvent[] wakeEvents = tracker.getEvents();

        for(WakeEvent event : wakeEvents){
            totalNotifications += event.getNotificationCount();
        }

        int count = tracker.getAverageNotificationCount();
        assertEquals(count, Math.round(totalNotifications / wakeEvents.length));
    }

    @Test
    public void shouldCalculateAverageDuration(){
        WakeTracker tracker = new WakeTracker();
        long totalDuration = 0;

        fillTrackerWithEvents(tracker);

        WakeEvent[] wakeEvents = tracker.getEvents();
        for(WakeEvent event : wakeEvents){
            totalDuration += event.getEventDuration();
        }

        long duration = tracker.getAverageDurationMs();
        assertEquals(duration, Math.round(totalDuration / wakeEvents.length));
    }

    @Test
    public void shouldGetLastSleepCalendar(){
        WakeTracker tracker = new WakeTracker();

        fillTrackerWithEvents(tracker);
        Calendar eventCalendar = getTrackerLastEvent(tracker).getEventCalendar();

        assertEquals(eventCalendar, tracker.getLastEventCalendar());
    }

    @Test
    public void shouldGetLastSleepDuration(){
        WakeTracker tracker = new WakeTracker();

        fillTrackerWithEvents(tracker);
        long lastDuration = getTrackerLastEvent(tracker).getEventDuration();

        assertEquals(lastDuration, tracker.getLastEventDuration());
    }

    /*--------------------------
        HELPERS
    --------------------------*/

    private void fillTrackerWithEvents(WakeTracker tracker){
        WakeEvent event;
        int numEvents = 1 + (int)(Math.random() * 50);

        for(int i=0; i < numEvents; i++){
            event = new WakeEvent(i);
            event.eventDuration = i;
            tracker.addEvent(event);
        }
    }

    private WakeEvent getTrackerLastEvent(WakeTracker tracker){
        WakeEvent[] wakeEvents = tracker.getEvents();
        return wakeEvents[wakeEvents.length - 1];
    }
}
