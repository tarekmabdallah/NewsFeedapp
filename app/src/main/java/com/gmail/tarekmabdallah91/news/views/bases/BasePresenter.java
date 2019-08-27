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

package com.gmail.tarekmabdallah91.news.views.bases;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.paging.ReloadLayoutListener;

import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.getTextFromEditText;

public abstract class BasePresenter implements BaseInterface {

    private ReloadLayoutListener reloadLayoutListener;

    public void setOnClickListenerForErrorMsg(final TextView errorTV, ImageView errorIV){
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickMsgIV(errorTV);
            }
        };
        if (null != errorIV) errorIV.setOnClickListener(onClickListener);
        if (null != errorTV) errorTV.setOnClickListener(onClickListener);
    }

    /**
     * to reload data if the user click on the error image and it was because failure in internet connection
     */
    private void onClickMsgIV(TextView errorTV){
        Context context = errorTV.getContext();
        String errorMsg = getTextFromEditText(errorTV);
        if (context.getString(R.string.no_connection).equals(errorMsg)) reloadLayoutListener.onRetryClick((Activity) context);
    }

    public abstract void onRetryClick(Activity activity);

    @Override
    public void initiateValues(Activity activity, View...views) {}

    @Override
    public void initiateValuesAfterCheckSaveInstanceState() {

    }

    @Override
    public void setActivityWhenSaveInstanceStateNull() {

    }

    @Override
    public void reSetActivityWithSaveInstanceState(Bundle savedInstanceState, RecyclerView articlesRecyclerView) {

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState, RecyclerView articlesRecyclerView) {

    }

    @Override
    public void setUI(Activity activity) {

    }

    @Override
    public void showToastMsg(String msg) {

    }

    @Override
    public String getActivityTitle(Activity activity) {
        return null;
    }

    public void setReloadLayoutListener(ReloadLayoutListener reloadLayoutListener) {
        this.reloadLayoutListener = reloadLayoutListener;
    }
}
