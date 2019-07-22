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

package com.gmail.tarekmabdallah91.news.views.articlesFragment.paging;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import retrofit2.Retrofit;

public class ItemDataSourceFactory  extends DataSource.Factory {

    private MutableLiveData<ItemDataSource> itemLiveDataSource ;
    private Activity activity;
    private String sectionId, searchKeyword;
    private Retrofit retrofit;

    ItemDataSourceFactory(Activity activity, String sectionId, String searchKeyword, Retrofit retrofit){
        this.activity = activity;
        this.sectionId = sectionId;
        this.searchKeyword = searchKeyword;
        this.retrofit = retrofit;
        this.itemLiveDataSource = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {
        ItemDataSource itemDataSource = new ItemDataSource(activity, sectionId, searchKeyword, retrofit);
        itemLiveDataSource.postValue(itemDataSource);
        return itemDataSource;
    }

    MutableLiveData<ItemDataSource> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}