package com.appattack.phoneaddict.tracker;

import java.util.Calendar;

public class WakeEvent {

    /*--------------------------
        PARAMETERS
    --------------------------*/

    Calendar eventCalendar;
    long eventDuration;

    int notificationCount;

    /*--------------------------
        CONSTRUCTORS
    --------------------------*/

    public  WakeEvent(){
        eventCalendar = Calendar.getInstance();
        notificationCount = 0;
    }

    public WakeEvent(int notifications){
        this();
        notificationCount = notifications;
    }

    /*--------------------------
        LIFECYCLE METHODS
    --------------------------*/

    public void end(){
        eventDuration = Calendar.getInstance().getTimeInMillis() - eventCalendar.getTimeInMillis();
    }

    /*--------------------------
        ACCESSOR METHODS
    --------------------------*/

    public Calendar getEventCalendar() {
        return (Calendar) eventCalendar.clone();
    }

    public long getEventDuration(){
        return eventDuration;
    }

    public int getNotificationCount(){
        return notificationCount;
    }
}
