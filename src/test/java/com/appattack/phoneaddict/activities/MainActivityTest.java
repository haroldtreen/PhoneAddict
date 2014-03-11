package com.appattack.phoneaddict.activities;

import android.content.Intent;

import com.appattack.phoneaddict.RobolectricTestRunnerWithInjection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.util.ActivityController;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunnerWithInjection.class)
public class MainActivityTest {

    private ActivityController<MainActivity> controller;
    private MainActivity activity;

    @Before
    public void setup(){
        controller = Robolectric.buildActivity(MainActivity.class);
        activity = controller.get();
    }

    @Test
    public void shouldStartItself(){
        ShadowActivity shadowActivity = Robolectric.shadowOf(activity);

        MainActivity.start(activity);

        Intent activityIntent = shadowActivity.getNextStartedActivity();

        String expectedName = MainActivity.class.getName();
        String actualName = activityIntent.getComponent().getClassName();

        assertNotNull(activityIntent);
        assertEquals(expectedName, actualName);
    }

    @Test
    public void shouldStartScreenService() {

    }
}
