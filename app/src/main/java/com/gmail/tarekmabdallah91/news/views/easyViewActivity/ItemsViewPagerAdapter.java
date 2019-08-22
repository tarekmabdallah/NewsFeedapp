package com.gmail.tarekmabdallah91.news.views.easyViewActivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.views.articlesFragment.ArticlesFragment;

import static com.gmail.tarekmabdallah91.news.utils.Constants.ZERO;

/**
 * Changing the parent class of my PageAdapter from android.support.v4.app.FragmentPagerAdapter
 * to android.support.v4.app.FragmentStatePagerAdapter solve my ViewPager display issue on "second time"!
 */
public class ItemsViewPagerAdapter extends FragmentStatePagerAdapter {

    private String[] sectionsLabels;
    private String[] sectionsIds;

    ItemsViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        sectionsLabels = context.getResources().getStringArray(R.array.sections_labels);
        sectionsIds = context.getResources().getStringArray(R.array.sections_ids);
    }

    @Override
    public Fragment getItem(int position) {
        ArticlesFragment articlesFragment = ArticlesFragment.getInstance();
        String sectionId = sectionsIds[position];
        articlesFragment.setSectionId(sectionId);
        articlesFragment.setPosition(position);
        return articlesFragment;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return sectionsLabels[position];
    }

    @Override
    public int getCount() {
        return sectionsLabels == null ? ZERO : sectionsLabels.length;
    }
}