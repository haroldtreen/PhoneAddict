package com.appattack.phoneaddict.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appattack.phoneaddict.R;

import java.util.HashMap;

public class StatGroupView extends LinearLayout {

    /*--------------------------
        PARAMETERS
    --------------------------*/

    Context context;

    HashMap metricsMap;

    final String TITLE_TAG = "metric_title";
    final String VALUE_TAG = "metric_value";

    int metricTitleSize;
    int metricValueSize;

    /*--------------------------
        VIEW SETUP
    --------------------------*/

    public StatGroupView(Context context, AttributeSet attrs){
        super(context, attrs);
        this.context = context;

        metricsMap = new HashMap();

        setOrientation(LinearLayout.VERTICAL);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StatGroupView);
        String groupTitleText = a.getString(R.styleable.StatGroupView_title);
        int defaultTitleSize = context.getResources().getDimensionPixelSize(R.dimen.group_title_default);
        int groupTitleSize = a.getDimensionPixelSize(R.styleable.StatGroupView_titleSize, defaultTitleSize);

        setMetricTextSizes(a);
        addTitle(groupTitleText, groupTitleSize);

        a.recycle();
    }

    public void setMetricTextSizes(TypedArray a){
        Resources res = context.getResources();

        int titleSize = res.getDimensionPixelSize(R.dimen.metric_title_default);
        int valueSize = res.getDimensionPixelSize(R.dimen.metric_value_default);

        this.metricTitleSize = a.getDimensionPixelSize(R.styleable.StatGroupView_metricTitleSize, titleSize);
        this.metricValueSize = a.getDimensionPixelSize(R.styleable.StatGroupView_metricValueSize, valueSize);
    }

    /*--------------------------
        VIEW MANIPULATION
    --------------------------*/

    public void addTitle(String title, int size){
        TextView titleView = new TextView(context);

        titleView.setText(title);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);

        addView(titleView);
    }

    public void addStat(String metric, String value){
        LinearLayout statContainer = new LinearLayout(context);
        statContainer.setOrientation(LinearLayout.HORIZONTAL);

        addMetricTitle(statContainer, metric);
        addMetricValue(statContainer, value);

        addView(statContainer);

        metricsMap.put(metric, statContainer);

        refresh();
    }

    private void addMetricTitle(LinearLayout layout, String title){
        TextView titleView = new TextView(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1);

        titleView.setLayoutParams(params);
        titleView.setText(title);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, metricTitleSize);
        titleView.setTag(TITLE_TAG);
        titleView.setGravity(Gravity.BOTTOM);

        layout.addView(titleView);
    }

    private void addMetricValue(LinearLayout layout, String value){
        TextView titleView = new TextView(context);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        titleView.setLayoutParams(params);
        titleView.setText(value);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, metricValueSize);
        titleView.setTag(VALUE_TAG);
        titleView.setGravity(Gravity.BOTTOM);

        layout.addView(titleView);
    }

    public boolean updateStat(String title, String value){
        if(metricsMap.containsKey(title)){
            LinearLayout stat = (LinearLayout) metricsMap.get(title);
            TextView metricValue = (TextView) stat.findViewWithTag(VALUE_TAG);

            metricValue.setText(value);
            return true;
        }else{
            return false;
        }
    }

    private void refresh(){
        invalidate();
        requestLayout();
    }
}
