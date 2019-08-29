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

package com.gmail.tarekmabdallah91.smooth.ui.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.TextView;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.data.sp.SharedPreferencesHelper;
import com.gmail.tarekmabdallah91.news.views.bases.BaseActivity;

import static com.gmail.tarekmabdallah91.news.utils.Constants.EMPTY_STRING;
import static com.gmail.tarekmabdallah91.news.utils.Constants.SECTION_ID_KEYWORD;
import static com.gmail.tarekmabdallah91.news.utils.Constants.makeTypeFaceSubTitleStyle;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.getValueFromPreferencesByKey;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.makeViewVisible;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.restartActivity;


public class SimpleActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_simple;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.main_label);
    }

    @Override
    public void initiateValues(@Nullable Bundle savedInstanceState) {
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragmentsContainer) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            ArticlesListFragment articlesListFragment = new ArticlesListFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            articlesListFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragmentsContainer' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentsContainer, articlesListFragment).commit();
        }
        // get section id to be used in calling tha API
        String sectionId = getValueFromPreferencesByKey(this, R.string.sections_list_key, R.string.sections_list_default_value);
        // is it world news ?
        boolean isDefaultSection = null == sectionId;
        if (isDefaultSection) sectionId = getString(R.string.sections_list_default_value);
        getIntent().putExtra(SECTION_ID_KEYWORD, sectionId);
    }

    @Override
    public void setUI() {
        SharedPreferencesHelper sharedPreferencesHelper = SharedPreferencesHelper.getInstance(this);
        boolean isSPUpdated = sharedPreferencesHelper.getIsSPUpdated();
        if (isSPUpdated) {
            sharedPreferencesHelper.saveIsSPUpdated(false);
            restartActivity(this); // TODO: 8/29/2019 to de attach the fragment here
        }
    }

    @Override // to avoid showing back arrow in the tool bar in MainSectionActivity
    protected void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) customTitleTVStyle(actionBar);
    }

    @Override
    protected void customTitleTVStyle(ActionBar actionBar) {
        super.customTitleTVStyle(actionBar);
        SharedPreferencesHelper sharedPreferencesHelper = SharedPreferencesHelper.getInstance(this);
        String sectionName = sharedPreferencesHelper.getMainSection();
        if (EMPTY_STRING.equals(sectionName)) sectionName = getString(R.string.world_news_label);
        TextView subtitleTV = findViewById(R.id.action_bar_subtitle);
        makeViewVisible(subtitleTV);
        makeTypeFaceSubTitleStyle(subtitleTV);
        subtitleTV.setText(sectionName);
    }

    public static void openSimpleActivity(Context context){
        Intent openSimpleActivity = new Intent(context, SimpleActivity.class);
        context.startActivity(openSimpleActivity);
    }
}

