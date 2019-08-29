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

package com.gmail.tarekmabdallah91.news.data.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.gmail.tarekmabdallah91.news.data.room.favArticles.ArticleDao;
import com.gmail.tarekmabdallah91.news.data.room.newsDb.NewsDao;
import com.gmail.tarekmabdallah91.news.models.articles.Article;
import com.gmail.tarekmabdallah91.news.models.newsDbPages.NewsDbPage;

@Database(entities = {Article.class, NewsDbPage.class}, version = 1, exportSchema = false)
public abstract class NewsDb extends RoomDatabase {

    private static NewsDb newsDb;
    private final static String DATABASE_NAME = "mainNewsDb";

    public static NewsDb getNewsDbInstance(Context context) {
        if (newsDb == null) newsDb = Room.databaseBuilder(context, NewsDb.class, DATABASE_NAME).build();
        return newsDb;
    }

    public abstract NewsDao newsDao();
    public abstract ArticleDao articleDao();
}
