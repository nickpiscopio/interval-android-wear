package com.intencity.interval.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.intencity.interval.R;
import com.intencity.interval.functionality.view.Agreement;

/**
 * The class for the agreement activity for mobile.
 *
 * Created by Nick Piscopio on 6/1/16.
 */
public class AgreementActivity extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_agreement, container, false);

        TextView terms = (TextView) view.findViewById(R.id.terms);
        Button next = (Button) view.findViewById(R.id.next);

        new Agreement(this.getActivity(), terms, next, TermsActivity.class, MainActivity.class);

        return view;
    }
}