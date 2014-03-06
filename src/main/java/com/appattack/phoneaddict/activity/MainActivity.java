package com.appattack.phoneaddict.activity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.appattack.phoneaddict.R;
import com.appattack.phoneaddict.service.WakeService;
import com.appattack.phoneaddict.service.WakeService.*;
import com.appattack.phoneaddict.tracker.WakeTracker;

import java.util.Calendar;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.main_activity)
public class MainActivity extends RoboActivity implements View.OnClickListener {

    boolean boundToService = false;

    @InjectView(R.id.start_service_btn)
    ToggleButton serviceToggleBtn;

    @InjectView(R.id.last_sleep_txt)
    TextView lastSleepTxt;

    @InjectView(R.id.avarage_duration_txt)
    TextView averageDurationTxt;

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

    public static void start(Context context){
        Intent startIntent = new Intent(context, MainActivity.class);
        context.startActivity(startIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceToggleBtn.setOnClickListener(this);
    }

    protected void onStart(){
        super.onStart();
        boolean isOn = isServiceRunning(WakeService.class);
        serviceToggleBtn.setChecked(isOn);

        if(isOn && !boundToService){
            Intent serviceIntent = new Intent(this, WakeService.class);
            bindService(serviceIntent, mConnection, Context.BIND_WAIVE_PRIORITY);
        } else if(boundToService) {
            updateWakeStats();
        }
    }

    private boolean isServiceRunning(Class serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void updateWakeStats() {
        WakeTracker tracker = wakeService.getTracker();

        Calendar lastEvent = tracker.getLastSleepCalendar();
        long averageSleepDurationS = tracker.getAverageDurationMs()/1000;

        if(lastEvent != null){
            lastSleepTxt.setText(lastEvent.getTime().toString());
            averageDurationTxt.setText(Long.toString(averageSleepDurationS));
        }
    }

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
