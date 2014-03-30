package com.appattack.phoneaddict.tracker;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Calendar;

public class WakeEventTest {

    WakeEvent event;

    @Before
    public void setup(){
        event = new WakeEvent();
    }

    @Test
    public void hasAnEventTime(){
        Calendar eventCalendar = event.getEventCalendar();
        Calendar currentCalendar = Calendar.getInstance();

        long timeDiff = eventCalendar.getTimeInMillis() - currentCalendar.getTimeInMillis();

        assertTrue(timeDiff < 1000);
    }

    @Test
    public void hasANotificationCount(){
        WakeEvent noNotifcationEvent = new WakeEvent();
        WakeEvent notificationEvent = new WakeEvent(10);

        assertTrue(noNotifcationEvent.getNotificationCount() == 0);
        assertTrue(notificationEvent.getNotificationCount() == 10);
    }

    @Test
    public void hasInteractionDuration() throws InterruptedException {
        Thread.sleep(1);
        event.end();

        long eventDuration = event.getEventDuration();
        assertTrue(eventDuration > 0 && eventDuration < 3);
    }

    @Test
    public void shouldIncrementNotificationCount(){
        event.addNotification();
        event.addNotification();

        assertEquals(2, event.getNotificationCount());
    }
}
