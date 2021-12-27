/*
 * Copyright 2019 tarekmabdallah91@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gmail.tarekmabdallah91.news.views.mainSection;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.TextView;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.data.sp.SharedPreferencesHelper;
import com.gmail.tarekmabdallah91.smooth.ui.view.SimpleActivity;

import static com.gmail.tarekmabdallah91.news.utils.Constants.COUNTRY_SECTION;
import static com.gmail.tarekmabdallah91.news.utils.Constants.EMPTY_STRING;
import static com.gmail.tarekmabdallah91.news.utils.Constants.makeTypeFaceSubTitleStyle;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.getValueFromPreferencesByKey;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.makeViewVisible;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.putSomeValuesInIntent;

public class MainSectionActivity extends SimpleActivity {

    @Override
    public void initiateValues(@Nullable Bundle savedInstanceState) {
        super.initiateValues(savedInstanceState);
        // get section id to be used in calling tha API
        String sectionId = getValueFromPreferencesByKey(this, R.string.sections_list_key, R.string.sections_list_default_value);
        // is it world news ?
        boolean isDefaultSection = null == sectionId;
        if (isDefaultSection) sectionId = getString(R.string.sections_list_default_value);
        putSomeValuesInIntent(getIntent(), sectionId, null);
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.main_label);
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

    public static void openMainSectionActivity(Context context, boolean isCountrySection){
        Intent openMainSectionActivity = new Intent(context, MainSectionActivity.class);
        openMainSectionActivity.putExtra(COUNTRY_SECTION, isCountrySection);
        context.startActivity(openMainSectionActivity);
    }

}
