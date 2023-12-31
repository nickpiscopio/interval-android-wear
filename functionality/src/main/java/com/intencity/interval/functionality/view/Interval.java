package com.intencity.interval.functionality.view;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.support.wearable.view.BoxInsetLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intencity.interval.functionality.R;
import com.intencity.interval.functionality.util.CircleProgressBar;
import com.intencity.interval.functionality.util.Constant;
import com.intencity.interval.functionality.util.CountDownTimer;
import com.intencity.interval.functionality.util.states.ActivityState;
import com.intencity.interval.functionality.util.states.ExerciseState;
import com.intencity.interval.functionality.util.states.IntervalState;
import com.intencity.interval.functionality.util.states.TimerState;

/**
 * A util class for the interval activity.
 *
 * Created by Nick Piscopio on 5/26/16.
 */
public class Interval
{
    // 1 Minute for the WARM-UP / COOL DOWN.
    private final int INJURY_PREVENTION_MILLIS = 60000;

    private long millisLeft;

    private int intervalLayoutHeight;
    private int intervalItem = 0;
    private int currentInterval = 0;

    private String warmUpTitle;
    private String intervalTitle;
    private String restTitle;
    private String coolDownTitle;

    private LayoutInflater inflater;
    private Resources res;

    private ActivityState activityState;
    private ExerciseState exerciseState;

    private Vibrator vibrator;

    // View
    private Context context;

    private int intervals = 1;
    private int intervalSeconds = 10;
    private int intervalRestSeconds = 10;

    private BoxInsetLayout container;

    private TextView title;
    private TextView timeLeftTextView;
    private ImageView pause;
    private LinearLayout timeLeftlayout;
    private LinearLayout intervalLayout;

    private CountDownTimer countDownTimer;
    private CircleProgressBar progressBar;

    private Class completedClass;

    public Interval(Context context, int intervals, int intervalSeconds, int intervalRestSeconds, BoxInsetLayout container, CircleProgressBar progressBar, TextView title, TextView timeLeftTextView, ImageView pause, LinearLayout timeLeftlayout, LinearLayout intervalLayout, Class completedClass)
    {
        this.context = context;
        this.intervals = intervals;
        this.intervalSeconds = intervalSeconds;
        this.intervalRestSeconds = intervalRestSeconds;
        this.container = container;
        this.progressBar = progressBar;
        this.title = title;
        this.timeLeftTextView = timeLeftTextView;
        this.pause = pause;
        this.timeLeftlayout = timeLeftlayout;
        this.intervalLayout = intervalLayout;
        this.completedClass = completedClass;

        init();
    }

    /**
     * Initializes the view.
     */
    public void init()
    {
        warmUpTitle = context.getString(R.string.title_warm_up);
        intervalTitle = context.getString(R.string.title_interval);
        restTitle = context.getString(R.string.title_rest);
        coolDownTitle = context.getString(R.string.title_cool_down);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        res = context.getResources();

        activityState = ActivityState.WARM_UP;
        exerciseState = ExerciseState.ACTIVE;

        timeLeftlayout.setOnClickListener(pauseClickLister);

        intervalLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener()
        {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom)
            {
                // Preventing extra work because method will be called many times.
                if(intervalLayoutHeight == (bottom - top))
                {
                    return;
                }

                intervalLayoutHeight = (bottom - top);

                addIntervalChart();

                startTimer(TimerState.INIT, Constant.CODE_FAILED);
            }
        });

        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    /**
     * Pauses the timer.
     */
    public void pause()
    {
        stopTimer();
        pause.setImageResource(R.mipmap.play);
        exerciseState = ExerciseState.INACTIVE;
    }

    /**
     * Stops the class.
     */
    public void destroy()
    {
        stopTimer();
    }

    View.OnClickListener pauseClickLister = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (exerciseState)
            {
                case ACTIVE:
                    pause();
                    break;
                case INACTIVE:
                    startTimer(TimerState.RESTART, millisLeft);
                    pause.setImageResource(R.mipmap.pause);
                    exerciseState = ExerciseState.ACTIVE;
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * Starts the timer.
     *
     * @param state         The current state of the timer.
     * @param millisLeft    The milliseconds left on the timer.
     */
    private void startTimer(TimerState state, long millisLeft)
    {
        // The time we are displaying on the interval count down.
        int interval = 0;
        int soundResId = Constant.CODE_FAILED;
        long[] vibrationDuration = { 0, 250 };

        if (state == TimerState.INIT)
        {
            if (countDownTimer != null)
            {
                stopTimer();
            }

            switch (activityState)
            {
                case WARM_UP:
                    setBackgroundColor(container, R.color.secondary_dark);
                    title.setText(warmUpTitle);
                    scaleTitle();
                    interval = INJURY_PREVENTION_MILLIS;

                    // 2 vibrations
                    vibrationDuration = new long[]{ 0, 250, 250, 250 };
                    break;
                case INTERVAL:
                    setBackgroundColor(container, R.color.primary);
                    title.setText(intervalTitle);
                    scaleTitle();
                    interval = intervalSeconds;

                    soundResId = R.raw.interval_beep;
                    vibrationDuration = new long[]{ 0, 500 };
                    break;
                case REST:
                    setBackgroundColor(container, R.color.secondary_light);
                    title.setText(restTitle);
                    scaleTitle();
                    interval = intervalRestSeconds;

                    soundResId = R.raw.rest_beep;
                    // 3 vibrations
                    vibrationDuration = new long[]{ 0, 100, 100, 100, 100, 100 };
                    break;
                case COOL_DOWN:
                    setBackgroundColor(container, R.color.secondary_dark);
                    title.setText(coolDownTitle);
                    scaleTitle();
                    interval = INJURY_PREVENTION_MILLIS;

                    soundResId = R.raw.start_stop_beep;
                    // 2 vibrations
                    vibrationDuration = new long[]{ 0, 250, 250, 250 };
                    break;
                default:
                    break;
            }

            if (intervalItem > 0)
            {
                setIntervalItemColor(intervalItem - 1, IntervalState.INACTIVE);
            }

            setIntervalItemColor(intervalItem, IntervalState.ACTIVE);
        }
        else
        {
            interval = (int) millisLeft;
        }

        // Every 2 milliseconds, the timer updates.
        countDownTimer = new CountDownTimer(interval, 2)
        {
            public void onTick(long millisUntilFinished)
            {
                Interval.this.millisLeft = millisUntilFinished;
                timeLeftTextView.setText(String.valueOf(convertToSeconds(millisUntilFinished)));

                long completedMillis = getTotalMillis() - millisUntilFinished;
                float completedPercentage = ((float)completedMillis / (float)getTotalMillis()) * (float) 100;
                progressBar.setProgress(completedPercentage);
            }

            public void onFinish()
            {
                intervalItem++;

                boolean stillExercising = activityState != ActivityState.COOL_DOWN;

                switch (activityState)
                {
                    case WARM_UP:
                        activityState = ActivityState.INTERVAL;
                        break;
                    case INTERVAL:
                        currentInterval++;
                        activityState = currentInterval >= intervals ? ActivityState.COOL_DOWN : ActivityState.REST;
                        break;
                    case REST:
                        activityState = ActivityState.INTERVAL;
                        break;
                    case COOL_DOWN:
                        setWorkoutComplete();
                    default:
                        // Do nothing
                        break;
                }

                if (stillExercising)
                {
                    // We start the timer over until we finish all the intervals.
                    startTimer(TimerState.INIT, Constant.CODE_FAILED);
                }
            }
        };

        countDownTimer.start();

        notifyUserOfIntervalChange(soundResId, vibrationDuration);
    }

    /**
     * Stops the timer.
     */
    private void stopTimer()
    {
        countDownTimer.cancel();
    }

    /**
     * Notifies the user of an interval change.
     *
     * @param soundResId    The resource id for the sound to play.
     * @param duration      The pattern duration to vibrate.
     */
    private void notifyUserOfIntervalChange(int soundResId, long[] duration)
    {
        vibrator.vibrate(duration, Constant.CODE_FAILED);

        if (soundResId > Constant.CODE_FAILED)
        {
            MediaPlayer mediaPlayer = MediaPlayer.create(context, soundResId);
            mediaPlayer.start();
        }
    }

    /**
     * Pushes the completed workout view.
     */
    private void setWorkoutComplete()
    {
        notifyUserOfIntervalChange(R.raw.start_stop_beep, new long[]{ 0, 500 });

        Intent intent = new Intent(context, completedClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
    }

    /**
     * Sets the interval color for the chart item.
     *
     * @param tag       The tag of the view to set the color.
     * @param state     The state the interval item is.
     */
    private void setIntervalItemColor(int tag, IntervalState state)
    {
        View view = intervalLayout.findViewWithTag(tag);
        view.setBackgroundColor(ContextCompat.getColor(context, state == IntervalState.ACTIVE ? R.color.accent : android.R.color.white));
    }

    /**
     * Adds the interval chart to the UI.
     */
    private void addIntervalChart()
    {
        // The first item will be 0, so we don't need to increment it.
        int intervalItems = 0;
        insertIntervalItem(ActivityState.WARM_UP, intervalItems);

        for (int i = 0; i < intervals; i++)
        {
            insertIntervalItem(ActivityState.INTERVAL, ++intervalItems);

            if (i != intervals - 1)
            {
                insertIntervalItem(ActivityState.REST, ++intervalItems);
            }
        }

        insertIntervalItem(ActivityState.COOL_DOWN, ++intervalItems);
    }

    /**
     * Inserts an interval item to the interval chart.
     *
     * @param state         The interval item to add.
     * @param tagNumber     The incrementer to tag each interval item.
     */
    private void insertIntervalItem(ActivityState state, int tagNumber)
    {
        int height = intervalLayoutHeight;

        switch (state)
        {
            case INTERVAL:
                height *= getPercentage(convertToSeconds(intervalSeconds));
                break;
            case REST:
                height *= getPercentage(convertToSeconds(intervalRestSeconds));
                break;
            case WARM_UP:
            case COOL_DOWN:
            default:
                // We want this to be the max height since they are warm-up and cool down.
                height *= getPercentage(convertToSeconds(INJURY_PREVENTION_MILLIS));
                break;
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, height, 1.0f);

        View v = inflater.inflate(R.layout.view_chart_interval, null);
        View item = v.findViewById(R.id.item);
        item.setTag(tagNumber);
        v.setLayoutParams(params);

        intervalLayout.addView(v);
    }

    /**
     * Fades the new background color in.
     *
     * @param view      The view we are changing.
     * @param colorRes  The resource color of the background.
     */
    private void setBackgroundColor(View view, int colorRes)
    {
        int fadeDuration = 750;
        int color = Color.TRANSPARENT;
        Drawable background = view.getBackground();
        if (background instanceof ColorDrawable)
        {
            color = ((ColorDrawable)background).getColor();
        }

        ObjectAnimator colorFade = ObjectAnimator.ofObject(view, "backgroundColor", new ArgbEvaluator(), color, ContextCompat.getColor(context, colorRes));
        colorFade.setDuration(fadeDuration);
        colorFade.start();
    }

    /**
     * Fades the new background color in.
     *
     * @param view      The textview we are changing.
     * @param colorRes  The resource color of the background.
     * @param duration  The duration it takes in millis to change.
     */
    private void setColor(TextView view, int colorRes, int duration)
    {
        ObjectAnimator colorFade = ObjectAnimator.ofObject(view, "textColor", new ArgbEvaluator(), view.getCurrentTextColor(), ContextCompat.getColor(context, colorRes));
        colorFade.setDuration(duration);
        colorFade.start();
    }

    /**
     * Scales the title so the user sees a change.
     */
    private void scaleTitle()
    {
        final int duration = 300;
        float scale = 1.4f;

        final ObjectAnimator anim = ObjectAnimator.ofPropertyValuesHolder(title, PropertyValuesHolder.ofFloat("scaleX", scale), PropertyValuesHolder.ofFloat("scaleY", scale));
        anim.setDuration(duration);
        anim.setRepeatCount(1);
        anim.setRepeatMode(ObjectAnimator.REVERSE);

        anim.start();

        // This needs to be 0.
        // If it isn't 0, then the color never changes for the title.
        setColor(title, android.R.color.white, 0);
        setColor(title, R.color.white_transparent, 750);
    }

    /**
     * Converts milliseconds to seconds.
     *
     * @param millisToConvert   The milliseconds to convert.
     *
     * @return  The converted seconds.
     */
    private int convertToSeconds(long millisToConvert)
    {
        float seconds = (float) Math.round((float) millisToConvert / (float) Constant.ONE_SECOND_MILLIS);
        return (int) seconds;
    }

    /**
     * Converts the seconds inputted to the percentage of INJURY_PREVENTION_MILLIS.
     * We use this to create the height of each inteval item.
     *
     * @param seconds   The seconds of the interval item.
     *
     * @return  The percentage to multiply the height of the interval item by.
     */
    private float getPercentage(int seconds)
    {
        int maxMillis = INJURY_PREVENTION_MILLIS;
        if (maxMillis < intervalSeconds || maxMillis < intervalRestSeconds)
        {
            maxMillis = intervalSeconds > intervalRestSeconds ? intervalSeconds : intervalRestSeconds;
        }

        return (float) seconds / (float) (maxMillis / Constant.ONE_SECOND_MILLIS);
    }
}
