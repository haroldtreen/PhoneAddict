package com.appattack.phoneaddict.tracker;

import com.appattack.phoneaddict.RobolectricTestRunnerWithInjection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class WakeTrackerTest {

    WakeTracker tracker;

    @Before
    public void setup(){
        tracker = new WakeTracker();
    }

    /*--------------------------
        TESTS
    --------------------------*/

    @Test
    public void shouldAddWakeEvents(){
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
    public void shouldTrackTheCurrentEvent(){
        WakeEvent eventOne = new WakeEvent(10);
        WakeEvent eventTwo = new WakeEvent(12);

        tracker.addEvent(eventOne);
        tracker.addEvent(eventTwo);

        WakeEvent currentEvent = tracker.getCurrentEvent();

        assertEquals(currentEvent.getNotificationCount(), eventTwo.getNotificationCount());
    }

    @Test
    public void shouldGetTheNumberOfEvents(){
        int numEvents = fillTrackerWithEvents(tracker);

        assertEquals(numEvents, tracker.getNumEvents());
    }

    @Test
    public void emptyTrackerShouldReturnEmptyValues(){
        WakeEvent[] emptyArray = new WakeEvent[0];

        assertArrayEquals(emptyArray, tracker.getEvents());
        assertEquals(0, tracker.getAverageNotificationCount());
        assertNull(tracker.getLastEventCalendar());
        assertEquals(0, tracker.getAverageDurationMs());
        assertEquals(0, tracker.getLastEventDurationMs());
    }

    @Test
    public void shouldGetTheTotalNumberOfNotifications(){
        int totalNotifications = 0;

        fillTrackerWithEvents(tracker);
        WakeEvent[] wakeEvents = tracker.getEvents();

        for(WakeEvent event: wakeEvents){
            totalNotifications += event.getNotificationCount();
        }

        assertEquals(totalNotifications, tracker.getTotalNotificationCount());
    }

    @Test
    public void shouldCalculateAverageNumberOfNotifications(){
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
        fillTrackerWithEvents(tracker);
        Calendar eventCalendar = getTrackerLastEvent(tracker).getEventCalendar();

        assertEquals(eventCalendar, tracker.getLastEventCalendar());
    }

    @Test
    public void shouldGetLastSleepDuration(){
        fillTrackerWithEvents(tracker);
        long lastDuration = getTrackerLastEvent(tracker).getEventDuration();

        assertEquals(lastDuration, tracker.getLastEventDurationMs());
    }

    /*--------------------------
        HELPERS
    --------------------------*/

    private int fillTrackerWithEvents(WakeTracker tracker){
        WakeEvent event;
        int numEvents = 1 + (int)(Math.random() * 50);

        for(int i=0; i < numEvents; i++){
            event = new WakeEvent(i);
            event.eventDuration = i;
            tracker.addEvent(event);
        }

        return numEvents;
    }

    private WakeEvent getTrackerLastEvent(WakeTracker tracker){
        WakeEvent[] wakeEvents = tracker.getEvents();
        return wakeEvents[wakeEvents.length - 1];
    }
}
