package com.appattack.phoneaddict.tracker;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by haroldtreen on 2014-03-03.
 */
public class WakeTracker {

    ArrayList<WakeEvent> wakeEvents = new ArrayList<WakeEvent>();

    public WakeTracker(){

    }

    public void addEvent(WakeEvent event){
        wakeEvents.add(event);
        Log.v("PhoneAddict", "Event Added!");
        Log.v("PhoneAddict", "Num Events: " + wakeEvents.size());
    }

    public WakeEvent[] getEvents(){
        WakeEvent[] eventsArray = new WakeEvent[wakeEvents.size()];
        return wakeEvents.toArray(eventsArray);
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
            long totalDuration = 0;
            for(WakeEvent event : wakeEvents){
                totalDuration += event.getEventDuration();
            }

            return Math.round(totalDuration/wakeEvents.size());
        } else {
            return 0;
        }
    }

    public Calendar getLastSleepCalendar(){
        WakeEvent event = getLastWakeEvent();

        if(event == null){
            return null;
        } else {
            return (Calendar) event.getEventCalendar().clone();
        }
    }

    public long getLastSleepDuration(){
        WakeEvent event = getLastWakeEvent();

        if(event == null){
            return 0;
        } else {
            return event.getEventDuration();
        }
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
