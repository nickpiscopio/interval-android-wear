package com.intencity.interval.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intencity.interval.R;
import com.intencity.interval.functionality.util.CircleProgressBar;
import com.intencity.interval.functionality.util.Constant;
import com.intencity.interval.functionality.view.Interval;

public class IntervalActivity extends AppCompatActivity
{
    private Interval interval;

    private BoxInsetLayout container;

    private CircleProgressBar progressBar;
    private TextView title;
    private TextView timeLeftTextView;
    private ImageButton pause;
    private LinearLayout intervalLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interval);

        Context context = getApplicationContext();

        // Add the back button to the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }

        Bundle extras = getIntent().getExtras();

        int intervals = extras.getInt(Constant.BUNDLE_INTERVALS);
        int intervalSeconds = extras.getInt(Constant.BUNDLE_INTERVAL_MILLIS);
        int intervalRestSeconds = extras.getInt(Constant.BUNDLE_INTERVAL_REST_MILLIS);

        container = (BoxInsetLayout) findViewById(R.id.container);
        intervalLayout = (LinearLayout) findViewById(R.id.layout_intervals);
        title = (TextView) findViewById(R.id.title);
        timeLeftTextView = (TextView) findViewById(R.id.time_left);
        pause = (ImageButton) findViewById(R.id.pause);

        progressBar = (CircleProgressBar) findViewById(R.id.progress_bar);

        interval = new Interval(context, intervals, intervalSeconds, intervalRestSeconds, container, progressBar, title, timeLeftTextView, pause, intervalLayout, CompletedActivity.class);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void onBackPressed()
    {
        interval.destroy();
        interval = null;

        super.onBackPressed();
    }
}