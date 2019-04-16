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

package com.example.tarek.news.views.main;

import com.example.tarek.news.R;
import com.example.tarek.news.views.section.SectionActivity;

import static com.example.tarek.news.utils.Constants.SECTION_ID_KEYWORD;
import static com.example.tarek.news.utils.ViewsUtils.getValueFromPreferencesByKey;

public class MainActivity extends SectionActivity {

    private boolean isDefaultSection; // is it world news ?

    @Override
    protected void initiateValues() {
        super.initiateValues();
        String sectionId = getValueFromPreferencesByKey(this, R.string.sections_list_key, R.string.sections_list_default_value);
        isDefaultSection = null == sectionId;
        if (isDefaultSection) sectionId = getString(R.string.sections_list_default_value);
        getIntent().putExtra(SECTION_ID_KEYWORD, sectionId);
    }

    @Override
    protected String getActivityTitle() {
        String sectionName =  getString(R.string.world_news_label); // TODO: 4/15/2019 to get selected name not id from SP even if the user return to sny activity either MainActivity
        if (!isDefaultSection) sectionName = getValueFromPreferencesByKey(this, R.string.sections_list_key, R.string.sections_list_default_value); // temp solution
        return getString(R.string.main_label) + String.format("(%s)", sectionName);
    }

    @Override // to avoid showing back arrow in the tool bar in MainActivity
    protected void setActionBar() {
        setTitle(getActivityTitle());
    }
}
