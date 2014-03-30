package com.appattack.phoneaddict.tracker;

import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Singleton
public class WakeTracker {

    /*--------------------------
        PARAMETERS
    --------------------------*/

    List<WakeEvent> wakeEvents = new ArrayList<WakeEvent>();
    WakeEvent currentEvent;
    private int briefEvents=0;

    /*--------------------------
        TRACKER MODIFIERS
    --------------------------*/

    public void addEvent(WakeEvent event){
        if(event.eventDuration/1000 < 5){
            briefEvents++;
        }

        currentEvent = event;
        wakeEvents.add(event);
    }

    /*--------------------------
        EVENT ACCESSORS
    --------------------------*/

    public WakeEvent getCurrentEvent(){
        return currentEvent;
    }

    public WakeEvent[] getEvents(){
        WakeEvent[] eventsArray = new WakeEvent[wakeEvents.size()];
        return wakeEvents.toArray(eventsArray);
    }

    public int getNumEvents(){
        return wakeEvents.size();
    }

    public int getBriefEventsCount(){
        return briefEvents;
    }

    /*--------------------------
        NOTIFICATION ACCESSORS
    --------------------------*/

    public int getTotalNotificationCount(){
        int totalNotifications = 0;
        for(WakeEvent event : wakeEvents){
            totalNotifications += event.getNotificationCount();
        }
        return totalNotifications;
    }

    public int getAverageNotificationCount(){
        if(wakeEvents.size() > 0){
            int totalNotifications = getTotalNotificationCount();
            return Math.round(totalNotifications / wakeEvents.size());
        } else {
            return 0;
        }
    }

    /*--------------------------
        TIME ACCESSORS
    --------------------------*/

    public long getAverageDurationMs(){
        if(wakeEvents.size() > 0){
            return Math.round(getTotalScreenOnTimeMs()/wakeEvents.size());
        } else {
            return 0;
        }
    }

    public long getTotalScreenOnTimeMs(){
        long totalDuration = 0;

        if(wakeEvents.size() > 0){
            for(WakeEvent event : wakeEvents){
                totalDuration += event.getEventDuration();
            }
        }

        return totalDuration;
    }

    public long getLastEventDurationMs(){
        WakeEvent event = getLastWakeEvent();

        if(event == null){
            return 0;
        } else {
            return event.getEventDuration();
        }
    }

    public Calendar getLastEventCalendar(){
        WakeEvent event = getLastWakeEvent();

        if(event == null){
            return null;
        } else {
            return (Calendar) event.getEventCalendar().clone();
        }
    }

    /*--------------------------
        PRIVATE METHODS
    --------------------------*/

    private WakeEvent getLastWakeEvent(){
        int size = wakeEvents.size();

        if(size > 0){
            return wakeEvents.get(size-1);
        } else {
            return null;
        }
    }
}