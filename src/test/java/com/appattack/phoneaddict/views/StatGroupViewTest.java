package com.appattack.phoneaddict.views;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appattack.phoneaddict.RobolectricTestRunnerWithInjection;
import com.google.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunnerWithInjection.class)
public class StatGroupViewTest {

    @Inject Context context;
    StatGroupView statView;

    @Before
    public void setup(){
        statView = new StatGroupView(context);
    }

    @Test
    public void shouldBeAbleToSetTheTitle(){
        statView.addTitle("TestTitle", 10);

        assertEquals(1, statView.getChildCount());
    }

    @Test
    public void shouldBeAbleToAddMetrics(){
        String title = "Stat";
        String value = "1 unit";

        assertFalse(statView.metricsMap.containsKey(title));

        statView.addStat(title, value);

        LinearLayout stat = (LinearLayout) statView.getChildAt(0);
        TextView statTitle = (TextView) stat.getChildAt(0);
        TextView statValue = (TextView) stat.getChildAt(1);

        assertEquals(2, stat.getChildCount());
        assertEquals(1, statView.getChildCount());

        assertEquals(title, statTitle.getText());
        assertEquals(value, statValue.getText());

        assertTrue(statView.metricsMap.containsKey(title));
    }

    @Test
    public void shouldBeAbleToUpdateMetrics(){
        String title = "Stat";
        String value = "1 unit";
        String updatedValue = "2 unit";

        assertFalse(statView.updateStat(title, updatedValue));
        assertFalse(statView.metricsMap.containsKey(title));

        statView.addStat(title, value);
        assertTrue(statView.updateStat(title, updatedValue));

        LinearLayout stat = (LinearLayout) statView.getChildAt(0);
        TextView statTitle = (TextView) stat.getChildAt(0);
        TextView statValue = (TextView) stat.getChildAt(1);

        assertEquals(title, statTitle.getText());
        assertEquals(updatedValue, statValue.getText());
    }
}
