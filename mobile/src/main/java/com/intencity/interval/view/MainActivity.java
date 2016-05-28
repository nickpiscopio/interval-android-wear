package com.intencity.interval.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intencity.interval.R;
import com.intencity.interval.functionality.view.Main;

public class MainActivity extends AppCompatActivity
{
    private Context context;

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
}
