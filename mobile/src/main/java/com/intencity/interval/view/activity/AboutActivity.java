package com.intencity.interval.view.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intencity.interval.R;

/**
 * This is the about activity for Interval.
 *
 * Created by Nick Piscopio on 5/26/16.
 */
public class AboutActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Add the back button to the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        try
        {
            String versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            TextView versionTextView = (TextView) findViewById(R.id.text_view_version);
            versionTextView.setText(versionName);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            Log.i("Couldn't find version", "Couldn't find version " + e.toString());
        }

        LinearLayout intencityLayout = (LinearLayout) findViewById(R.id.intencity_layout);
        intencityLayout.setOnClickListener(intencityClickListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private View.OnClickListener intencityClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://intencity.fit")));
        }
    };
}