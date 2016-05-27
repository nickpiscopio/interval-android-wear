package com.intencity.interval.view;

import android.content.Context;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.intencity.interval.R;
import com.intencity.interval.functionality.view.Main;

public class MainActivity extends WearableActivity
{
    private final int INTERVALS_ID = R.id.text_view_interval;
    private final int INTERVAL_TIME_ID = R.id.text_view_interval_time;
    private final int INTERVAL_REST_ID = R.id.text_view_interval_rest;

    private Context context;

    private ScrollView containerView;
    private TextView titleTextView;
    private TextView intervalTextView;
    private TextView intervalTimeTextView;
    private TextView intervalRestTextView;
    private ImageButton incrementInterval;
    private ImageButton decrementInterval;

    private Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        containerView = (ScrollView)findViewById(R.id.container);
        titleTextView = (TextView)findViewById(R.id.title);
        intervalTextView = (TextView)findViewById(R.id.text_view_interval);
        intervalTimeTextView = (TextView)findViewById(R.id.text_view_interval_time);
        intervalRestTextView = (TextView)findViewById(R.id.text_view_interval_rest);
        incrementInterval = (ImageButton)findViewById(R.id.button_increment);
        decrementInterval = (ImageButton)findViewById(R.id.button_decrement);
        start = (Button) findViewById(R.id.start);

        new Main(context, INTERVALS_ID, INTERVAL_TIME_ID, INTERVAL_REST_ID, titleTextView, intervalTextView, intervalTimeTextView, intervalRestTextView, incrementInterval, decrementInterval, start, IntervalActivity.class);
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
//        if (isAmbient())
//        {
//            containerView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.black));
//            intervalTextView.setTextColor(ContextCompat.getColor(context, android.R.color.white));
//        }
//        else
//        {
//            containerView.setBackground(null);
//            intervalTextView.setTextColor(ContextCompat.getColor(context, R.color.secondary_dark));
//        }
    }
}
