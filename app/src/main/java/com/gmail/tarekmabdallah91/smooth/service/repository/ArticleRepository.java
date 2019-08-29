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

package com.gmail.tarekmabdallah91.smooth.service.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.paging.PagedList;
import android.content.Context;
import android.support.annotation.Nullable;

import com.gmail.tarekmabdallah91.news.models.articles.Article;
import com.gmail.tarekmabdallah91.news.utils.NetworkState;
import com.gmail.tarekmabdallah91.smooth.service.repository.network.ArticlesNetwork;
import com.gmail.tarekmabdallah91.smooth.service.repository.network.paging.DataSourceFactory;
import com.gmail.tarekmabdallah91.smooth.service.repository.storge.AticlesDatabase;

import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.printLog;

public class ArticleRepository {
    private static ArticleRepository instance;
    final private ArticlesNetwork network;
    final private AticlesDatabase database;
    final private MediatorLiveData liveDataMerger;

    private ArticleRepository(Context context, String sectionId) {

        DataSourceFactory dataSourceFactory = new DataSourceFactory(context, sectionId);

        network = new ArticlesNetwork(dataSourceFactory, boundaryCallback);
        database = AticlesDatabase.getInstance(context.getApplicationContext());
        // when we get new articles from net we set them into the database
        liveDataMerger = new MediatorLiveData<>();
        liveDataMerger.addSource(network.getPagedArticles(), new Observer() {
            @Override
            public void onChanged(@Nullable Object value) {
                liveDataMerger.setValue(value);
                printLog(value.toString());
            }
        });

        // save the articles into db
        dataSourceFactory.getArticles().
                observeOn(Schedulers.io()).
                subscribe(new Action1<Article>() {
                    @Override
                    public void call(Article article) {
                        database.articleDao().insertArticles(article);
                    }
                });

    }

    private PagedList.BoundaryCallback<Article> boundaryCallback = new PagedList.BoundaryCallback<Article>() {
        @Override
        public void onZeroItemsLoaded() {
            super.onZeroItemsLoaded();
            liveDataMerger.addSource(database.getArticlesPaged(), new Observer() {
                @Override
                public void onChanged(@Nullable Object value) {
                    liveDataMerger.setValue(value);
                    liveDataMerger.removeSource(database.getArticlesPaged());
                }
            });
        }
    };
    public static ArticleRepository getInstance(Context context, String sectionId){
        if(instance == null){
            instance = new ArticleRepository(context, sectionId);
        }
        return instance;
    }

    public LiveData<PagedList<Article>> getArticles(){
        return  liveDataMerger;
    }

    public LiveData<NetworkState> getNetworkState() {
        return network.getNetworkState();
    }
}
