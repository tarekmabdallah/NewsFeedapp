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

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.views.bases.BaseActivity;

import static com.gmail.tarekmabdallah91.news.utils.Constants.TITLE_KEYWORD;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.putSomeValuesInIntent;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.showFragment;


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
            ArticlesListFragment articlesListFragment = ArticlesListFragment.newInstance();
            showFragment(articlesListFragment, getSupportFragmentManager(), R.id.fragmentsContainer, false);
        }
    }


//     @Override
//    public void setUI() {
//        SharedPreferencesHelper sharedPreferencesHelper = SharedPreferencesHelper.getInstance(this);
//        boolean isSPUpdated = sharedPreferencesHelper.getIsSPUpdated();
//        if (isSPUpdated) {
//            sharedPreferencesHelper.saveIsSPUpdated(false);
//            restartActivity(this); // TODO: 8/29/2019 to de attach the fragment here
//        }
//    }
//
//    @Override // to avoid showing back arrow in the tool bar in MainSectionActivity
//    protected void setActionBar() {
//        ActionBar actionBar = getSupportActionBar();
//        if (null != actionBar) customTitleTVStyle(actionBar);
//    }
//
//    @Override
//    protected void customTitleTVStyle(ActionBar actionBar) {
//        super.customTitleTVStyle(actionBar);
//        SharedPreferencesHelper sharedPreferencesHelper = SharedPreferencesHelper.getInstance(this);
//        String sectionName = sharedPreferencesHelper.getMainSection();
//        if (EMPTY_STRING.equals(sectionName)) sectionName = getString(R.string.world_news_label);
//        TextView subtitleTV = findViewById(R.id.action_bar_subtitle);
//        makeViewVisible(subtitleTV);
//        makeTypeFaceSubTitleStyle(subtitleTV);
//        subtitleTV.setText(sectionName);
//    }

    public static void openSimpleActivity(Context context, String sectionId, String sectionTitle, String countrySection){
        Intent openSimpleActivity = new Intent(context, SimpleActivity.class);
        openSimpleActivity.putExtra(TITLE_KEYWORD, sectionTitle);
        putSomeValuesInIntent(openSimpleActivity, sectionId, countrySection);
        context.startActivity(openSimpleActivity);
    }
}

