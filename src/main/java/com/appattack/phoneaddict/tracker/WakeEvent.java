package com.appattack.phoneaddict.tracker;

import java.util.Calendar;

/**
 * Created by haroldtreen on 2014-03-03.
 */
public class WakeEvent {

    Calendar eventCalendar;
    long eventDuration;

    int notificationCount;

    public  WakeEvent(){
        eventCalendar = Calendar.getInstance();
        notificationCount = 0;
    }

    public WakeEvent(int notifications){
        this();
        notificationCount = notifications;
    }

    public Calendar getEventCalendar() {
        return (Calendar) eventCalendar.clone();
    }

    public int getNotificationCount(){
        return notificationCount;
    }

    public void end(){
        eventDuration = Calendar.getInstance().getTimeInMillis() - eventCalendar.getTimeInMillis();
    }

    public long getEventDuration(){
        return eventDuration;
    }
}
