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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.paging.ReloadLayoutListener;
import com.gmail.tarekmabdallah91.news.views.articlesFragment.ItemArticlesFragment;
import com.gmail.tarekmabdallah91.news.views.bases.BaseActivityNoMenu;
import com.gmail.tarekmabdallah91.news.views.bases.BasePresenter;

import java.util.Stack;

import butterknife.BindView;

import static com.gmail.tarekmabdallah91.news.utils.Constants.IS_LOADED_BEFORE;
import static com.gmail.tarekmabdallah91.news.utils.Constants.ONE;
import static com.gmail.tarekmabdallah91.news.utils.Constants.ZERO;

public class EasyViewActivity extends BaseActivityNoMenu implements ReloadLayoutListener {

    @BindView(R.id.id_view_pager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;

    private ItemsViewPagerAdapter viewPagerAdapter;
    private Stack<Integer> stack = new Stack<>();
    private int tabPosition = ZERO;

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
    public void initiateValues(@Nullable Bundle savedInstanceState) {
        setViewPager();
    }

    private void setViewPager() {
        viewPagerAdapter = new ItemsViewPagerAdapter(getSupportFragmentManager(), this, this);
        viewPager.setOffscreenPageLimit(ONE);// keeps 3 fragments in memory
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            /*https://stackoverflow.com/a/33028501/5055780*/
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPosition = tab.getPosition();
                updateFragmentArgs(tabPosition);
                viewPager.setCurrentItem(tab.getPosition());

                if (stack.empty()) stack.push(ZERO);
                if (stack.contains(tabPosition)) {
                    int indexOfTabPosition = stack.indexOf(tabPosition);
                    stack.remove(indexOfTabPosition); // should be like this stack.remove(stack.indexOf(tabPosition));
                    stack.push(tabPosition);
                } else {
                    stack.push(tabPosition);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    @Override
    public void onRetryClick(Activity activity) {
        viewPagerAdapter.notifyDataSetChanged();
        int position = viewPager.getCurrentItem();
        ItemArticlesFragment itemArticlesFragment = updateFragmentArgs(position);
        itemArticlesFragment.setUserVisibleHint(true);
    }

    @Override
    public void onBackPressed() {
        if (stack.size() > ONE) {
            stack.pop();
            int lastElement = stack.lastElement();
            updateFragmentArgs(lastElement);
            viewPager.setCurrentItem(lastElement);
        } else super.onBackPressed();
    }

    /**
     * used to remove the key which prevent the fragment to be loaded
     * it's complex but this is the best solution I found for now to solve the viewpager's problem with loading fragment as wanted
     * simply it should load one fragment at a time and display any clicked tap any time
     * @param position of the fragment
     */
    private ItemArticlesFragment updateFragmentArgs (int position){
        ItemArticlesFragment currentItemArticlesFragment = (ItemArticlesFragment) viewPagerAdapter.getItem(position);
        Bundle args = currentItemArticlesFragment.getArguments();
        if (args != null && args.containsKey(IS_LOADED_BEFORE)) args.remove(IS_LOADED_BEFORE);
        currentItemArticlesFragment.setArguments(args);
        return currentItemArticlesFragment;
    }

    public static void openEasyViewActivity(Context context) {
        Intent openEasyViewActivity = new Intent(context, EasyViewActivity.class);
        context.startActivity(openEasyViewActivity);
    }
}
