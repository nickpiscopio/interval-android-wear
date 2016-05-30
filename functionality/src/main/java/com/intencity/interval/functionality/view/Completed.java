package com.intencity.interval.functionality.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.intencity.interval.functionality.util.Constant;
import com.intencity.interval.functionality.util.CountDownTimer;

/**
 * The class implementation for the completed activity.
 *
 * Created by Nick Piscopio on 5/30/16.
 */
public class Completed
{
    private final int SCREEN_DURATION = 3000;

    private CountDownTimer timer;

    private Context context;

    private RelativeLayout layout;

    private Class firstScreen;

    public Completed(Context context, RelativeLayout layout, Class firstScreen)
    {
        this.context = context;
        this.layout = layout;
        this.firstScreen = firstScreen;

        init();
    }

    /**
     * Initializes the views.
     */
    private void init()
    {
        layout.setOnClickListener(layoutClickListener);

        // Timer to start the app over after a designated time.
        timer = new CountDownTimer(SCREEN_DURATION, Constant.ONE_SECOND_MILLIS)
        {
            public void onTick(long millisUntilFinished) { }

            public void onFinish()
            {
                startAppOver();
            }
        };

        timer.start();
    }

    /**
     * Starts the app over.
     */
    public void startAppOver()
    {
        timer.cancel();

        Intent intent = new Intent(context, firstScreen);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
    }

    /**
     * The click listener for the layout.
     */
    private View.OnClickListener layoutClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            startAppOver();
        }
    };
}