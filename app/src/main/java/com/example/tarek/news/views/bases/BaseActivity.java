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

package com.example.tarek.news.views.bases;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tarek.news.R;

import butterknife.ButterKnife;

import static com.example.tarek.news.utils.Constants.EMPTY_STRING;
import static com.example.tarek.news.utils.Constants.ZERO;
import static com.example.tarek.news.utils.ViewsUtils.commitFragment;
import static com.example.tarek.news.utils.ViewsUtils.showShortToastMsg;
import static com.example.tarek.news.views.search.SearchActivity.openSearchActivity;
import static com.example.tarek.news.views.sections.SectionsActivity.openSectionsActivity;

public abstract class BaseActivity extends AppCompatActivity {

    // TODO: 07-Apr-19 to save some articles using Room db
    // TODO: 07-Apr-19 to use paging
    // TODO: 07-Apr-19 to use Fb sdk for logging
    // TODO: 07-Apr-19 to use firebase for crash analytics and messing
    // TODO: 07-Apr-19 to use the rest APIs
    // TODO: 07-Apr-19 to use dragger 2 and RxJava
    // TODO: 07-Apr-19 to update the UI
    // TODO: 4/11/2019 add rotating func.

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
        initiateValues();
        setActionBar();
        setTitle(getSectionTitle());
        if (null == savedInstanceState) {
            setActivityWhenSaveInstanceStateNull();
        } else {
            reSetActivityWithSaveInstanceState(savedInstanceState);
        }
    }

    protected void setActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    protected String getSectionTitle(){
        return EMPTY_STRING;
    }

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
        if (id == R.id.item_settings)
//            startActivity(openSettingsActivity(this));
            showShortToastMsg(this, "sorry, not ready now");
        else if (id == R.id.item_search) startActivity(openSearchActivity(this));
        else if (id == R.id.item_sections) startActivity(openSectionsActivity(this));
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

    /**
     * called when the activity created for the first time (WhenSaveInstanceStateNull)
     */
    protected void setActivityWhenSaveInstanceStateNull() {
    }

    /**
     * called when the device rotated  (WhenSaveInstanceState IS NOT Null)
     *
     * @param savedInstanceState -
     */
    protected void reSetActivityWithSaveInstanceState(Bundle savedInstanceState) {
    }

    /**
     * to init some values once and will be called every time the device rotated
     */
    protected void initiateValues() {
    }

    /**
     * override it to set the  UI
     * it is called in onResume() to recalled each time the activity resumed
     */
    protected void setUI() {
    }

    /**
     * to be override to call the api
     */
    protected void callAPi() {

    }

    /**
     * called after hiding the loading indicator (progress bar)
     */
    protected void whenDataFetchedGetResponse(Object response) {

    }

    /**
     * override it to getIntent and call it where you need it
     */
    protected void getComingIntent() {

    }

    /**
     * to @return value from intent by @param key
     */
    protected Object getValueFromIntent(String key) {
        Intent comingIntent = getIntent();
        return comingIntent.getStringExtra(key);
    }

    protected void setFragmentToCommit (BaseFragment fragment, int containerId){
        FragmentManager fm = getSupportFragmentManager();
        commitFragment(fm, containerId, fragment);
    }

    /**
     * simple method to show Toast Msg and control all Toast's style in the app
     *
     * @param msg which will be shown
     */
    protected void showToastMsg(String msg) {
        showShortToastMsg(this, msg);
    }

    /**
     * @return the layout resource id for each activity
     */
    protected abstract int getLayoutResId();
}
