package com.intencity.interval.view.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.intencity.interval.R;
import com.intencity.interval.adapter.MenuAdapter;
import com.intencity.interval.model.MenuItem;

import java.util.ArrayList;

/**
 * This is the settings activity for Intencity.
 *
 * Created by Nick Piscopio on 1/17/15.
 */
public class MenuActivity extends AppCompatActivity
{
    private ArrayList<MenuItem> menuItems;

    private String rateTitle;
    private String contributeTitle;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_list);

        // Add the back button to the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        context = getApplicationContext();

        rateTitle = getString(R.string.title_rate);
        contributeTitle = getString(R.string.title_contribute);

        menuItems = new ArrayList<>();
//        menuItems.add(new MenuItem(getString(R.string.title_settings), null));

//        menuItems.add(new MenuItem(getString(R.string.title_app), null));
        menuItems.add(new MenuItem(getString(R.string.title_about), AboutActivity.class));
        menuItems.add(new MenuItem(getString(R.string.title_terms), TermsActivity.class, null));
        menuItems.add(new MenuItem(rateTitle, null));
        menuItems.add(new MenuItem(contributeTitle, null));


        MenuAdapter settingsAdapter = new MenuAdapter(this, R.layout.list_item_header, R.layout.list_item_standard, menuItems);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(settingsAdapter);
        listView.setOnItemClickListener(settingClicked);
    }

    /**
     * The click listener for each item clicked in the settings list.
     */
    private AdapterView.OnItemClickListener settingClicked = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            MenuItem menuItem = menuItems.get(position);

            Class cls = menuItem.getCls();

            if (cls != null)
            {
                startActivity(cls, menuItem.getBundle());
            }
            else if (menuItem.getTitle().equals(rateTitle))
            {
                String packageName = context.getPackageName();

                try
                {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
                }
                catch (ActivityNotFoundException e)
                {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                                             Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)));
                }
            }
            else if (menuItem.getTitle().equals(contributeTitle))
            {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://intencity.fit/contribute.html")));
            }
        }
    };

    /**
     * Starts an activity.
     *
     * @param cls       The class to start.
     * @param bundle    The bundle that is included with the intent if there is one.
     */
    private void startActivity(Class<?> cls, Bundle bundle)
    {
        Intent intent = new Intent(this, cls);

        if (bundle != null)
        {
            intent.putExtras(bundle);
        }

        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem menuItem)
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
}