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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tarek.news.R;
import com.example.tarek.news.apis.DataFetcherCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.tarek.news.utils.ViewsUtils.getTextFromEditText;
import static com.example.tarek.news.utils.ViewsUtils.isConnected;
import static com.example.tarek.news.utils.ViewsUtils.makeViewGone;
import static com.example.tarek.news.utils.ViewsUtils.showFailureMsg;
import static com.example.tarek.news.utils.ViewsUtils.showProgressBar;
import static com.example.tarek.news.utils.ViewsUtils.showShortToastMsg;
import static com.example.tarek.news.views.search.SearchActivity.openSearchActivity;
import static com.example.tarek.news.views.sections.SectionsActivity.openSectionsActivity;

public abstract class BaseActivity extends AppCompatActivity implements DataFetcherCallback {

    @BindView(R.id.msg_iv)
    protected ImageView errorIV;
    @BindView(R.id.progress_bar)
    protected View progressBar;
    @BindView(R.id.msg_tv)
    protected TextView errorTV;
    @BindView(R.id.msg_layout)
    protected View errorLayout;

    // TODO: 07-Apr-19 to save some articles using Room db
    // TODO: 07-Apr-19 to use paging
    // TODO: 07-Apr-19 to use Fb sdk for logging
    // TODO: 07-Apr-19 to use firebase for crash analytics and messing
    // TODO: 07-Apr-19 to use the rest APIs
    // TODO: 07-Apr-19 to use dragger 2 and RxJava
    // TODO: 07-Apr-19 to update the UI

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
        initiateValues();
        setActionBar();
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

    @Override
    protected void onResume() {
        super.onResume();
        setUI();
    }

    @Override
    public void onDataFetched(Object response) {
        showProgressBar(progressBar, false);
        makeViewGone(errorLayout); // to hide if the data reloaded after it was empty
        whenDataFetchedGetResponse(response);
    }

    @Override
    public void onFailure(Throwable t, int errorImageResId) {
        showFailureMsg(t, errorImageResId, errorLayout, progressBar, errorTV, errorIV);
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
        if (isConnected(this)) loadData();
        else handleCaseNoConnection();
    }

    /**
     * to call the api after showing the progressbar
     * can be called when reloading data is needed
     */
    protected void loadData() {
        showProgressBar(progressBar, true);
        callAPi();
        makeViewGone(errorLayout);
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
     * called when there response has empty list
     */
    protected void handleNoDataFromResponse() {
        Throwable noDataThrowable = new Throwable(getString(R.string.no_news_found));
        onFailure(noDataThrowable, R.drawable.icons8_empty_box);
    }

    /**
     * if there is not internet connection before calling the api and refresh after 1 second
     */
    protected void handleCaseNoConnection() {
        Throwable noInternetConnectionThrowable = new Throwable(getString(R.string.no_connection));
        onFailure(noInternetConnectionThrowable, android.R.drawable.stat_notify_sync);
    }

    /**
     * to reload data if the user click on the error image and it was because failure in internet connection
     */
    @OnClick(R.id.msg_iv)
    void onClickMsgIV(){
        String errorMsg = getTextFromEditText(errorTV);
        if (getString(R.string.no_connection).equals(errorMsg)) setUI();
    }

    @OnClick(R.id.msg_tv)
    void onClickMsgTV(){
        onClickMsgIV();
    }

    /**
     * override it to getIntent and call it where you need it
     */
    protected void getComingIntent() {

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
