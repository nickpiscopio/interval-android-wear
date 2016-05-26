package com.intencity.interval.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.wearable.activity.WearableActivity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.intencity.interval.R;
import com.intencity.interval.functionality.view.Main;

public class MainActivity extends WearableActivity
{
    private final int INCREMENT_INTERVAL_ID = R.id.button_increment_interval;
    private final int DECREMENT_INTERVAL_ID = R.id.button_decrement_interval;
    private final int INCREMENT_INTERVAL_TIME_ID = R.id.button_increment_interval_time;
    private final int DECREMENT_INTERVAL_TIME_ID = R.id.button_decrement_interval_time;
    private final int INCREMENT_INTERVAL_REST_ID = R.id.button_increment_interval_rest;
    private final int DECREMENT_INTERVAL_REST_ID = R.id.button_decrement_interval_rest;

    private Context context;

    private ScrollView containerView;
    private TextView intervalTextView;
    private TextView intervalTimeTextView;
    private TextView intervalRestTextView;
    private ImageButton incrementInterval;
    private ImageButton decrementInterval;
    private ImageButton incrementIntervalTime;
    private ImageButton decrementIntervalTime;
    private ImageButton incrementIntervalRest;
    private ImageButton decrementIntervalRest;

    private Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAmbientEnabled();

        context = getApplicationContext();

        containerView = (ScrollView)findViewById(R.id.container);
        intervalTextView = (TextView)findViewById(R.id.text_view_interval);
        intervalTimeTextView = (TextView)findViewById(R.id.text_view_interval_time);
        intervalRestTextView = (TextView)findViewById(R.id.text_view_interval_rest);
        incrementInterval = (ImageButton)findViewById(INCREMENT_INTERVAL_ID);
        decrementInterval = (ImageButton)findViewById(DECREMENT_INTERVAL_ID);
        incrementIntervalTime = (ImageButton)findViewById(INCREMENT_INTERVAL_TIME_ID);
        decrementIntervalTime = (ImageButton)findViewById(DECREMENT_INTERVAL_TIME_ID);
        incrementIntervalRest = (ImageButton)findViewById(INCREMENT_INTERVAL_REST_ID);
        decrementIntervalRest = (ImageButton)findViewById(DECREMENT_INTERVAL_REST_ID);
        start = (Button) findViewById(R.id.start);

        new Main(context, INCREMENT_INTERVAL_ID, DECREMENT_INTERVAL_ID, INCREMENT_INTERVAL_TIME_ID, DECREMENT_INTERVAL_TIME_ID, INCREMENT_INTERVAL_REST_ID, DECREMENT_INTERVAL_REST_ID, intervalTextView, intervalTimeTextView,
                 intervalRestTextView, incrementInterval, decrementInterval, incrementIntervalTime, decrementIntervalTime, incrementIntervalRest, decrementIntervalRest, start, IntervalActivity.class);
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails)
    {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient()
    {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient()
    {
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay()
    {
        if (isAmbient())
        {
            containerView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.black));
            intervalTextView.setTextColor(ContextCompat.getColor(context, android.R.color.white));
        }
        else
        {
            containerView.setBackground(null);
            intervalTextView.setTextColor(ContextCompat.getColor(context, R.color.secondary_dark));
        }
    }
}
