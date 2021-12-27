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

package com.gmail.tarekmabdallah91.news.views.sections;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.views.bases.BaseActivity;
import com.gmail.tarekmabdallah91.news.views.bases.BasePresenter;

/**
 * to view the 3 options of sections like countries, sections or db articles
 */
public class SectionsActivity extends BaseActivity {

    @Override
    public int getLayoutResId() {
        return R.layout.activity_sections;
    }

    @Override
    protected int[] getMenuItemIdsToHide() {
        return new int[]{R.id.item_search, R.id.item_sections};
    }

    @Override
    public void initiateValues(@Nullable Bundle savedInstanceState) {
        SectionsFragment sectionsFragment = SectionsFragment.getInstance();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragment_sections_container, sectionsFragment).commit();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    protected String getActivityTitle() {
        return getString(R.string.sections_label);
    }

    public static void openSectionsActivity (Context context){
        Intent openSectionsActivity = new Intent(context, SectionsActivity.class);
        context.startActivity(openSectionsActivity);
    }
}
