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

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.paging.PagedList;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.tarekmabdallah91.news.apis.APIClient;
import com.gmail.tarekmabdallah91.news.data.sp.SharedPreferencesHelper;
import com.gmail.tarekmabdallah91.news.models.articles.Article;
import com.gmail.tarekmabdallah91.news.paging.ItemAdapter;
import com.gmail.tarekmabdallah91.news.paging.ItemViewModel;
import com.gmail.tarekmabdallah91.news.paging.OnArticleClickListener;
import com.gmail.tarekmabdallah91.news.utils.NetworkState;
import com.gmail.tarekmabdallah91.news.views.bases.BasePresenter;

import retrofit2.Retrofit;

import static com.gmail.tarekmabdallah91.news.utils.Constants.EMPTY_STRING;
import static com.gmail.tarekmabdallah91.news.utils.Constants.FOUR;
import static com.gmail.tarekmabdallah91.news.utils.Constants.IS_FAVOURITE_LIST;
import static com.gmail.tarekmabdallah91.news.utils.Constants.ONE;
import static com.gmail.tarekmabdallah91.news.utils.Constants.PAGE_SIZE;
import static com.gmail.tarekmabdallah91.news.utils.Constants.SCROLL_POSITION;
import static com.gmail.tarekmabdallah91.news.utils.Constants.SECTION_ID_KEYWORD;
import static com.gmail.tarekmabdallah91.news.utils.Constants.THREE;
import static com.gmail.tarekmabdallah91.news.utils.Constants.TITLE_KEYWORD;
import static com.gmail.tarekmabdallah91.news.utils.Constants.TWO;
import static com.gmail.tarekmabdallah91.news.utils.Constants.ZERO;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.handelErrorMsg;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.makeViewVisible;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.restartActivity;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.showNormalProgressBar;
import static com.gmail.tarekmabdallah91.news.views.section.SectionActivity.openSectionActivity;
import static com.gmail.tarekmabdallah91.news.views.webViewActivity.WebViewActivity.openArticleWebViewActivity;

public class SectionPresenter extends BasePresenter {

    private static SectionPresenter sectionPresenter;
    private Retrofit retrofit;
    private ItemAdapter itemAdapter;
    private int scrollPosition;

    public static SectionPresenter getInstance() {
        if (null == sectionPresenter) sectionPresenter = new SectionPresenter();
        return sectionPresenter;
    }

    @Override
    public void initiateValues(Activity activity, View...views) {
        setOnClickListenerForErrorMsg((TextView) views[TWO], (ImageView) views[THREE]);
        retrofit = APIClient.getInstance(activity);
        setItemAdapter(activity);
        setArticlesRecyclerView((RecyclerView) views[FOUR]);
        //must set ViewModel here to recalled if the screen rotated
        if (IS_FAVOURITE_LIST.equals(getSectionId(activity))) setDbViewModel();
        else setPagingViewModel(activity,null,views);
    }

    @Override
    public String getActivityTitle(Activity activity){
        Intent comingIntent = activity.getIntent();
        return comingIntent.getStringExtra(TITLE_KEYWORD);
    }

    @Override
    public void initiateValuesAfterCheckSaveInstanceState() {

    }

    @Override
    public void setActivityWhenSaveInstanceStateNull() {

    }

    @Override
    public void reSetActivityWithSaveInstanceState(Bundle savedInstanceState, RecyclerView articlesRecyclerView) {
        scrollPosition = savedInstanceState.getInt(SCROLL_POSITION);
//        moveRecyclerViewToPosition(articlesRecyclerView, scrollPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState, RecyclerView articlesRecyclerView) {
        int scrollingPosition = getRecyclerViewPosition(articlesRecyclerView);
        savedInstanceState.putInt (SCROLL_POSITION, scrollingPosition);
    }

    @Override
    public void setUI(Activity activity) {
        SharedPreferencesHelper sharedPreferencesHelper = SharedPreferencesHelper.getInstance(activity);
        boolean isSPUpdated = sharedPreferencesHelper.getIsSPUpdated();
        if (isSPUpdated) {
            sharedPreferencesHelper.saveIsSPUpdated(false);
            restartActivity(activity);
        }
    }

    @Override
    public void showToastMsg(String msg) {

    }

    @Override
    public void onRetryClick(Activity activity) {
        restartActivity(activity);
    }

    private int getRecyclerViewPosition(RecyclerView articlesRecyclerView){
        int[] scrollPosition = new int[]{ZERO, ZERO};
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) articlesRecyclerView.getLayoutManager();
        if (null != linearLayoutManager) {
            int position = linearLayoutManager.findFirstVisibleItemPosition();
            View v = linearLayoutManager.getChildAt(position);
            int offset = (v == null) ? ZERO : (v.getTop() - linearLayoutManager.getPaddingTop());
            scrollPosition [ZERO] = position;
            scrollPosition [ONE] = offset;
            return position;
        }
        return ZERO;
    }

    /**
     * to scroll the recycler view to wanted position
     * https://stackoverflow.com/a/43505830/5055780
     */
    private void moveRecyclerViewToPosition(RecyclerView articlesRecyclerView, int scrollPosition){
        Context context = articlesRecyclerView.getContext();
        LinearLayoutManager layoutManager = (LinearLayoutManager) articlesRecyclerView.getLayoutManager();
        LinearSmoothScroller smoothScroller = new LinearSmoothScroller(context) {
            @Override protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
        smoothScroller.setTargetPosition(scrollPosition);
        if (null != layoutManager)
            layoutManager.startSmoothScroll(smoothScroller);
//            layoutManager.scrollToPositionWithOffset(scrollPosition, 0);
    }

    private String getSectionId(Activity activity) {
        return activity.getIntent().getStringExtra(SECTION_ID_KEYWORD);
    }

    /**
     * @param views errorLayout, progressBar, errorTV, errorIV, articlesRecyclerView
     */
    private void setPagingViewModel(final Activity activity, String searchKeyword, final View... views){
        makeViewVisible(views[ONE]);
        String sectionId = getSectionId(activity);
        ItemViewModel itemViewModel =  new ItemViewModel(activity, sectionId, searchKeyword, retrofit);
        itemViewModel.getItemPagedList().observe((LifecycleOwner) activity, new Observer<PagedList<Article>>() {
            @Override
            public void onChanged(@Nullable PagedList<Article> items) {
                itemAdapter.submitList(items);
            }
        });
        itemViewModel.getNetworkState().observe((LifecycleOwner) activity, new Observer<NetworkState>() {
            @Override
            public void onChanged(@Nullable NetworkState networkState) {
                observeNetworkState(activity, networkState, views);
                // must called here after the layout of progress bar is gone
                if (ZERO < scrollPosition) {
                    moveRecyclerViewToPosition((RecyclerView) views[FOUR],scrollPosition);
                    scrollPosition = ZERO;
                }
            }
        });
    }

    private void setDbViewModel() {
//        DbViewModel viewModel = ViewModelProviders.of(this).get(DbViewModel.class);
//        viewModel.getData().observe(this, new Observer<List<Article>>() {
//
//            @Override
//            public void onChanged(@Nullable List<Article> articlesInDb) {
//                if (null != articlesInDb && !articlesInDb.isEmpty())
//                    itemAdapter.swapList(articlesInDb);
//                else {
//                    observeNetworkState(new NetworkState(NetworkState.Status.FAILED,
//                            getString(R.string.empty_db_msg)));
//                    makeViewGone(progressBar);
//                }
//            }
//        });
    }

    private void setItemAdapter(final Activity activity){
        itemAdapter = new ItemAdapter();
        itemAdapter.setOnArticleClickListener(new OnArticleClickListener() {
            @Override
            public void onClickArticle(Article article) {
                openArticleWebViewActivity(activity, article);
            }

            @Override
            public void onClickArticleSection(Article article) {
                String sectionName = article.getSectionName();
                String activityTitle = activity.getTitle().toString();
                if (!activityTitle.equals(sectionName))
                    openSectionActivity(activity, article.getSectionId(), article.getSectionName(), false);
            }
        });
    }

    public ItemAdapter getItemAdapter() {
        return itemAdapter;
    }

    private void setArticlesRecyclerView(RecyclerView articlesRecyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(articlesRecyclerView.getContext());
        articlesRecyclerView.setLayoutManager(layoutManager);
        articlesRecyclerView.setHasFixedSize(true);
        articlesRecyclerView.setAdapter(itemAdapter);
    }

    /**
     * @param views errorLayout, progressBar, errorTV, errorIV, articlesRecyclerView
     */
    private void observeNetworkState(Activity activity, @Nullable NetworkState networkState, View... views){
        itemAdapter.setNetworkState(networkState);
        boolean hasExtraRows = itemAdapter.hasExtraRow();
        showNormalProgressBar(views[ONE], hasExtraRows);
        String errorMsg = EMPTY_STRING;
        if (null != networkState) errorMsg = networkState.getMsg();
        // when the page size is larger than the total size in the Guardian API >> change it to equal 1 and restart the activity
        final String HTTP_BAD_REQUEST = "HTTP 400 Bad Request";
        if (HTTP_BAD_REQUEST.equals(errorMsg)){
            PAGE_SIZE /= TWO ;
            setPagingViewModel(activity,null,views);
        }
        handelErrorMsg(networkState, views);
    }

}
