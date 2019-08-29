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

package com.gmail.tarekmabdallah91.smooth.service.repository.network;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.gmail.tarekmabdallah91.news.models.articles.Article;
import com.gmail.tarekmabdallah91.news.utils.NetworkState;
import com.gmail.tarekmabdallah91.smooth.service.repository.network.paging.DataSourceFactory;
import com.gmail.tarekmabdallah91.smooth.service.repository.network.paging.ItemDataSource;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.gmail.tarekmabdallah91.news.utils.Constants.NUMBERS_OF_THREADS;
import static com.gmail.tarekmabdallah91.news.utils.Constants.PAGE_SIZE;


public class ArticlesNetwork {

    final private LiveData<PagedList<Article>> articlesPaged;
    final private LiveData<NetworkState> networkState;

    public ArticlesNetwork(DataSourceFactory dataSourceFactory, PagedList.BoundaryCallback<Article> boundaryCallback){
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(true)
                .setInitialLoadSizeHint(PAGE_SIZE).setPageSize(PAGE_SIZE).build();
        networkState = Transformations.switchMap(dataSourceFactory.getNetworkStatus(),
                new Function<ItemDataSource, LiveData<NetworkState>>() {
                    @Override
                    public LiveData<NetworkState> apply(ItemDataSource itemDataSource) {
                        return itemDataSource.getNetworkState();
                    }
                });
        Executor executor = Executors.newFixedThreadPool(NUMBERS_OF_THREADS);
        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);
        articlesPaged = livePagedListBuilder.
                setFetchExecutor(executor).
                setBoundaryCallback(boundaryCallback).
                build();

    }


    public LiveData<PagedList<Article>> getPagedArticles(){
        return articlesPaged;
    }



    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

}
