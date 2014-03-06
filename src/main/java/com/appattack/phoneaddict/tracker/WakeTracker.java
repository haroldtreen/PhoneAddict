package com.appattack.phoneaddict.tracker;

import java.util.ArrayList;

/**
 * Created by haroldtreen on 2014-03-03.
 */
public class WakeTracker {

    ArrayList<WakeEvent> wakeEvents = new ArrayList<WakeEvent>();

    public WakeTracker(){

    }

    public void addEvent(WakeEvent event){
        wakeEvents.add(event);
    }

    public WakeEvent[] getEvents(){
        WakeEvent[] eventsArray = new WakeEvent[wakeEvents.size()];
        return wakeEvents.toArray(eventsArray);
    }

    public int getAverageNotificationCount(){
        int totalNotifications = 0;
        for(WakeEvent event : wakeEvents){
            totalNotifications += event.getNotificationCount();
        }

        return Math.round(totalNotifications / wakeEvents.size());
    }

    public long getAverageDuration(){
        long totalDuration = 0;
        for(WakeEvent event : wakeEvents){
            totalDuration += event.getEventDuration();
        }

        return Math.round(totalDuration/wakeEvents.size());
    }
}
