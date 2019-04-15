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

package com.example.tarek.news.views.section.articlesFragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.tarek.news.R;
import com.example.tarek.news.models.countryNews.ResponseCountryNews;
import com.example.tarek.news.models.section.ResponseSection;
import com.example.tarek.news.models.section.articles.Article;
import com.example.tarek.news.views.bases.BaseDataLoaderFragment;
import com.example.tarek.news.views.section.articlesFragment.adapter.ArticleAdapter;
import com.example.tarek.news.views.section.articlesFragment.adapter.OnArticleClickListener;

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;

import static com.example.tarek.news.utils.Constants.IS_COUNTRY_SECTION;
import static com.example.tarek.news.utils.Constants.SECTION_ID_KEYWORD;
import static com.example.tarek.news.views.section.SectionActivity.openSectionActivity;
import static com.example.tarek.news.views.webViewActivity.WebViewActivity.openArticleHtmlInWebViewActivity;


public class ArticlesFragment extends BaseDataLoaderFragment {

    @BindView(R.id.articles_recycler_view)
    RecyclerView articlesRecyclerView;

    protected ArticleAdapter adapter  ;
    protected List<Article> articles ;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_articles;
    }

    @Override
    protected void initiateValues() {
        super.initiateValues();
        setArticlesRecyclerView();
    }

    @Override
    protected int getAdapterCount() {
        return adapter.getItemCount();
    }

    @Override
    protected Call getCall() {
        boolean isCountrySection = activity.getIntent().getBooleanExtra(IS_COUNTRY_SECTION, false);
        if (isCountrySection) return apiServices.getCountrySection(getSectionId(), queries);
        return super.getCall();
    }

    @Override
    public String getSectionId() {
        return activity.getIntent().getStringExtra(SECTION_ID_KEYWORD);
    }

    private void setArticlesRecyclerView() {
        OnArticleClickListener onArticleClickListener = new OnArticleClickListener() {

            @Override
            public void onClickArticle(int position) {
                Article article = articles.get(position);
               // openArticleUrlInWebViewActivity(activity, article.getWebUrl());
                openArticleHtmlInWebViewActivity(activity, article.getFields().getBody());
            }

            @Override
            public void onClickArticleSection(int position) {
                Article article = articles.get(position);
                String sectionName = article.getSectionName();
                String activityTitle = activity.getTitle().toString();
                if (!activityTitle.equals(sectionName))
                    openSectionActivity(activity, article.getSectionId(), article.getSectionName(), false);
            }
        };
        adapter = new ArticleAdapter(activity);
        adapter.setOnArticleClickListener(onArticleClickListener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        articlesRecyclerView.setLayoutManager(layoutManager);
        articlesRecyclerView.setHasFixedSize(true);
        articlesRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void whenDataFetchedGetResponse(Object response) {
        if (response instanceof ResponseSection) {
            ResponseSection section = (ResponseSection) response;
            articles = section.getResponse().getResults();
        }else if (response instanceof ResponseCountryNews){
            ResponseCountryNews section = (ResponseCountryNews) response;
            articles = section.getResponse().getResults();
        }
        if (articles != null && !articles.isEmpty()) {
            adapter.swapList(articles);
        } else handleNoDataFromResponse();
    }

    public static ArticlesFragment getInstance() {
        return new ArticlesFragment();
    }
}
