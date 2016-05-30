package com.intencity.interval.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.support.wearable.view.DelayedConfirmationView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intencity.interval.R;
import com.intencity.interval.functionality.util.Constant;
import com.intencity.interval.functionality.view.Interval;

public class IntervalActivity extends AppCompatActivity
{
    private Interval interval;

    private BoxInsetLayout container;

    private DelayedConfirmationView delayedView;
    private TextView title;
    private TextView timeLeftTextView;
    private ImageButton pause;
    private LinearLayout intervalLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interval);

        Bundle extras = getIntent().getExtras();

        int intervals = extras.getInt(Constant.BUNDLE_INTERVALS);
        int intervalSeconds = extras.getInt(Constant.BUNDLE_INTERVAL_MILLIS);
        int intervalRestSeconds = extras.getInt(Constant.BUNDLE_INTERVAL_REST_MILLIS);

        container = (BoxInsetLayout) findViewById(R.id.container);
        intervalLayout = (LinearLayout) findViewById(R.id.layout_intervals);
        title = (TextView) findViewById(R.id.title);
        timeLeftTextView = (TextView) findViewById(R.id.time_left);
        pause = (ImageButton) findViewById(R.id.pause);

        delayedView = (DelayedConfirmationView) findViewById(R.id.delayed_confirm);

        interval = new Interval(getApplicationContext(), intervals, intervalSeconds, intervalRestSeconds, container, delayedView, title, timeLeftTextView, pause, intervalLayout, CompletedActivity.class);
    }

    @Override
    public void onBackPressed()
    {
        interval.destroy();
        interval = null;

        super.onBackPressed();
    }
}