package com.intencity.interval.view;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.support.wearable.view.DelayedConfirmationView;
import android.support.wearable.view.DismissOverlayView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intencity.interval.R;
import com.intencity.interval.functionality.util.Constant;
import com.intencity.interval.functionality.view.Interval;

public class IntervalActivity extends WearableActivity
{
    private DismissOverlayView dismissOverlay;
    private GestureDetector gestureDetector;

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
        setAmbientEnabled();

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

        initWearableMethods();
    }

    /**
     * Initializes the wearable independent methods.
     */
    private void initWearableMethods()
    {
        // Obtain the DismissOverlayView element
        dismissOverlay = (DismissOverlayView) findViewById(R.id.dismiss_overlay);
        dismissOverlay.setIntroText(R.string.dismiss_view_instructions);
        dismissOverlay.showIntroIfNecessary();

        // Configure a gesture detector
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener()
        {
            public void onLongPress(MotionEvent ev)
            {
                dismissOverlay.show();
            }
        });
    }

    @Override
    protected void onDestroy()
    {
        interval.destroy();
        interval = null;

        super.onDestroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        return gestureDetector.onTouchEvent(ev) || super.onTouchEvent(ev);
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