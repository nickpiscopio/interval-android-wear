package com.intencity.interval.functionality.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intencity.interval.functionality.R;
import com.intencity.interval.functionality.util.Constant;

public class Main
{
    private final int INTERVAL_MIN_THRESHOLD = 1;

    private int layoutIntervalId;
    private int layoutIntervalTimeId;
    private int layoutIntervalRestId;
    private int textViewIntervalId;
    private int textViewIntervalTimeId;
    private int textViewIntervalRestId;

    private int intervals;
    private int intervalSeconds;
    private int intervalRestSeconds;

    private SharedPreferences prefs;

    private TextView activeTextView;

    private Context context;

    // FROM UI
    private Activity activity;

    private LinearLayout intervalLayout;
    private LinearLayout intervalTimeLayout;
    private LinearLayout intervalRestLayout;

    private TextView titleTextView;
    private TextView selectedTextView;
    private TextView intervalTextView;
    private TextView intervalTimeTextView;
    private TextView intervalRestTextView;
    private ImageButton incrementInterval;
    private ImageButton decrementInterval;

    private Button start;

    private Class<?> demoActivity;
    private Class<?> intervalActivity;

    public Main(Activity activity, LinearLayout intervalLayout, LinearLayout intervalTimeLayout, LinearLayout intervalRestLayout, TextView titleTextView, TextView selectedTextView, TextView intervalTextView, TextView intervalTimeTextView,
                TextView intervalRestTextView, ImageButton incrementInterval, ImageButton decrementInterval, Button start, Class<?> demoActivity, Class<?> intervalActivity)
    {
        this.activity = activity;
        this.intervalLayout = intervalLayout;
        this.intervalTimeLayout = intervalTimeLayout;
        this.intervalRestLayout = intervalRestLayout;
        this.titleTextView = titleTextView;
        this.selectedTextView = selectedTextView;
        this.intervalTextView = intervalTextView;
        this.intervalTimeTextView = intervalTimeTextView;
        this.intervalRestTextView = intervalRestTextView;
        this.incrementInterval = incrementInterval;
        this.decrementInterval = decrementInterval;
        this.start = start;

        this.demoActivity = demoActivity;
        this.intervalActivity = intervalActivity;

        init();
    }

    /**
     * Determines which view to initialize.
     */
    public void init()
    {
        context = activity.getApplicationContext();
        prefs = context.getSharedPreferences(Constant.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        // If the user has already agreed to the terms of use.
        if(prefs.getBoolean(Constant.AGREED_TO_TERMS, false))
        {
            initMain();
        }
        else
        {
            initAgreement();
        }
    }

    /**
     * Initializes the agreement screen.
     */
    public void initAgreement()
    {
        Intent intent = new Intent(context, demoActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        activity.finish();
    }

    /**
     * Initializes the start screen.
     */
    public void initMain()
    {
        layoutIntervalId = intervalLayout.getId();
        layoutIntervalTimeId = intervalTimeLayout.getId();
        layoutIntervalRestId = intervalRestLayout.getId();
        textViewIntervalId = intervalTextView.getId();
        textViewIntervalTimeId = intervalTimeTextView.getId();
        textViewIntervalRestId = intervalRestTextView.getId();

        intervalLayout.setOnClickListener(intervalClickListener);
        intervalTimeLayout.setOnClickListener(intervalClickListener);
        intervalRestLayout.setOnClickListener(intervalClickListener);
        incrementInterval.setOnClickListener(incrementIntervalClickListener);
        decrementInterval.setOnClickListener(decrementIntervalClickListener);
        start.setOnClickListener(startClickListener);

        intervals = prefs.getInt(Constant.BUNDLE_INTERVALS, 1);
        intervalSeconds = prefs.getInt(Constant.BUNDLE_INTERVAL_MILLIS, 10);
        intervalRestSeconds = prefs.getInt(Constant.BUNDLE_INTERVAL_REST_MILLIS, 10);

        setText(intervalTextView, intervals);
        setText(intervalTimeTextView, intervalSeconds);
        setText(intervalRestTextView, intervalRestSeconds);

        setActiveTextView(intervalTextView);
    }

    /**
     * Sets the text of a textview.
     *
     * @param textView  The textview we are updating.
     * @param value     The value to set.
     */
    private void setText(TextView textView, int value)
    {
        textView.setText(String.valueOf(value));
    }

    /**
     * Sets the alpha of a layout.
     *
     * @param textView  The current textview we have selected.
     * @param alpha     The alpha we want to set to the layout.
     */
    private void setLayoutAlpha(TextView textView, float alpha)
    {
        int id = textView.getId();
        if (id == textViewIntervalId)
        {
            intervalLayout.setAlpha(alpha);
        }
        else if (id == textViewIntervalTimeId)
        {
            intervalTimeLayout.setAlpha(alpha);
        }
        else if (id == textViewIntervalRestId)
        {
            intervalRestLayout.setAlpha(alpha);
        }
    }

    /**
     * Sets the active textview.
     *
     * @param textView  The textview to set as active.
     */
    private void setActiveTextView(TextView textView)
    {
        if (activeTextView != null)
        {
            setLayoutAlpha(activeTextView, 0.66f);
        }

        activeTextView = textView;
        setLayoutAlpha(activeTextView, 1.0f);

        int titleResId = R.string.title_intervals;

        int id = textView.getId();
        if (id == textViewIntervalTimeId)
        {
            titleResId = R.string.title_interval_time;
        }
        else if (id == textViewIntervalRestId)
        {
            titleResId = R.string.title_interval_rest;
        }

        titleTextView.setText(context.getString(titleResId));
        selectedTextView.setText(activeTextView.getText().toString());
    }

    private View.OnClickListener intervalClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int id = v.getId();
            if (id == layoutIntervalId)
            {
                setActiveTextView(intervalTextView);
            }
            else if (id == layoutIntervalTimeId)
            {
                setActiveTextView(intervalTimeTextView);
            }
            else if (id == layoutIntervalRestId)
            {
                setActiveTextView(intervalRestTextView);
            }
        }
    };

    private View.OnClickListener incrementIntervalClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int id = activeTextView.getId();
            if (id == textViewIntervalId)
            {
                intervals++;
                setText(intervalTextView, intervals);
                setText(selectedTextView, intervals);
            }
            else if (id == textViewIntervalTimeId)
            {
                intervalSeconds++;
                setText(intervalTimeTextView, intervalSeconds);
                setText(selectedTextView, intervalSeconds);
            }
            else if (id == textViewIntervalRestId)
            {
                intervalRestSeconds++;
                setText(intervalRestTextView, intervalRestSeconds);
                setText(selectedTextView, intervalRestSeconds);
            }
        }
    };

    private View.OnClickListener decrementIntervalClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int id = activeTextView.getId();
            if (id == textViewIntervalId && intervals > INTERVAL_MIN_THRESHOLD)
            {
                intervals--;
                setText(intervalTextView, intervals);
                setText(selectedTextView, intervals);
            }
            else if (id == textViewIntervalTimeId && intervalSeconds > INTERVAL_MIN_THRESHOLD)
            {
                intervalSeconds--;
                setText(intervalTimeTextView, intervalSeconds);
                setText(selectedTextView, intervalSeconds);
            }
            else if (id == textViewIntervalRestId && intervalRestSeconds > INTERVAL_MIN_THRESHOLD)
            {
                intervalRestSeconds--;
                setText(intervalRestTextView, intervalRestSeconds);
                setText(selectedTextView, intervalRestSeconds);
            }
        }
    };

    private View.OnClickListener startClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(Constant.BUNDLE_INTERVALS, intervals);
            editor.putInt(Constant.BUNDLE_INTERVAL_MILLIS, intervalSeconds);
            editor.putInt(Constant.BUNDLE_INTERVAL_REST_MILLIS, intervalRestSeconds);
            editor.apply();

            Intent intent = new Intent(context, intervalActivity);
            intent.putExtra(Constant.BUNDLE_INTERVALS, intervals);
            intent.putExtra(Constant.BUNDLE_INTERVAL_MILLIS, intervalSeconds * Constant.ONE_SECOND_MILLIS);
            intent.putExtra(Constant.BUNDLE_INTERVAL_REST_MILLIS, intervalRestSeconds * Constant.ONE_SECOND_MILLIS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    };
}
