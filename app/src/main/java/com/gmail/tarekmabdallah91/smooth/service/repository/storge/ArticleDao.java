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


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gmail.tarekmabdallah91.news.models.articles.Article;

import java.util.List;

/**
 * Data Access Object for the movies table.
 */
@Dao
public interface ArticleDao {

    /**
     * Get the Articles from the table.
     * -------------------------------
     * Since the DB use as caching, we don't return LiveData.
     * We don't need to get update every time the database update.
     * We using the get query when application start. So, we able to display
     * data fast and in case we don't have connection to work offline.
     * @return the movies from the table
     */
    @Query("SELECT * FROM favArticles")
    List<Article> getArticles();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertArticles(Article movie);

    @Query("DELETE FROM favArticles")
    abstract void deleteAllArticles();
}
