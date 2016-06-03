package com.intencity.interval.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.intencity.interval.functionality.R;
import com.intencity.interval.functionality.view.Completed;

/**
 * The class for the completed activity for mobile.
 *
 * Created by Nick Piscopio on 5/30/16.
 */
public class CompletedActivity extends AppCompatActivity
{
    private Completed completedInstance;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_completed);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);

        completedInstance = new Completed(getApplicationContext(), layout, MainActivity.class);
    }

    @Override
    public void onBackPressed()
    {
        completedInstance.startAppOver();
    }
}