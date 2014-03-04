package com.appattack.phoneaddict.activity;

import android.app.Activity;

import com.appattack.phoneaddict.activity.MainActivity;

import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class PhoneAddictApplicationTest {

  @org.junit.Test
  public void testSomething() throws Exception {
    Activity activity = Robolectric.buildActivity(MainActivity.class).create().get();
    assertTrue(activity != null);
  }
}
