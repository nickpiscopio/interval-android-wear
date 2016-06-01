package com.intencity.interval.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.intencity.interval.R;
import com.intencity.interval.functionality.view.Agreement;

/**
 * The class for the agreement activity for mobile.
 *
 * Created by Nick Piscopio on 6/1/16.
 */
public class AgreementActivity extends Activity
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