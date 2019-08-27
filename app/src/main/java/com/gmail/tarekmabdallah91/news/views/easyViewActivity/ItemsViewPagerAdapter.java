/*
 *
 * Copyright 2019 tarekmabdallah91@gmail.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.gmail.tarekmabdallah91.news.views.easyViewActivity;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.paging.ReloadLayoutListener;
import com.gmail.tarekmabdallah91.news.views.articlesFragment.ItemArticlesFragment;

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

    ItemsViewPagerAdapter(FragmentManager fm, Context context, ReloadLayoutListener reloadLayoutListener) {
        super(fm);
        SECTIONS_LABELS = context.getResources().getStringArray(R.array.easy_view_sections_labels);
        final String[] SECTIONS_IDS = context.getResources().getStringArray(R.array.easy_view_sections_ids);
        SECTIONS_COUNT = SECTIONS_LABELS.length - ONE;
        FRAGMENTS = new ArrayList<>();
        for (int i = SECTIONS_COUNT; i >= ZERO; i--)
            FRAGMENTS.add(ItemArticlesFragment.newInstance(SECTIONS_IDS[getReversPosition(i)], reloadLayoutListener));
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