package com.appattack.phoneaddict.activity;

import android.app.Activity;
import android.app.Application;
import android.app.Service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowService;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    @Test
    public void shouldStartItself(){
        ShadowActivity shadowActivity = Robolectric.shadowOf()
    }

    @Test
    public void shouldStartScreenService() {
    }
}
