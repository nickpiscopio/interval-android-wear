package com.intencity.interval.view;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;

import com.intencity.interval.R;

/**
 * This is the terms activity for Intencity.
 *
 * Created by Nick Piscopio on 6/1/16.
 */
public class TermsActivity extends WearableActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
    }
}