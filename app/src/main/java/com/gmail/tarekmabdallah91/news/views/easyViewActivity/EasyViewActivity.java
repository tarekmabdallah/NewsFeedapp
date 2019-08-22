package com.gmail.tarekmabdallah91.news.views.easyViewActivity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.views.bases.BaseActivityNoMenu;
import com.gmail.tarekmabdallah91.news.views.bases.BasePresenter;

import butterknife.BindView;

import static com.gmail.tarekmabdallah91.news.utils.Constants.PAGE_SIZE;
import static com.gmail.tarekmabdallah91.news.utils.Constants.TEN;

public class EasyViewActivity extends BaseActivityNoMenu {

    @BindView(R.id.id_view_pager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.app_name);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_easy_view;
    }

    @Override
    public void initiateValues() {
        setViewPager();
    }

    private void setViewPager() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                PAGE_SIZE = TEN;
//                ItemsViewPagerAdapter itemsViewPagerAdapter = (ItemsViewPagerAdapter)viewPager.getAdapter();
//                ArticlesFragment articlesFragment = (ArticlesFragment) itemsViewPagerAdapter.getItem(position);
//                articlesFragment.setPosition(position);
//                String sectionId = (String) itemsViewPagerAdapter.getPageTitle(position);
//                Log.d(SCROLL_POSITION, position + " " + sectionId);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

//        viewPager.setOffscreenPageLimit(ONE); // to set how many page instances you want the system to (skip with out loading) keep in memory on either side of your current page. As a result, more memory will be consumed.

        ItemsViewPagerAdapter viewPagerAdapter = new ItemsViewPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(viewPagerAdapter);


        tabLayout.setupWithViewPager(viewPager);
    }

    public static void openEasyViewActivity(Context context) {
        Intent openEasyViewActivity = new Intent(context, EasyViewActivity.class);
        context.startActivity(openEasyViewActivity);
    }

}
