package com.intencity.interval.functionality.view;

import android.content.Context;
import android.content.Intent;
import android.support.wearable.view.BoxInsetLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.intencity.interval.functionality.util.Constant;

public class Main
{
    private final int INTERVAL_MIN_THRESHOLD = 1;

    private int intervals = 1;
    private int intervalSeconds = 10;
    private int intervalRestSeconds = 10;

    // FROM UI
    private Context context;

    private final int INCREMENT_INTERVAL_ID;
    private final int DECREMENT_INTERVAL_ID;
    private final int INCREMENT_INTERVAL_TIME_ID;
    private final int DECREMENT_INTERVAL_TIME_ID;
    private final int INCREMENT_INTERVAL_REST_ID;
    private final int DECREMENT_INTERVAL_REST_ID;

    private BoxInsetLayout containerView;
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

    private Class<?> cls;

    public Main(Context context, int INCREMENT_INTERVAL_ID, int DECREMENT_INTERVAL_ID, int INCREMENT_INTERVAL_TIME_ID, int DECREMENT_INTERVAL_TIME_ID, int INCREMENT_INTERVAL_REST_ID, int DECREMENT_INTERVAL_REST_ID,
                BoxInsetLayout containerView, TextView intervalTextView, TextView intervalTimeTextView, TextView intervalRestTextView, ImageButton incrementInterval, ImageButton decrementInterval, ImageButton incrementIntervalTime,
                ImageButton decrementIntervalTime, ImageButton incrementIntervalRest, ImageButton decrementIntervalRest, Button start, Class<?> cls)
    {
        this.context = context;
        this.INCREMENT_INTERVAL_ID = INCREMENT_INTERVAL_ID;
        this.DECREMENT_INTERVAL_ID = DECREMENT_INTERVAL_ID;
        this.INCREMENT_INTERVAL_TIME_ID = INCREMENT_INTERVAL_TIME_ID;
        this.DECREMENT_INTERVAL_TIME_ID = DECREMENT_INTERVAL_TIME_ID;
        this.INCREMENT_INTERVAL_REST_ID = INCREMENT_INTERVAL_REST_ID;
        this.DECREMENT_INTERVAL_REST_ID = DECREMENT_INTERVAL_REST_ID;
        this.containerView = containerView;
        this.intervalTextView = intervalTextView;
        this.intervalTimeTextView = intervalTimeTextView;
        this.intervalRestTextView = intervalRestTextView;
        this.incrementInterval = incrementInterval;
        this.decrementInterval = decrementInterval;
        this.incrementIntervalTime = incrementIntervalTime;
        this.decrementIntervalTime = decrementIntervalTime;
        this.incrementIntervalRest = incrementIntervalRest;
        this.decrementIntervalRest = decrementIntervalRest;
        this.start = start;

        this.cls = cls;

        init();
    }

    public void init()
    {
        incrementInterval.setOnClickListener(incrementIntervalClickListener);
        decrementInterval.setOnClickListener(decrementIntervalClickListener);
        incrementIntervalTime.setOnClickListener(incrementIntervalClickListener);
        decrementIntervalTime.setOnClickListener(decrementIntervalClickListener);
        incrementIntervalRest.setOnClickListener(incrementIntervalClickListener);
        decrementIntervalRest.setOnClickListener(decrementIntervalClickListener);
        start.setOnClickListener(startClickListener);

        setIntervals(intervalTextView, intervals);
        setIntervals(intervalTimeTextView, intervalSeconds);
        setIntervals(intervalRestTextView, intervalRestSeconds);
    }

    private void setIntervals(TextView textView, int time)
    {
        textView.setText(String.valueOf(time));
    }

    View.OnClickListener incrementIntervalClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int id = v.getId();
            if (id == INCREMENT_INTERVAL_ID)
            {
                setIntervals(intervalTextView, ++intervals);
            }
            else if (id == INCREMENT_INTERVAL_TIME_ID)
            {
                setIntervals(intervalTimeTextView, ++intervalSeconds);
            }
            else if (id == INCREMENT_INTERVAL_REST_ID)
            {
                setIntervals(intervalRestTextView, ++intervalRestSeconds);
            }
        }
    };

    View.OnClickListener decrementIntervalClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int id = v.getId();
            if (id == DECREMENT_INTERVAL_ID && intervals > INTERVAL_MIN_THRESHOLD)
            {
                setIntervals(intervalTextView, --intervals);
            }
            else if (id == DECREMENT_INTERVAL_TIME_ID && intervalSeconds > INTERVAL_MIN_THRESHOLD)
            {
                setIntervals(intervalTimeTextView, --intervalSeconds);
            }
            else if (id == DECREMENT_INTERVAL_REST_ID && intervalRestSeconds > INTERVAL_MIN_THRESHOLD)
            {
                setIntervals(intervalRestTextView, --intervalRestSeconds);
            }
        }
    };

    View.OnClickListener startClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(context, cls);
            intent.putExtra(Constant.BUNDLE_INTERVALS, intervals);
            intent.putExtra(Constant.BUNDLE_INTERVAL_MILLIS, intervalSeconds * Constant.ONE_SECOND_MILLIS);
            intent.putExtra(Constant.BUNDLE_INTERVAL_REST_MILLIS, intervalRestSeconds * Constant.ONE_SECOND_MILLIS);
            context.startActivity(intent);
        }
    };
}