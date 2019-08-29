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

package com.gmail.tarekmabdallah91.smooth.ui.view;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.models.articles.Article;
import com.gmail.tarekmabdallah91.news.paging.ItemAdapter;
import com.gmail.tarekmabdallah91.news.paging.OnArticleClickListener;
import com.gmail.tarekmabdallah91.news.utils.NetworkState;
import com.gmail.tarekmabdallah91.news.views.bases.BaseFragment;
import com.gmail.tarekmabdallah91.smooth.ui.listeners.ItemClickListener;
import com.gmail.tarekmabdallah91.smooth.ui.viewmodel.ArticleDetailsViewModel;
import com.gmail.tarekmabdallah91.smooth.ui.viewmodel.ArticlesListViewModel;

import butterknife.BindView;

import static com.gmail.tarekmabdallah91.news.utils.Constants.SECTION_ID_KEYWORD;

public class ArticlesListFragment extends BaseFragment implements ItemClickListener {

    private ArticleDetailsViewModel detailsViewModel;
    private FragmentActivity activity;

    @BindView(R.id.articles_recycler_view)
    protected RecyclerView recyclerView;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_articles;
    }

    @Override
    public void initiateValuesOnCreateView(Bundle savedInstanceState) {
        setRecyclerView();
        observersRegisters();
    }

    private String getSectionId () {
        return activity.getIntent().getStringExtra(SECTION_ID_KEYWORD);
    }

    public void setRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void observersRegisters() {
        String sectionId = getSectionId();
        ArticlesListViewModel viewModel = new ArticlesListViewModel(activity.getApplication(), sectionId);

        final ItemAdapter pageListAdapter = new ItemAdapter();
        pageListAdapter.setOnArticleClickListener(new OnArticleClickListener() {
            @Override
            public void onClickArticle(Article article) {
                OnItemClick(article);
            }

            @Override
            public void onClickArticleSection(Article article) {

            }
        });
        viewModel.getArticles().observe(this, new Observer<PagedList<Article>>() {
            @Override
            public void onChanged(@Nullable PagedList<Article> pagedList) {
                pageListAdapter.submitList(pagedList);
            }
        });
        viewModel.getNetworkState().observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(@Nullable NetworkState networkState) {
                pageListAdapter.setNetworkState(networkState);
            }
        });

        recyclerView.setAdapter(pageListAdapter);
        detailsViewModel = ViewModelProviders.of(activity).get(ArticleDetailsViewModel.class);
    }

    @Override
    public void OnItemClick(Article article) {
        detailsViewModel.getArticle().postValue(article);
        if (!detailsViewModel.getArticle().hasActiveObservers()) {
            // Create fragment and give it an argument specifying the article it should show
            ArticleDetailsFragment detailsFragment = new ArticleDetailsFragment();
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragmentsContainer, detailsFragment);
            transaction.addToBackStack(null);
            // Commit the transaction
            transaction.commit();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (FragmentActivity) activity;
    }
}
