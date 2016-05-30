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
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.support.wearable.view.BoxInsetLayout;
import android.support.wearable.view.DelayedConfirmationView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intencity.interval.functionality.R;
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
public class Interval implements DelayedConfirmationView.DelayedConfirmationListener
{
    // 1 Minute for the WARM-UP / COOL DOWN.
    private final int INJURY_PREVENTION_MILLIS = 12000;

    private long millisLeft;

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

    private DelayedConfirmationView delayedView;
    private TextView title;
    private TextView timeLeftTextView;
    private ImageButton pause;
    private LinearLayout intervalLayout;

    private CountDownTimer countDownTimer;

    private Class completedClass;

    public Interval(Context context, int intervals, int intervalSeconds, int intervalRestSeconds, BoxInsetLayout container, DelayedConfirmationView delayedView, TextView title, TextView timeLeftTextView, ImageButton pause, LinearLayout intervalLayout, Class completedClass)
    {
        this.context = context;
        this.intervals = intervals;
        this.intervalSeconds = intervalSeconds;
        this.intervalRestSeconds = intervalRestSeconds;
        this.container = container;
        this.delayedView = delayedView;
        this.title = title;
        this.timeLeftTextView = timeLeftTextView;
        this.pause = pause;
        this.intervalLayout = intervalLayout;
        this.completedClass = completedClass;

        init();
    }

    /**
     * Initializes the view.
     */
    public void init()
    {
        delayedView.setListener(this);

        warmUpTitle = context.getString(R.string.title_warm_up);
        intervalTitle = context.getString(R.string.title_interval);
        restTitle = context.getString(R.string.title_rest);
        coolDownTitle = context.getString(R.string.title_cool_down);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        res = context.getResources();

        activityState = ActivityState.WARM_UP;
        exerciseState = ExerciseState.ACTIVE;

        pause.setOnClickListener(pauseClickLister);

        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        addIntervalChart();

        startTimer(TimerState.INIT, Constant.CODE_FAILED);
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
                    stopTimer();
                    delayedView.setSelected(true);
                    pause.setImageResource(R.mipmap.play);
                    exerciseState = ExerciseState.INACTIVE;
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

    @Override
    public void onTimerFinished(View view)
    {
        // User didn't cancel, perform the action
    }

    @Override
    public void onTimerSelected(View view) {
        // User canceled, abort the action
    }

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

                    vibrationDuration = new long[]{ 0, 500 };
                    break;
                case REST:
                    setBackgroundColor(container, R.color.secondary_light);
                    title.setText(restTitle);
                    scaleTitle();
                    interval = intervalRestSeconds;

                    // 3 vibrations
                    vibrationDuration = new long[]{ 0, 100, 100, 100, 100, 100 };
                    break;
                case COOL_DOWN:
                    setBackgroundColor(container, R.color.secondary_dark);
                    title.setText(coolDownTitle);
                    scaleTitle();
                    interval = INJURY_PREVENTION_MILLIS;

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

        countDownTimer = new CountDownTimer(interval, Constant.ONE_SECOND_MILLIS)
        {
            public void onTick(long millisUntilFinished)
            {
                Interval.this.millisLeft = millisUntilFinished;
                timeLeftTextView.setText(String.valueOf(convertToSeconds(millisUntilFinished)));
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
                else
                {
                    // TODO: WILL NEED TO DO SOMETHING ELSE HERE WHEN ER ARE DONE EXERCISING.
                    timeLeftTextView.setText("0");
                }
            }
        };

        // Two seconds to cancel the action
        delayedView.setTotalTimeMs(interval);
        // Start the timer
        delayedView.start();

        countDownTimer.start();

        notifyUserOfIntervalChange(vibrationDuration);
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
     * @param duration      The pattern duration to vibrate.
     */
    private void notifyUserOfIntervalChange(long[] duration)
    {
        vibrator.vibrate(duration, Constant.CODE_FAILED);

//        try {
//            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//            Ringtone r = RingtoneManager.getRingtone(context, notification);
//            r.play();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * Pushes the completed workout view.
     */
    private void setWorkoutComplete()
    {
        notifyUserOfIntervalChange(new long[]{ 0, 500 });

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
        int height = res.getDimensionPixelSize(R.dimen.chart_interval_max_height);

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
     * @return  The percentage to muliply the height of the interval item by.
     */
    private float getPercentage(int seconds)
    {
        return (float) seconds / (float) (INJURY_PREVENTION_MILLIS / Constant.ONE_SECOND_MILLIS);
    }
}
