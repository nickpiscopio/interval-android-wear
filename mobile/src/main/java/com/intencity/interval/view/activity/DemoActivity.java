package com.intencity.interval.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.intencity.interval.R;
import com.intencity.interval.adapter.ViewPagerAdapter;
import com.intencity.interval.view.fragment.PagerFragment;

/**
 * Screens to demo the application before the user starts Interval.
 *
 * Created by Nick Piscopio on 6/3/16.
 */
public class DemoActivity extends FragmentActivity
{
    public static final int DESCRIPTION = 0;
    public static final int INTERVAL = 1;
    public static final int WATCH = 2;
    public static final int AGREEMENT = 3;

    private static final int PAGER_SELECTED_RESOURCE = R.mipmap.pager_selected;
    private static final int PAGER_UNSELECTED_RESOURCE = R.mipmap.pager_unselected;

    private ViewPager mPager;

    private RelativeLayout navigation;

    private ImageView pager0;
    private ImageView pager1;
    private ImageView pager2;

    private FloatingActionButton next;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        // Instantiate a ViewPager and a PagerAdapter.
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(getNewPagerFragment(DESCRIPTION), "");
        adapter.addFrag(getNewPagerFragment(INTERVAL), "");
        adapter.addFrag(getNewPagerFragment(WATCH), "");
        adapter.addFrag(new AgreementActivity(), "");

        navigation = (RelativeLayout) findViewById(R.id.navigation);

        pager0 = (ImageView) findViewById(R.id.pager_0);
        pager1 = (ImageView) findViewById(R.id.pager_1);
        pager2 = (ImageView) findViewById(R.id.pager_2);

        next = (FloatingActionButton) findViewById(R.id.button_next);
        next.setOnClickListener(nextListener);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(adapter);
        mPager.addOnPageChangeListener(pageChangeListener);
        mPager.setCurrentItem(0);
    }

    /**
     * Creates a new PagerFragment for the view pager.
     *
     * @param position  The position the fragment will be added.
     *
     * @return  The PagerFragment
     */
    private PagerFragment getNewPagerFragment(int position)
    {
        Bundle bundle = new Bundle();
        bundle.putInt(PagerFragment.FRAGMENT_PAGE, position);

        PagerFragment sliderFragment = new PagerFragment();
        sliderFragment.setArguments(bundle);

        return sliderFragment;
    }

    @Override
    public void onBackPressed()
    {
        if (mPager.getCurrentItem() == 0)
        {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        }
        else
        {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    /**
     * The listener for the page changing.
     */
    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener()
    {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

        @Override
        public void onPageSelected(int position)
        {
            switch (position)
            {
                case DESCRIPTION:
                    pager0.setImageResource(PAGER_SELECTED_RESOURCE);
                    pager1.setImageResource(PAGER_UNSELECTED_RESOURCE);
                    pager2.setImageResource(PAGER_UNSELECTED_RESOURCE);
                    break;
                case INTERVAL:
                    pager0.setImageResource(PAGER_UNSELECTED_RESOURCE);
                    pager1.setImageResource(PAGER_SELECTED_RESOURCE);
                    pager2.setImageResource(PAGER_UNSELECTED_RESOURCE);
                    break;
                case WATCH:
                    pager0.setImageResource(PAGER_UNSELECTED_RESOURCE);
                    pager1.setImageResource(PAGER_UNSELECTED_RESOURCE);
                    pager2.setImageResource(PAGER_SELECTED_RESOURCE);

                    next.setVisibility(View.VISIBLE);
                    navigation.setVisibility(View.VISIBLE);
                    break;
                case AGREEMENT:
                    next.setVisibility(View.GONE);
                    navigation.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) { }
    };

    /**
     * The listener for the next button.
     */
    private View.OnClickListener nextListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            mPager.setCurrentItem(mPager.getCurrentItem() + 1);
        }
    };
}