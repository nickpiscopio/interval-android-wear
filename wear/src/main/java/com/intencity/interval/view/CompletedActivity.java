package com.intencity.interval.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.intencity.interval.functionality.R;
import com.intencity.interval.functionality.view.Completed;

/**
 * The class for the completed activity for wear.
 *
 * Created by Nick Piscopio on 5/30/16.
 */
public class CompletedActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_completed);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);

        new Completed(getApplicationContext(), layout, MainActivity.class);
    }
}