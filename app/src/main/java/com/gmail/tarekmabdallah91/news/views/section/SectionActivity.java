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

package com.gmail.tarekmabdallah91.news.views.section;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.views.bases.BaseActivity;
import com.gmail.tarekmabdallah91.news.views.bases.BasePresenter;

import butterknife.BindView;

import static com.gmail.tarekmabdallah91.news.utils.Constants.IS_COUNTRY_SECTION;
import static com.gmail.tarekmabdallah91.news.utils.Constants.SECTION_ID_KEYWORD;
import static com.gmail.tarekmabdallah91.news.utils.Constants.TITLE_KEYWORD;

public class SectionActivity extends BaseActivity {

    @BindView(R.id.articles_recycler_view)
    protected RecyclerView articlesRecyclerView;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_section;
    }

    @Override
    protected BasePresenter getPresenter() {
        return SectionPresenter.getInstance();
    }

    @Override
    protected String getActivityTitle(){
        return presenter.getActivityTitle(this);
    }

    public static void openSectionActivity(Context context, String sectionId, String sectionTitle, boolean isCountrySection){
        Intent openSectionActivity = new Intent(context, SectionActivity.class);
        openSectionActivity.putExtra(SECTION_ID_KEYWORD, sectionId);
        openSectionActivity.putExtra(TITLE_KEYWORD, sectionTitle);
        openSectionActivity.putExtra(IS_COUNTRY_SECTION, isCountrySection);
        context.startActivity(openSectionActivity);
    }
}
