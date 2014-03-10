package com.appattack.phoneaddict.tracker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WakeTracker {

    List<WakeEvent> wakeEvents = new ArrayList<WakeEvent>();
    private int briefEvents=0;

    public WakeTracker(){

    }

    public void addEvent(WakeEvent event){
        if(event.eventDuration/1000 < 5){
            briefEvents++;
        }
        wakeEvents.add(event);
    }

    public WakeEvent[] getEvents(){
        WakeEvent[] eventsArray = new WakeEvent[wakeEvents.size()];
        return wakeEvents.toArray(eventsArray);
    }

    public int getNumEvents(){
        return wakeEvents.size();
    }

    public int getAverageNotificationCount(){
        if(wakeEvents.size() > 0){
            int totalNotifications = 0;
            for(WakeEvent event : wakeEvents){
                totalNotifications += event.getNotificationCount();
            }

            return Math.round(totalNotifications / wakeEvents.size());
        } else {
            return 0;
        }
    }

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

    public Calendar getLastEventCalendar(){
        WakeEvent event = getLastWakeEvent();

        if(event == null){
            return null;
        } else {
            return (Calendar) event.getEventCalendar().clone();
        }
    }

    public long getLastEventDuration(){
        WakeEvent event = getLastWakeEvent();

        if(event == null){
            return 0;
        } else {
            return event.getEventDuration();
        }
    }

    public int getBriefEventsCount(){
        return briefEvents;
    }

    private WakeEvent getLastWakeEvent(){
        int size = wakeEvents.size();

        if(size > 0){
            return wakeEvents.get(size-1);
        } else {
            return null;
        }
    }
}
