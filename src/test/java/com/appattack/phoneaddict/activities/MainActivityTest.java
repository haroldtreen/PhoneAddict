package com.appattack.phoneaddict.activities;

import android.content.Intent;
import android.content.ServiceConnection;
import android.widget.ToggleButton;

import com.appattack.phoneaddict.R;
import com.appattack.phoneaddict.RobolectricTestRunnerWithInjection;
import com.appattack.phoneaddict.services.ServiceUtilities;
import com.appattack.phoneaddict.services.WakeService;
import com.google.inject.Inject;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.util.ActivityController;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunnerWithInjection.class)
public class MainActivityTest {

    ToggleButton serviceButton;
    @Inject ServiceUtilities svcUtilities;

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
    public void shouldStartEventService() {
        ShadowActivity shadowActivity = Robolectric.shadowOf(activity);
        activity.mConnection = Mockito.mock(ServiceConnection.class);

        controller.create().start();

        serviceButton = (ToggleButton) activity.findViewById(R.id.start_service_btn);
        serviceButton.performClick();

        Intent serviceIntent = shadowActivity.getNextStartedService();

        String expectedName = WakeService.class.getName();
        String actualName = serviceIntent.getComponent().getClassName();

        assertNotNull(actualName);
        assertEquals(expectedName, actualName);
    }

    @Test
    public void shouldStopEventService() {
        ShadowActivity shadowActivity = Robolectric.shadowOf(activity);
        activity.mConnection = Mockito.mock(ServiceConnection.class);

        Mockito.when(svcUtilities.isServiceRunning(WakeService.class)).thenReturn(true);
        controller.create().start();

        serviceButton = (ToggleButton) activity.findViewById(R.id.start_service_btn);
        serviceButton.performClick();

        Intent serviceIntent = shadowActivity.getNextStoppedService();

        String expectedName = WakeService.class.getName();
        String actualName = serviceIntent.getComponent().getClassName();

        assertNotNull(actualName);
        assertEquals(expectedName, actualName);
    }

    @Test
    public void buttonShouldReflectServiceState(){
        ShadowActivity shadowActivity = Robolectric.shadowOf(activity);
        activity.mConnection = Mockito.mock(ServiceConnection.class);

        Mockito.when(svcUtilities.isServiceRunning(WakeService.class)).thenReturn(false);
        controller.create().start();

        serviceButton = (ToggleButton) activity.findViewById(R.id.start_service_btn);
        assertFalse(serviceButton.isChecked());

        Mockito.when(svcUtilities.isServiceRunning(WakeService.class)).thenReturn(true);
        controller.create().start();

        serviceButton = (ToggleButton) activity.findViewById(R.id.start_service_btn);
        Assert.assertTrue(serviceButton.isChecked());
    }


}
