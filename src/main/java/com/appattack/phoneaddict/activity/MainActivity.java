package com.appattack.phoneaddict.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.appattack.phoneaddict.R;
import roboguice.activity.RoboActivity;

public class MainActivity extends RoboActivity {

    public static void start(Context context){
        Intent startIntent = new Intent(context, MainActivity.class);
        context.startActivity(startIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }
}
