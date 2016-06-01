package com.intencity.interval.view;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.Button;
import android.widget.TextView;

import com.intencity.interval.R;
import com.intencity.interval.functionality.view.Agreement;

/**
 * The class for the agreement activity for wear.
 *
 * Created by Nick Piscopio on 6/1/16.
 */
public class AgreementActivity extends WearableActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);

        TextView terms = (TextView) findViewById(R.id.terms);
        Button next = (Button) findViewById(R.id.next);

        new Agreement(this, terms, next, TermsActivity.class, MainActivity.class);
    }
}