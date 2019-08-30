/*
 *
 * Copyright 2019 tarekmabdallah91@gmail.com
 *  inspired by https://github.com/EladBenDavid/TMDb
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

package com.gmail.tarekmabdallah91.smooth.service.repository.network.paging;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.gmail.tarekmabdallah91.news.models.articles.Article;

import rx.subjects.ReplaySubject;

/*
    Responsible for creating the DataSource so we can give it to the PagedList.
 */
public class DataSourceFactory extends DataSource.Factory {

    private MutableLiveData<ItemDataSource> networkStatus;
    private ItemDataSource itemDataSource;

    public DataSourceFactory(Activity activity) {
        this.networkStatus = new MutableLiveData<>();
        this.itemDataSource = new ItemDataSource(activity);
    }

    @Override
    public DataSource create() {
        networkStatus.postValue(itemDataSource);
        return itemDataSource;
    }

    public MutableLiveData<ItemDataSource> getNetworkStatus() {
        return networkStatus;
    }

    public ReplaySubject<Article> getArticles() {
        return itemDataSource.getArticles();
    }

}
