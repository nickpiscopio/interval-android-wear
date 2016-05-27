package com.intencity.interval.functionality.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.intencity.interval.functionality.R;
import com.intencity.interval.functionality.util.Constant;

public class Main
{
    private final int INTERVAL_MIN_THRESHOLD = 1;

    private int intervals;
    private int intervalSeconds;
    private int intervalRestSeconds;

    private Resources res;
    private SharedPreferences prefs;

    private TextView activeTextView;

    // FROM UI
    private Context context;

    private final int INTERVALS_ID;
    private final int INTERVAL_TIME_ID;
    private final int INTERVAL_REST_ID;

    private TextView titleTextView;
    private TextView intervalTextView;
    private TextView intervalTimeTextView;
    private TextView intervalRestTextView;
    private ImageButton incrementInterval;
    private ImageButton decrementInterval;

    private Button start;

    private Class<?> cls;

    public Main(Context context, int INTERVALS_ID, int INTERVAL_TIME_ID, int INTERVAL_REST_ID, TextView titleTextView, TextView intervalTextView, TextView intervalTimeTextView, TextView intervalRestTextView, ImageButton incrementInterval, ImageButton decrementInterval,
                Button start, Class<?> cls)
    {
        this.context = context;
        this.INTERVALS_ID = INTERVALS_ID;
        this.INTERVAL_TIME_ID = INTERVAL_TIME_ID;
        this.INTERVAL_REST_ID = INTERVAL_REST_ID;
        this.titleTextView = titleTextView;
        this.intervalTextView = intervalTextView;
        this.intervalTimeTextView = intervalTimeTextView;
        this.intervalRestTextView = intervalRestTextView;
        this.incrementInterval = incrementInterval;
        this.decrementInterval = decrementInterval;
        this.start = start;

        this.cls = cls;

        init();
    }

    public void init()
    {
        intervalTextView.setOnClickListener(intervalClickListener);
        intervalTimeTextView.setOnClickListener(intervalClickListener);
        intervalRestTextView.setOnClickListener(intervalClickListener);
        incrementInterval.setOnClickListener(incrementIntervalClickListener);
        decrementInterval.setOnClickListener(decrementIntervalClickListener);
        start.setOnClickListener(startClickListener);

        res = context.getResources();
        prefs = context.getSharedPreferences(Constant.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        intervals = prefs.getInt(Constant.BUNDLE_INTERVALS, 1);
        intervalSeconds = prefs.getInt(Constant.BUNDLE_INTERVAL_MILLIS, 10);
        intervalRestSeconds = prefs.getInt(Constant.BUNDLE_INTERVAL_REST_MILLIS, 10);

        setTextSize(intervalTextView, R.dimen.title3_size);
        setTextSize(intervalTimeTextView, R.dimen.title3_size);
        setTextSize(intervalRestTextView, R.dimen.title3_size);

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
     * Sets the text size of a textview.
     *
     * @param textView  The textview we are updating.
     * @param id        The resource dimen res id.
     */
    private void setTextSize(TextView textView, int id)
    {
        textView.setTextSize(res.getDimensionPixelSize(id));
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
            activeTextView.setBackgroundResource(0);
            setTextSize(activeTextView, R.dimen.title3_size);
        }

        activeTextView = textView;
        activeTextView.setBackgroundResource(R.drawable.outline);
        setTextSize(activeTextView, R.dimen.title_size);

        int titleResId = R.string.title_intervals;
        int gravity = Gravity.START;

        int id = textView.getId();
        if (id == INTERVAL_TIME_ID)
        {
            titleResId = R.string.title_interval_time;
            gravity = Gravity.CENTER;
        }
        else if (id == INTERVAL_REST_ID)
        {
            titleResId = R.string.title_interval_rest;
            gravity = Gravity.END;
        }

        titleTextView.setText(context.getString(titleResId));
        titleTextView.setGravity(gravity);
    }

    View.OnClickListener intervalClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            setActiveTextView((TextView) v);
        }
    };

    View.OnClickListener incrementIntervalClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int id = activeTextView.getId();
            if (id == INTERVALS_ID)
            {
                setText(intervalTextView, ++intervals);
            }
            else if (id == INTERVAL_TIME_ID)
            {
                setText(intervalTimeTextView, ++intervalSeconds);
            }
            else if (id == INTERVAL_REST_ID)
            {
                setText(intervalRestTextView, ++intervalRestSeconds);
            }
        }
    };

    View.OnClickListener decrementIntervalClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int id = activeTextView.getId();
            if (id == INTERVALS_ID && intervals > INTERVAL_MIN_THRESHOLD)
            {
                setText(intervalTextView, --intervals);
            }
            else if (id == INTERVAL_TIME_ID && intervalSeconds > INTERVAL_MIN_THRESHOLD)
            {
                setText(intervalTimeTextView, --intervalSeconds);
            }
            else if (id == INTERVAL_REST_ID && intervalRestSeconds > INTERVAL_MIN_THRESHOLD)
            {
                setText(intervalRestTextView, --intervalRestSeconds);
            }
        }
    };

    View.OnClickListener startClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(Constant.BUNDLE_INTERVALS, intervals);
            editor.putInt(Constant.BUNDLE_INTERVAL_MILLIS, intervalSeconds);
            editor.putInt(Constant.BUNDLE_INTERVAL_REST_MILLIS, intervalRestSeconds);
            editor.apply();

            Intent intent = new Intent(context, cls);
            intent.putExtra(Constant.BUNDLE_INTERVALS, intervals);
            intent.putExtra(Constant.BUNDLE_INTERVAL_MILLIS, intervalSeconds * Constant.ONE_SECOND_MILLIS);
            intent.putExtra(Constant.BUNDLE_INTERVAL_REST_MILLIS, intervalRestSeconds * Constant.ONE_SECOND_MILLIS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    };
}
