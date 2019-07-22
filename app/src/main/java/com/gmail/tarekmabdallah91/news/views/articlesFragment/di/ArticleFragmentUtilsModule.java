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

package com.gmail.tarekmabdallah91.news.views.articlesFragment.di;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.gmail.tarekmabdallah91.news.models.articles.Article;
import com.gmail.tarekmabdallah91.news.views.articlesFragment.paging.OnArticleClickListener;
import com.gmail.tarekmabdallah91.news.views.articlesFragment.paging.diAdapter.ItemAdapter;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.gmail.tarekmabdallah91.news.views.section.SectionActivity.openSectionActivity;
import static com.gmail.tarekmabdallah91.news.views.webViewActivity.WebViewActivity.openArticleWebViewActivity;

@Module
public class ArticleFragmentUtilsModule {

    private Activity activity;
//    private View errorLayout, progressBar, errorTV, errorIV;

    @Inject
    public ArticleFragmentUtilsModule(Activity activity) {
        this.activity = activity;
//        errorLayout = views[ZERO];
//        progressBar = views[ONE];
//        errorTV = views[TWO];
//        errorIV = views[THREE];
    }

    @Provides
    Context provideContext() {
        return activity;
    }

    @Provides
    @Singleton
    LinearLayoutManager provideLayoutManager() {
        return new LinearLayoutManager(activity);
    }

    @Provides
    @Singleton
    OnArticleClickListener provideOnArticleClickListener() {
        return new OnArticleClickListener() {

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
    }

    @Provides
    ItemAdapter itemAdapter() {
        return new ItemAdapter();
    }

//    @Provides
//    Observer<List<Article>> provideDbViewModelObserver(final ItemAdapter itemAdapter){
//        return new Observer<List<Article>>() {
//
//            @Override
//            public void onChanged(@Nullable List<Article> articlesInDb) {
//                if (null != articlesInDb && !articlesInDb.isEmpty())
//                    itemAdapter.swapList(articlesInDb);
//                else {
//                    observeNetworkState(itemAdapter, new NetworkState(NetworkState.Status.FAILED,
//                            activity.getString(R.string.empty_db_msg)));
//                   // makeViewGone(progressBar);
//                }
//            }
//        };
//    }
//
//    @Provides
//    Observer<PagedList<Article>> providePagedListObserver (final ItemAdapter itemAdapter){
//        return new Observer<PagedList<Article>>() {
//            @Override
//            public void onChanged(@Nullable PagedList<Article> items) {
//                itemAdapter.submitList(items);
//            }
//        };
//    }
//
//    @Provides
//    Observer<NetworkState> provideNetworkStateObserver (final ItemAdapter itemAdapter){
//        return new Observer<NetworkState>() {
//            @Override
//            public void onChanged(@Nullable NetworkState networkState) {
//                observeNetworkState(itemAdapter, networkState);
//            }
//        };
//    }
//
//    private void observeNetworkState(final ItemAdapter itemAdapter, @Nullable NetworkState networkState){
//        itemAdapter.setNetworkState(networkState);
//        boolean hasExtraRows = itemAdapter.hasExtraRow();
//        //showNormalProgressBar(progressBar, hasExtraRows);
//        String errorMsg = EMPTY_STRING;
//        if (null != networkState) errorMsg = networkState.getMsg();
//        // when the page size is larger than the total size in the Guardian API >> change it to equal 1 and restart the activity
//        final String HTTP_BAD_REQUEST = "HTTP 400 Bad Request";
//        if (HTTP_BAD_REQUEST.equals(errorMsg)){
//            PAGE_SIZE = ONE ;
//            restartActivity(activity);
//        }
//        //handelErrorMsg(networkState, errorLayout, progressBar, errorTV, errorIV);
//    }
}
