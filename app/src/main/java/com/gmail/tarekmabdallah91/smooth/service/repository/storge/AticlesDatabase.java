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

package com.gmail.tarekmabdallah91.smooth.service.repository.storge;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.gmail.tarekmabdallah91.news.models.articles.Article;
import com.gmail.tarekmabdallah91.smooth.service.repository.storge.paging.DBArticlesDataSourceFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.gmail.tarekmabdallah91.news.utils.Constants.DATA_BASE_NAME;
import static com.gmail.tarekmabdallah91.news.utils.Constants.NUMBERS_OF_THREADS;

/**
 * The Room database that contains the table
 */
@Database(entities = {Article.class}, version = 1, exportSchema = false)
public abstract class AticlesDatabase extends RoomDatabase {

    private static AticlesDatabase instance;
    public abstract ArticleDao articleDao();
    private static final Object lock = new Object();
    private LiveData<PagedList<Article>> articlesPaged;

    public static AticlesDatabase getInstance(Context context) {
        synchronized (lock) {
            if (instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        AticlesDatabase.class, DATA_BASE_NAME)
                        .build();
                instance.init();

            }
            return instance;
        }
    }

    private void init() {
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                .setInitialLoadSizeHint(Integer.MAX_VALUE).setPageSize(Integer.MAX_VALUE).build();
        Executor executor = Executors.newFixedThreadPool(NUMBERS_OF_THREADS);
        DBArticlesDataSourceFactory dataSourceFactory = new DBArticlesDataSourceFactory(articleDao());
        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);
        articlesPaged = livePagedListBuilder.setFetchExecutor(executor).build();
    }

    public LiveData<PagedList<Article>> getArticlesPaged() {
        return articlesPaged;
    }
}
