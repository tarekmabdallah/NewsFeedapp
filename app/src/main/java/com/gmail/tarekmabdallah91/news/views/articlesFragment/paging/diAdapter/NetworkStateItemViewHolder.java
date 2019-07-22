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

package com.gmail.tarekmabdallah91.news.views.articlesFragment.paging.diAdapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.utils.NetworkState;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.getTextFromEditText;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.handelErrorMsg;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.showNormalProgressBar;

//@AutoFactory(implementing = ItemAdapterViewHolderFactory.class) will be used when inject item adapter
public class NetworkStateItemViewHolder extends RecyclerView.ViewHolder {

    private ItemAdapter itemAdapter;
    @BindView(R.id.msg_iv)
    protected ImageView errorIV;
    @BindView(R.id.progress_bar)
    protected View progressBar;
    @BindView(R.id.msg_tv)
    protected TextView errorTV;
    @BindView(R.id.msg_layout)
    protected View errorLayout;

    NetworkStateItemViewHolder(ItemAdapter itemAdapter, @NonNull ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_bar_no_data_tv_layout, parent, false));
        this.itemAdapter = itemAdapter;
        ButterKnife.bind(this, itemView);
    }

    void bindView(NetworkState networkState) {
        boolean isStateRunning = networkState != null && networkState.getStatus() == NetworkState.Status.RUNNING;
        showNormalProgressBar(progressBar, isStateRunning);
        handelErrorMsg(networkState, errorLayout, progressBar, errorTV, errorIV);
    }

    /**
     * to reload data if the user click on the error image and it was because failure in internet connection
     */
    @OnClick(R.id.msg_iv)
    void onClickMsgIV() {
        String errorMsg = getTextFromEditText(errorTV);
        if (itemAdapter.context.getString(R.string.no_connection).equals(errorMsg))
            itemAdapter.itemClickListener.onRetryClick();
    }

    @OnClick(R.id.msg_tv)
    void onClickMsgTV() {
        onClickMsgIV();
    }
}
