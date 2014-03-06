package com.appattack.phoneaddict.service;

import com.appattack.phoneaddict.RobolectricTestRunnerWithInjection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.shadows.ShadowService;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunnerWithInjection.class)
public class WakeServiceTest {

    @Test
    public void shouldBeInjectedWithAWakeTracker(){
        WakeService service = new WakeService();
        service.onCreate();

        assertNotNull(service.tracker);
    }
}
