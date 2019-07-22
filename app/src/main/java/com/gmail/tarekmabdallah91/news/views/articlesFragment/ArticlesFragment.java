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

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.data.room.news.DbViewModel;
import com.gmail.tarekmabdallah91.news.models.articles.Article;
import com.gmail.tarekmabdallah91.news.utils.NetworkState;
import com.gmail.tarekmabdallah91.news.views.articlesFragment.di.ArticleFragmentComponent;
import com.gmail.tarekmabdallah91.news.views.articlesFragment.di.ArticleFragmentUtilsModule;
import com.gmail.tarekmabdallah91.news.views.articlesFragment.di.DaggerArticleFragmentComponent;
import com.gmail.tarekmabdallah91.news.views.articlesFragment.di.RetrofitModule;
import com.gmail.tarekmabdallah91.news.views.articlesFragment.paging.ItemViewModel;
import com.gmail.tarekmabdallah91.news.views.articlesFragment.paging.OnArticleClickListener;
import com.gmail.tarekmabdallah91.news.views.articlesFragment.paging.diAdapter.ItemAdapter;
import com.gmail.tarekmabdallah91.news.views.bases.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import retrofit2.Retrofit;

import static com.gmail.tarekmabdallah91.news.utils.Constants.EMPTY_STRING;
import static com.gmail.tarekmabdallah91.news.utils.Constants.IS_FAVOURITE_LIST;
import static com.gmail.tarekmabdallah91.news.utils.Constants.ONE;
import static com.gmail.tarekmabdallah91.news.utils.Constants.PAGE_SIZE;
import static com.gmail.tarekmabdallah91.news.utils.Constants.SECTION_ID_KEYWORD;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.handelErrorMsg;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.makeViewGone;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.makeViewVisible;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.restartActivity;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.showNormalProgressBar;


public class ArticlesFragment extends BaseFragment {

    @BindView(R.id.articles_recycler_view)
    RecyclerView articlesRecyclerView;

    @Inject
    Retrofit retrofit;
    @Inject
    protected ItemAdapter itemAdapter;
    @Inject LinearLayoutManager layoutManager;
    @Inject
    OnArticleClickListener onArticleClickListener;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_articles;
    }

    @Override
    public void setFragmentComponent() {
        ArticleFragmentComponent articleFragmentComponent = DaggerArticleFragmentComponent.builder()
                .retrofitModule(new RetrofitModule())
                .articleFragmentUtilsModule(new ArticleFragmentUtilsModule(activity))
                .build();
        articleFragmentComponent.inject(this);
    }

    @Override
    protected void initiateValues() {
        setItemAdapter();
        setArticlesRecyclerView();
        if (IS_FAVOURITE_LIST.equals(getSectionId())) setDbViewModel();
        else setPagingViewModel(null);
    }

    protected String getSectionId() {
        return activity.getIntent().getStringExtra(SECTION_ID_KEYWORD);
    }

    Observer<PagedList<Article>> pagedListObserver;
    protected void setPagingViewModel(String searchKeyword){
        makeViewVisible(progressBar);
        ItemViewModel itemViewModel = new ItemViewModel(activity, getSectionId(), searchKeyword, retrofit);
        pagedListObserver = new Observer<PagedList<Article>>() {
            @Override
            public void onChanged(@Nullable PagedList<Article> items) {
                itemAdapter.submitList(items);
            }
        };
        itemViewModel.getItemPagedList().observe(this, pagedListObserver);
        itemViewModel.getNetworkState().observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(@Nullable NetworkState networkState) {
                observeNetworkState(networkState);
            }
        });
    }

    private void setDbViewModel() {
        // failed to inject observers
        DbViewModel viewModel = ViewModelProviders.of(this).get(DbViewModel.class);
        viewModel.getData().observe(this, new Observer<List<Article>>() {

            @Override
            public void onChanged(@Nullable List<Article> articlesInDb) {
                if (null != articlesInDb && !articlesInDb.isEmpty())
                    itemAdapter.swapList(articlesInDb);
                else {
                    observeNetworkState(new NetworkState(NetworkState.Status.FAILED,
                            activity.getString(R.string.empty_db_msg)));
                    makeViewGone(progressBar);
                }
            }
        });
    }

    private void setItemAdapter(){
        itemAdapter.setOnArticleClickListener(onArticleClickListener);
        itemAdapter.setItemClickListener(this);
    }

    private void setArticlesRecyclerView() {
        articlesRecyclerView.setLayoutManager(layoutManager);
        articlesRecyclerView.setHasFixedSize(true);
        articlesRecyclerView.setAdapter(itemAdapter);
    }

    private void observeNetworkState(@Nullable NetworkState networkState){
        itemAdapter.setNetworkState(networkState);
        boolean hasExtraRows = itemAdapter.hasExtraRow();
        showNormalProgressBar(progressBar, hasExtraRows);
        String errorMsg = EMPTY_STRING;
        if (null != networkState) errorMsg = networkState.getMsg();
        // when the page size is larger than the total size in the Guardian API >> change it to equal 1 and restart the activity
        final String HTTP_BAD_REQUEST = "HTTP 400 Bad Request";
        if (HTTP_BAD_REQUEST.equals(errorMsg)){
            PAGE_SIZE = ONE ;
            restartActivity(activity);
        }
        handelErrorMsg(networkState, errorLayout, progressBar, errorTV, errorIV);
    }
}