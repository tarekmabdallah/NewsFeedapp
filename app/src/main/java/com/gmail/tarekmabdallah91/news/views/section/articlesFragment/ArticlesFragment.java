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
import com.gmail.tarekmabdallah91.news.data.room.news.DbViewModel;
import com.gmail.tarekmabdallah91.news.models.articles.Article;
import com.gmail.tarekmabdallah91.news.paging.ItemAdapter;
import com.gmail.tarekmabdallah91.news.paging.ItemViewModel;
import com.gmail.tarekmabdallah91.news.paging.OnArticleClickListener;
import com.gmail.tarekmabdallah91.news.views.bases.BaseDataLoaderFragment;

import java.util.List;

import butterknife.BindView;

import static com.gmail.tarekmabdallah91.news.utils.Constants.IS_FAVOURITE_LIST;
import static com.gmail.tarekmabdallah91.news.utils.Constants.SECTION_ID_KEYWORD;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.showFailureMsg;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.showProgressBar;
import static com.gmail.tarekmabdallah91.news.views.section.SectionActivity.openSectionActivity;
import static com.gmail.tarekmabdallah91.news.views.webViewActivity.WebViewActivity.openArticleWebViewActivity;


public class ArticlesFragment extends BaseDataLoaderFragment {

    @BindView(R.id.articles_recycler_view)
    RecyclerView articlesRecyclerView;

    protected ItemAdapter itemAdapter ;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_articles;
    }

    @Override
    protected void initiateValues() {
        super.initiateValues();
        setItemAdapter();
        setArticlesRecyclerView();
        if (IS_FAVOURITE_LIST.equals(getSectionId())) setDbViewModel();
        else setPagingViewModel(null);
    }

    @Override
    public String getSectionId() {
        return activity.getIntent().getStringExtra(SECTION_ID_KEYWORD);
    }

    protected void setPagingViewModel(String searchKeyword){
        ItemViewModel itemViewModel =  new ItemViewModel(activity, getSectionId(), searchKeyword);
        showProgressBar(progressBar, true);
        itemViewModel.getItemPagedList().observe(this, new Observer<PagedList<Article>>() {
            @Override
            public void onChanged(@Nullable PagedList<Article> items) {
                showToastMsg(activity.getString(R.string.loading_msg));
                itemAdapter.submitList(items);
                showProgressBar(progressBar, false);
            }
        });
    }

    private void setDbViewModel() {
        showProgressBar(progressBar,true);
        DbViewModel viewModel = ViewModelProviders.of(this).get(DbViewModel.class);
        viewModel.getData().observe(this, new Observer<List<Article>>() {

            @Override
            public void onChanged(@Nullable List<Article> articlesInDb) {
                if (null != articlesInDb && !articlesInDb.isEmpty()){
                    itemAdapter.swapList(articlesInDb);
                    showProgressBar(progressBar,false);
                }else {
                    showFailureMsg(new Throwable(activity.getString(R.string.empty_db_msg)),
                            android.R.drawable.ic_dialog_alert, errorLayout, progressBar, errorTV, errorIV);
                }
            }
        });
    }

    private void setItemAdapter(){
        itemAdapter = new ItemAdapter();
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
        itemAdapter.setOnArticleClickListener(onArticleClickListener);
    }

    private void setArticlesRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        articlesRecyclerView.setLayoutManager(layoutManager);
        articlesRecyclerView.setHasFixedSize(true);
        articlesRecyclerView.setAdapter(itemAdapter);
    }

    public static ArticlesFragment getInstance() {
        return new ArticlesFragment();
    }
}