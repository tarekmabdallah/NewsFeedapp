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

package com.gmail.tarekmabdallah91.news.paging;

import android.app.Activity;
import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.gmail.tarekmabdallah91.news.models.articles.Article;
import com.gmail.tarekmabdallah91.news.utils.NetworkState;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Retrofit;

import static com.gmail.tarekmabdallah91.news.utils.Constants.FIVE;
import static com.gmail.tarekmabdallah91.news.utils.Constants.PAGE_SIZE;
import static com.gmail.tarekmabdallah91.news.utils.Constants.TWO;

public class ItemViewModel extends ViewModel {

    private LiveData<PagedList<Article>> itemPagedList;
    private LiveData<NetworkState> networkState;

    public ItemViewModel(Activity activity, String sectionId, String searchKeyword, Retrofit retrofit) {
        init(activity, sectionId, searchKeyword, retrofit);
    }

    private void init(Activity activity, String sectionId, String searchKeyword, Retrofit retrofit) {
        Executor executor = Executors.newFixedThreadPool(FIVE);
        ItemDataSourceFactory itemDataSourceFactory = new ItemDataSourceFactory(activity, sectionId, searchKeyword, retrofit);
        Function functionNetworkState = new Function<ItemDataSource, MutableLiveData>() {
            @Override
            public MutableLiveData apply(ItemDataSource dataSource) {
                return dataSource.getNetworkState();
            }
        };
        networkState = Transformations.switchMap(itemDataSourceFactory.getItemLiveDataSource(), functionNetworkState);

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(true)
                        .setInitialLoadSizeHint(PAGE_SIZE/TWO)
                        .setPageSize(PAGE_SIZE).build();

        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, pagedListConfig))
                .setFetchExecutor(executor)
                .build();
    }

    public LiveData<PagedList<Article>> getItemPagedList() {
        return itemPagedList;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }
}