package com.intencity.interval.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intencity.interval.R;
import com.intencity.interval.functionality.view.Main;

public class MainActivity extends AppCompatActivity
{
    private final int MENU_ID = R.id.menu;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout intervalLayout = (LinearLayout) findViewById(R.id.layout_intervals);
        LinearLayout intervalTimeLayout = (LinearLayout) findViewById(R.id.layout_interval_time);
        LinearLayout intervalRestLayout = (LinearLayout) findViewById(R.id.layout_interval_rest);

        TextView titleTextView = (TextView)findViewById(R.id.title);
        TextView selectedTextView = (TextView)findViewById(R.id.text_view_editing);
        TextView intervalTextView = (TextView)findViewById(R.id.text_view_interval);
        TextView intervalTimeTextView = (TextView)findViewById(R.id.text_view_interval_time);
        TextView intervalRestTextView = (TextView)findViewById(R.id.text_view_interval_rest);
        ImageButton incrementInterval = (ImageButton)findViewById(R.id.button_increment);
        ImageButton decrementInterval = (ImageButton)findViewById(R.id.button_decrement);
        Button start = (Button) findViewById(R.id.start);

        new Main(this, intervalLayout, intervalTimeLayout, intervalRestLayout, titleTextView, selectedTextView, intervalTextView, intervalTimeTextView, intervalRestTextView, incrementInterval, decrementInterval, start, AgreementActivity.class, IntervalActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case MENU_ID:
                startActivity(new Intent(this, MenuActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
