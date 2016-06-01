package com.intencity.interval.functionality.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.intencity.interval.functionality.R;
import com.intencity.interval.functionality.util.Constant;

/**
 * The class implementation for the agreement activity.
 *
 * Created by Nick Piscopio on 6/1/16.
 */
public class Agreement
{
    private Context context;

    private Activity activity;

    private TextView terms;
    private Button next;
    private Class termsActivity;
    private Class mainActivity;

    public Agreement(Activity activity, TextView terms, Button next, Class termsActivity, Class mainActivity)
    {
        this.activity = activity;
        this.terms = terms;
        this.next = next;
        this.termsActivity = termsActivity;
        this.mainActivity = mainActivity;

        init();
    }

    /**
     * Initializes the views.
     */
    private void init()
    {
        context = activity.getApplicationContext();

        SpannableStringBuilder builder = new SpannableStringBuilder();

        String[] checkBoxString = terms.getText().toString().split("@");
        int termsStringLength = checkBoxString.length;

        for (int i = 0; i < termsStringLength; i++)
        {
            if (i % 2 != 0)
            {
                String termsSnippet = checkBoxString[i];

                SpannableString spannable = new SpannableString(termsSnippet);
                spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.primary)),
                                  0, termsSnippet.length(), 0);

                builder.append(spannable);
            }
            else
            {
                builder.append(checkBoxString[i]);
            }

            terms.setText(builder, TextView.BufferType.SPANNABLE);
            terms.setOnClickListener(termsClickListener);
        }

        next.setOnClickListener(nextClickListener);
    }

    /**
     * The click listener for the next button.
     */
    private View.OnClickListener termsClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(context, termsActivity);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    };

    /**
     * The click listener for the next button.
     */
    private View.OnClickListener nextClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            SharedPreferences.Editor editor = context.getSharedPreferences(Constant.SHARED_PREFERENCES, Context.MODE_PRIVATE).edit();
            editor.putBoolean(Constant.AGREED_TO_TERMS, true);
            editor.apply();

            Intent intent = new Intent(context, mainActivity);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            activity.finish();
        }
    };
}