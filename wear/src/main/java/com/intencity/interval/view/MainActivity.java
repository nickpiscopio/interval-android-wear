package com.intencity.interval.view;

import android.content.Context;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.intencity.interval.R;
import com.intencity.interval.functionality.view.Main;

public class MainActivity extends WearableActivity
{
    private Context context;

    private RelativeLayout container;

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        container = (RelativeLayout) findViewById(R.id.container);

        intervalLayout = (LinearLayout) findViewById(R.id.layout_intervals);
        intervalTimeLayout = (LinearLayout) findViewById(R.id.layout_interval_time);
        intervalRestLayout = (LinearLayout) findViewById(R.id.layout_interval_rest);

        titleTextView = (TextView)findViewById(R.id.title);
        selectedTextView = (TextView)findViewById(R.id.text_view_editing);
        intervalTextView = (TextView)findViewById(R.id.text_view_interval);
        intervalTimeTextView = (TextView)findViewById(R.id.text_view_interval_time);
        intervalRestTextView = (TextView)findViewById(R.id.text_view_interval_rest);
        incrementInterval = (ImageButton)findViewById(R.id.button_increment);
        decrementInterval = (ImageButton)findViewById(R.id.button_decrement);
        start = (Button) findViewById(R.id.start);

        new Main(context, intervalLayout, intervalTimeLayout, intervalRestLayout, titleTextView, selectedTextView, intervalTextView, intervalTimeTextView, intervalRestTextView, incrementInterval, decrementInterval, start, IntervalActivity.class);
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
//            container.setBackgroundColor(ContextCompat.getColor(context, android.R.color.black));
//            intervalTextView.setTextColor(ContextCompat.getColor(context, android.R.color.white));
//        }
//        else
//        {
//            container.setBackgroundColor(ContextCompat.getColor(context, R.color.secondary_dark));
//            intervalTextView.setTextColor(ContextCompat.getColor(context, R.color.secondary_dark));
//        }
    }
}
