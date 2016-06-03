package com.intencity.interval.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intencity.interval.R;
import com.intencity.interval.view.activity.DemoActivity;

/**
 * Fragment class for the PagerFragment.
 *
 * Created by Nick Piscopio on 6/3/16.
 */
public class PagerFragment extends Fragment
{
    public static String FRAGMENT_PAGE = "fragment_page";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        int pageId;
        int page = getArguments().getInt(FRAGMENT_PAGE);
        switch (page)
        {
            case DemoActivity.INTERVAL:
                pageId = R.layout.fragment_demo_timer;
                break;

            case DemoActivity.WATCH:
                pageId = R.layout.fragment_demo_watch;
                break;

            case DemoActivity.DESCRIPTION:
            default:
                pageId = R.layout.fragment_demo_description;
                break;
        }

        return inflater.inflate(pageId, container, false);
    }
}