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

package com.gmail.tarekmabdallah91.news.views.articlesFragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.views.bases.BaseFragment;

import butterknife.BindView;

import static com.gmail.tarekmabdallah91.news.utils.Constants.ONE;
import static com.gmail.tarekmabdallah91.news.utils.Constants.SCROLL_POSITION;
import static com.gmail.tarekmabdallah91.news.utils.Constants.SECTION_ID_KEYWORD;
import static com.gmail.tarekmabdallah91.news.utils.Constants.ZERO;

public class ArticlesFragment extends BaseFragment {

    @BindView(R.id.articles_recycler_view)
    RecyclerView articlesRecyclerView;
    // TODO: 8/22/2019 to solve reloading fragments every time when be visible,
    //  and after rotation the view pager skip the 1st fragment (after the current position) not loaded
    private ArticleFragmentPresenter articleFragmentPresenter;
    private int position;
    private String sectionId;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_articles;
    }

    @Override
    public void initiateValues() {
        articleFragmentPresenter = ArticleFragmentPresenter.getInstance();
//        Log.d(SCROLL_POSITION + " F I", position + " " + sectionId);
        if(null != activity && position == ZERO) setUserVisibleHint(true); // to load only the first page in view pager, and load each pages one by one when it isVisible
    }

    @Override // to load only the first page in view pager, and load each pages one by one when it isVisible
    public void setUserVisibleHint(boolean isVisibleToUser) {
//        if (this.isVisible()){
//            if (isVisibleToUser) {
//                Log.d(SCROLL_POSITION + " Visible", position + " " + sectionId);
//                showCurrentFragment();
//            }
//           // else { /*do nothing*/ }
//        }
        if (isVisibleToUser) {
            // handler needed to wait sometime till the fragment be completely VisibleToUser
            // TO load pages when the user swipe taps (not when the user swipe between fragments)
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    Log.d(SCROLL_POSITION + " Visible", position + " " + sectionId);
                    showCurrentFragment();
                }
            }, ONE);
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void showCurrentFragment(){
        // MUST USED HERE
        if (null != sectionId) activity.getIntent().putExtra(SECTION_ID_KEYWORD, sectionId);
//        Log.d(SCROLL_POSITION + " F", position + " " + sectionId);
        articleFragmentPresenter.initiateValues(activity, errorLayout, progressBar, errorTV, errorIV, articlesRecyclerView);
    }

    @Override
    public void reSetActivityWithSaveInstanceState(Bundle savedInstanceState) {
//        articleFragmentPresenter.reSetActivityWithSaveInstanceState(savedInstanceState, articlesRecyclerView);
//        sectionId = activity.getIntent().getStringExtra(SECTION_ID_KEYWORD);
//        Log.d(SCROLL_POSITION + " Bundle", position + " " + sectionId);
        int scrollPosition = savedInstanceState.getInt(SCROLL_POSITION);
        articleFragmentPresenter.moveRecyclerViewToPosition(articlesRecyclerView, scrollPosition);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
//        articleFragmentPresenter = ArticleFragmentPresenter.getInstance();
//        articleFragmentPresenter.onSaveInstanceState(savedInstanceState, articlesRecyclerView);
        if (null != articleFragmentPresenter && null != articlesRecyclerView) {
            int scrollingPosition = articleFragmentPresenter.getRecyclerViewPosition(articlesRecyclerView);
            savedInstanceState.putInt(SCROLL_POSITION, scrollingPosition);
        }
        super.onSaveInstanceState(savedInstanceState);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public static ArticlesFragment getInstance(){
        return new ArticlesFragment();
    }

//    public void setSectionId(String sectionId) {
//        articleFragmentPresenter.setSectionId(sectionId);
//    }
//protected void setPagingViewModel(String searchKeyword){
//    makeViewVisible(progressBar);
//    ItemViewModel itemViewModel =  new ItemViewModel(activity, getSectionId(), searchKeyword, retrofit);
//    itemViewModel.getItemPagedList().observe(this, new Observer<PagedList<Article>>() {
//        @Override
//        public void onChanged(@Nullable PagedList<Article> items) {
//            itemAdapter.submitList(items);
//        }
//    });
//
//    itemViewModel.getNetworkState().observe(this, new Observer<NetworkState>() {
//        @Override
//        public void onChanged(@Nullable NetworkState networkState) {
//            observeNetworkState(networkState);
//        }
//    });
//}
//
//    private void setDbViewModel() {
//        // failed to inject observers
//        DbViewModel viewModel = ViewModelProviders.of(this).get(DbViewModel.class);
//        viewModel.getData().observe(this, new Observer<List<Article>>() {
//
//            @Override
//            public void onChanged(@Nullable List<Article> articlesInDb) {
//                if (null != articlesInDb && !articlesInDb.isEmpty())
//                    itemAdapter.swapList(articlesInDb);
//                else {
//                    observeNetworkState(new NetworkState(NetworkState.Status.FAILED,
//                            activity.getString(R.string.empty_db_msg)));
//                    makeViewGone(progressBar);
//                }
//            }
//        });
//    }
//
//    private void setItemAdapter(){
//        itemAdapter = new ItemAdapter();
//        itemAdapter.setOnArticleClickListener(new OnArticleClickListener() {
//            @Override
//            public void onClickArticle(Article article) {
//                openArticleWebViewActivity(activity, article);
//            }
//
//            @Override
//            public void onClickArticleSection(Article article) {
//                String sectionName = article.getSectionName();
//                String activityTitle = activity.getTitle().toString();
//                if (!activityTitle.equals(sectionName))
//                    openSectionActivity(activity, article.getSectionId(), article.getSectionName(), false);
//            }
//        });
//    }
//
//    private void setArticlesRecyclerView() {
//        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
//        articlesRecyclerView.setLayoutManager(layoutManager);
//        articlesRecyclerView.setHasFixedSize(true);
//        articlesRecyclerView.setAdapter(itemAdapter);
//    }
//
//    private void observeNetworkState(@Nullable NetworkState networkState){
//        itemAdapter.setNetworkState(networkState);
//        boolean hasExtraRows = itemAdapter.hasExtraRow();
//        showNormalProgressBar(progressBar, hasExtraRows);
//        String errorMsg = EMPTY_STRING;
//        if (null != networkState) errorMsg = networkState.getMsg();
//        // when the page size is larger than the total size in the Guardian API >> change it to equal 1 and restart the activity
//        final String HTTP_BAD_REQUEST = "HTTP 400 Bad Request";
//        if (HTTP_BAD_REQUEST.equals(errorMsg)){
//            if (PAGE_SIZE > ONE) PAGE_SIZE /= TWO ;
//            Log.d("PAGE_SIZE", PAGE_SIZE+"");
//            setPagingViewModel(null);
//        }
//        handelErrorMsg(networkState, errorLayout, progressBar, errorTV, errorIV);
//    }
    //    protected String getSectionId() {
////        return activity.getIntent().getStringExtra(SECTION_ID_KEYWORD);
//        return articleFragmentPresenter.getSectionId(position);
//    }

}