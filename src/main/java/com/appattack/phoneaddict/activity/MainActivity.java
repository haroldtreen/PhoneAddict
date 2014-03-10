package com.appattack.phoneaddict.activity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.appattack.phoneaddict.R;
import com.appattack.phoneaddict.service.ServiceUtilities;
import com.appattack.phoneaddict.service.WakeService;
import com.appattack.phoneaddict.service.WakeService.*;
import com.appattack.phoneaddict.tracker.WakeTracker;
import com.appattack.phoneaddict.view.StatGroupView;
import com.google.inject.Inject;

import java.util.Calendar;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.main_activity)
public class MainActivity extends RoboActivity implements View.OnClickListener {

    /*--------------------------
        PARAMETERS
    --------------------------*/

    @InjectView(R.id.main_wake_stats) StatGroupView wakeStats;
    @InjectView(R.id.start_service_btn) ToggleButton serviceToggleBtn;

    @Inject ServiceUtilities serviceUtilities;

    final String TOTAL_WAKES = "Total Wakes";
    final String TOTAL_BRIEF_EVENTS = "Total Brief Events (< 5s)";
    final String AVERAGE_WAKE_LENGTH = "Average Wake Length";

    boolean boundToService = false;
    WakeService wakeService;

    ServiceConnection mConnection = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            WakeServiceBinder binder = (WakeServiceBinder) iBinder;
            wakeService = binder.getService();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateWakeStats();
                }
            });
            boundToService = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            boundToService = false;
        }
    };

    /*--------------------------
        LIFECYCLE METHODS
    --------------------------*/

    public static void start(Context context){
        Intent startIntent = new Intent(context, MainActivity.class);
        context.startActivity(startIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceToggleBtn.setOnClickListener(this);

        wakeStats.addStat(TOTAL_WAKES, "<No Data>");
        wakeStats.addStat(AVERAGE_WAKE_LENGTH, "<No Data>");
        wakeStats.addStat(TOTAL_BRIEF_EVENTS, "<No Data>");
    }

    protected void onStart(){
        super.onStart();
        boolean isOn = serviceUtilities.isServiceRunning(WakeService.class);
        serviceToggleBtn.setChecked(isOn);

        if(isOn && !boundToService){
            Intent serviceIntent = new Intent(this, WakeService.class);
            bindService(serviceIntent, mConnection, Context.BIND_WAIVE_PRIORITY);
        } else if(boundToService) {
            updateWakeStats();
        }
    }

    /*--------------------------
        VIEW MANIPULATION
    --------------------------*/

    private void updateWakeStats() {
        WakeTracker tracker = wakeService.getTracker();

        wakeStats.updateStat(TOTAL_WAKES, Integer.toString(tracker.getNumEvents()));
        wakeStats.updateStat(AVERAGE_WAKE_LENGTH, Float.toString(tracker.getAverageDurationMs() / 1000) + "s");
        wakeStats.updateStat(TOTAL_BRIEF_EVENTS, Integer.toString(tracker.getBriefEventsCount()));
    }

    /*--------------------------
        CLICK HANDLERS
    --------------------------*/

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start_service_btn:
                toggleService();
                break;
        }
    }

    private void toggleService(){
        Intent serviceIntent = new Intent(this, WakeService.class);
        boolean isOn = serviceToggleBtn.isChecked();

        if(isOn){
            startService(serviceIntent);
            bindService(serviceIntent, mConnection, Context.BIND_WAIVE_PRIORITY);
        }
        else{
            unbindService(mConnection);
            stopService(serviceIntent);
        }
    }
}
