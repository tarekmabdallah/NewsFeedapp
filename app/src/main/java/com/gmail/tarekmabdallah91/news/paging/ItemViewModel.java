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

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.content.Context;

import com.gmail.tarekmabdallah91.news.models.articles.Article;

import static com.gmail.tarekmabdallah91.news.utils.Constants.PAGE_SIZE;

public class ItemViewModel extends ViewModel {

    private LiveData<PagedList<Article>> itemPagedList;
//    private LiveData<NetworkState> networkState;

    public ItemViewModel(Context context, String sectionId) {
        init(context, sectionId, null);
    }

    public ItemViewModel(Context context, String sectionId, String searchKeyword) {
        init(context, sectionId, searchKeyword);
    }

    private void init(Context context, String sectionId, String searchKeyword) {
       // Executor executor = Executors.newFixedThreadPool(5);
        ItemDataSourceFactory itemDataSourceFactory = new ItemDataSourceFactory(context, sectionId, searchKeyword);
//        Function function = new Function<ItemDataSource, LiveData<NetworkState>>() {
//            @Override
//            public LiveData<NetworkState> apply(ItemDataSource dataSource) {
//                return dataSource.getNetworkState();
//            }
//        };
//        networkState = Transformations.switchMap(itemDataSourceFactory.getItemLiveDataSource(), function);

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(true)
                        .setInitialLoadSizeHint(PAGE_SIZE)
                        .setPageSize(PAGE_SIZE).build();

        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, pagedListConfig))
                //.setFetchExecutor(executor)
                .build();
    }

    public LiveData<PagedList<Article>> getItemPagedList() {
        return itemPagedList;
    }

//    public LiveData<NetworkState> getNetworkState() {
//        return networkState;
//    }
}