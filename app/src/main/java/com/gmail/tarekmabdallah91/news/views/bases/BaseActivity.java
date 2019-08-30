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

package com.gmail.tarekmabdallah91.news.views.bases;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.views.sections.SectionsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static aboutMe.AboutMeActivity.openAboutMeActivity;
import static com.gmail.tarekmabdallah91.news.utils.Constants.ZERO;
import static com.gmail.tarekmabdallah91.news.utils.Constants.makeTypeFaceTitleStyle;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.showFragment;
import static com.gmail.tarekmabdallah91.news.views.search.SearchActivity.openSearchActivity;
import static com.gmail.tarekmabdallah91.news.views.settings.SettingsActivity.openSettingsActivity;

public abstract class BaseActivity extends AppCompatActivity {

    // TODO: 07-Apr-19 to use Fb sdk for logging
    // TODO: 07-Apr-19 to use firebase for crash analytics and messaging
    // TODO: 07-Apr-19 to update the UI (Responsive UI)

    @Nullable @BindView(R.id.msg_iv)
    protected ImageView errorIV;
    @Nullable @BindView(R.id.progress_bar)
    protected View progressBar;
    @Nullable @BindView(R.id.msg_tv)
    protected TextView errorTV;
    @Nullable @BindView(R.id.msg_layout)
    protected View errorLayout;

    protected BasePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        presenter = getPresenter();
        ButterKnife.bind(this);
        initiateValues(savedInstanceState);
        setActionBar();
        if (null == savedInstanceState) setActivityWhenSaveInstanceStateNull();
        else reSetActivityWithSaveInstanceState(savedInstanceState);
    }

    protected abstract int getLayoutResId();

    protected BasePresenter getPresenter(){
        return null;
    }

    protected void setActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            customTitleTVStyle(actionBar);
        }
    }
    
    protected void customTitleTVStyle(ActionBar actionBar){
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.action_bar_title_layout);
        TextView titleTV = findViewById(R.id.action_bar_title);
        makeTypeFaceTitleStyle(titleTV);
        titleTV.setText(getActivityTitle());
    }

    protected abstract String getActivityTitle();

    public void initiateValues (@Nullable Bundle savedInstanceState){}

    public void setActivityWhenSaveInstanceStateNull() {}

    public void reSetActivityWithSaveInstanceState(Bundle savedInstanceState) {}

    public void setUI (){}

    @Override
    protected void onResume() {
        super.onResume();
        setUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_settings) openSettingsActivity(this);
        else if (id == R.id.item_search) openSearchActivity(this);
        else if (id == R.id.item_sections) {
            SectionsFragment sectionsFragment = new SectionsFragment();
            showFragment(sectionsFragment, getSupportFragmentManager(), R.id.fragmentsContainer, false);
        } else if (id == R.id.item_about_me) openAboutMeActivity(this,
                "Tarek AbdAllah",
                "Android Developer",
                "+201096071130",
                "tarekmabdallah91@gmail.com",
                "http://bit.ly/2kfdLeB",
                "http://bit.ly/2Pi2h84");
        else if (item.getItemId() == android.R.id.home) finish();
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        hideMenuItemsByIds(menu, getMenuItemIdsToHide());
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * to hide all items @param ids array from @param menu
     */
    private void hideMenuItemsByIds(Menu menu, int...ids){
        if (null != ids && ZERO < ids.length)
            for (int id:ids) menu.findItem(id).setVisible(false);
    }

    /**
     * override to put and @return  an int[] of the items wanted to be invisible in the action bar menu
     */
    protected int[] getMenuItemIdsToHide(){
        return null;
    }
}
