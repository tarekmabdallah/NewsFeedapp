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

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.paging.PagedList;
import android.support.annotation.Nullable;

import com.gmail.tarekmabdallah91.news.models.articles.Article;
import com.gmail.tarekmabdallah91.news.utils.NetworkState;
import com.gmail.tarekmabdallah91.smooth.service.repository.network.PagedListSetter;
import com.gmail.tarekmabdallah91.smooth.service.repository.network.paging.DataSourceFactory;
import com.gmail.tarekmabdallah91.smooth.service.repository.storge.AticlesDatabase;

import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.getSectionIdOrCountrySection;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.printLog;

/**
 * Repository
 */

public class ArticleRepository {

    private static ArticleRepository instance;
    private final PagedListSetter network;
    private final AticlesDatabase database;
    private final MediatorLiveData liveDataMerger;
    private String sectionId;
    private Activity activity;


    private ArticleRepository(Activity activity) {
        this.activity = activity;
        DataSourceFactory dataSourceFactory = new DataSourceFactory(activity);

        PagedList.BoundaryCallback<Article> boundaryCallback = new PagedList.BoundaryCallback<Article>() {
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
        network = new PagedListSetter(dataSourceFactory, boundaryCallback);
        database = AticlesDatabase.getInstance(activity.getApplicationContext());
        // when we get new articles from net we set them into the database
        liveDataMerger = new MediatorLiveData<>();
        liveDataMerger.addSource(network.getPagedArticles(), new Observer() {
            @Override
            public void onChanged(@Nullable Object value) {
                liveDataMerger.setValue(value);
                if (value != null) printLog(value.toString());
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

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public Activity getActivity() {
        return activity;
    }

    public static ArticleRepository getInstance(Activity activity){
        if(instance == null) instance = new ArticleRepository(activity);
        else { // to reset process when sectionId is changed which means that the user is showing that new section so load it
            String sectionId = getSectionIdOrCountrySection (activity.getIntent());
            if (null == instance.getSectionId() || !instance.getSectionId().equals(sectionId)){
                instance = null;
                instance = new ArticleRepository(activity);
                instance.setSectionId(sectionId);
            }
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
