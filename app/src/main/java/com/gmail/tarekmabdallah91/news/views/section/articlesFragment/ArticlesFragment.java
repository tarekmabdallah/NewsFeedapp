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

package com.gmail.tarekmabdallah91.news.views.section.articlesFragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.data.room.news.ArticlesViewModel;
import com.gmail.tarekmabdallah91.news.models.articles.Article;
import com.gmail.tarekmabdallah91.news.paging.ItemAdapter;
import com.gmail.tarekmabdallah91.news.paging.ItemViewModel;
import com.gmail.tarekmabdallah91.news.views.bases.BaseDataLoaderFragment;
import com.gmail.tarekmabdallah91.news.views.section.articlesFragment.adapter.ArticleAdapter;
import com.gmail.tarekmabdallah91.news.views.section.articlesFragment.adapter.OnArticleClickListener;

import java.util.List;

import butterknife.BindView;

import static com.gmail.tarekmabdallah91.news.utils.Constants.IS_FAVOURITE_LIST;
import static com.gmail.tarekmabdallah91.news.utils.Constants.SECTION_ID_KEYWORD;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.showProgressBar;
import static com.gmail.tarekmabdallah91.news.views.section.SectionActivity.openSectionActivity;
import static com.gmail.tarekmabdallah91.news.views.webViewActivity.WebViewActivity.openArticleWebViewActivity;


public class ArticlesFragment extends BaseDataLoaderFragment {

    @BindView(R.id.articles_recycler_view)
    RecyclerView articlesRecyclerView;

    protected ArticleAdapter articleAdapter;
    protected ItemAdapter itemAdapter ;
    protected List<Article> articles ;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_articles;
    }

    @Override
    protected void initiateValues() {
        super.initiateValues();
        setArticlesRecyclerView();
        OnArticleClickListener onArticleClickListener = new OnArticleClickListener() {

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
        };
        if (IS_FAVOURITE_LIST.equals(getSectionId())) {
            articleAdapter = new ArticleAdapter();
            articleAdapter.setOnArticleClickListener(onArticleClickListener);
            articlesRecyclerView.setAdapter(articleAdapter);
            setViewModel();
        } else {
            itemAdapter = new ItemAdapter();
            itemAdapter.setOnArticleClickListener(onArticleClickListener);
            articlesRecyclerView.setAdapter(itemAdapter);
            setPagingViewModel(null);
        }
    }

    @Override
    public String getSectionId() {
        return activity.getIntent().getStringExtra(SECTION_ID_KEYWORD);
    }

    private void setArticlesRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        articlesRecyclerView.setLayoutManager(layoutManager);
        articlesRecyclerView.setHasFixedSize(true);
    }

    protected void setPagingViewModel(String searchKeyword){
        ItemViewModel itemViewModel =  new ItemViewModel(activity, getSectionId(), searchKeyword);
        showProgressBar(progressBar, true);
        itemViewModel.getItemPagedList().observe(this, new Observer<PagedList<Article>>() {
            @Override
            public void onChanged(@Nullable PagedList<Article> items) {
                showToastMsg("loading...");
                itemAdapter.submitList(items);
                showProgressBar(progressBar, false);
            }
        });
    }

    private void setViewModel() {
        showProgressBar(progressBar,true);
        ArticlesViewModel viewModel = ViewModelProviders.of(this).get(ArticlesViewModel.class);
        viewModel.getData().observe(this, new Observer<List<Article>>() {

            @Override
            public void onChanged(@Nullable List<Article> articlesInDb) {
                articles = articlesInDb;
                articleAdapter.swapList(articlesInDb);
                showProgressBar(progressBar,false);
            }
        });
    }

    public static ArticlesFragment getInstance() {
        return new ArticlesFragment();
    }
}