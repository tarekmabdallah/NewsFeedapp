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

package com.gmail.tarekmabdallah91.news.data.room.news;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.gmail.tarekmabdallah91.news.models.articles.Article;

@Database(entities = {Article.class}, version = 1, exportSchema = false)
abstract class ArticlesDb extends RoomDatabase {


    private static ArticlesDb articlesDb;
    private final static String DATABASE_NAME = "news";

    static ArticlesDb getCartDbInstance(Context context) {
        if (articlesDb == null) articlesDb = Room.databaseBuilder(context, ArticlesDb.class, DATABASE_NAME).build();
        return articlesDb;
    }

    abstract ArticleDao articleDao();

}
