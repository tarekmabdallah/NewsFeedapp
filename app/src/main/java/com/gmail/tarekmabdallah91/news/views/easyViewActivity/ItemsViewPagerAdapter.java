package com.gmail.tarekmabdallah91.news.views.easyViewActivity;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.paging.reloadLayoutListener;
import com.gmail.tarekmabdallah91.news.views.articlesFragment.ArticlesFragment;

import java.util.ArrayList;

import static com.gmail.tarekmabdallah91.news.utils.Constants.ONE;
import static com.gmail.tarekmabdallah91.news.utils.Constants.ZERO;

/**
 * Changing the parent class of my PageAdapter from android.support.v4.app.FragmentPagerAdapter
 * to android.support.v4.app.FragmentStatePagerAdapter solve my ViewPager display issue on "second time"!
 */
public class ItemsViewPagerAdapter extends FragmentStatePagerAdapter {

    private final String[] SECTIONS_LABELS;
    private final int SECTIONS_COUNT;
    private final ArrayList<Fragment> FRAGMENTS;

    ItemsViewPagerAdapter(FragmentManager fm, Context context, reloadLayoutListener reloadLayoutListener) {
        super(fm);
        SECTIONS_LABELS = context.getResources().getStringArray(R.array.easy_view_sections_labels);
        final String[] SECTIONS_IDS = context.getResources().getStringArray(R.array.easy_view_sections_ids);
        SECTIONS_COUNT = SECTIONS_LABELS.length - ONE;
        FRAGMENTS = new ArrayList<>();
        for (int i = SECTIONS_COUNT; i >= ZERO; i--)
            FRAGMENTS.add(ArticlesFragment.newInstance(SECTIONS_IDS[getReversPosition(i)], reloadLayoutListener));
    }

    private int getReversPosition (int position){
        return (SECTIONS_COUNT - position);
    }

    @Override
    public Fragment getItem(int position) {
        return FRAGMENTS.get(getReversPosition(position));
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return SECTIONS_LABELS[getReversPosition(position)];
    }

    @Override
    public int getCount() {
        return SECTIONS_LABELS == null ? ZERO : SECTIONS_LABELS.length;
    }

    /**
     * to reload the fragment in view pager
     * https://stackoverflow.com/a/26517867/5055780
     * doesn't work
     */
    @Override
    public int getItemPosition(Object object) {
        // POSITION_NONE makes it possible to reload the PagerAdapter
        return POSITION_NONE;
    }
}