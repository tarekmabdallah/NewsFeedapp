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

package com.gmail.tarekmabdallah91.smooth.service.repository.storge.paging;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.gmail.tarekmabdallah91.news.models.articles.Article;
import com.gmail.tarekmabdallah91.smooth.service.repository.storge.ArticleDao;

import java.util.List;

import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.printLog;

public class DBArticlesPageKeyedDataSource extends PageKeyedDataSource<String, Article> {

    private final ArticleDao articleDao;
    DBArticlesPageKeyedDataSource(ArticleDao dao) {
        articleDao = dao;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull final LoadInitialCallback<String, Article> callback) {
        printLog( "Loading Initial Rang, Count " + params.requestedLoadSize);
        List<Article> articles = articleDao.getArticles();
        if(articles.size() != 0) {
            callback.onResult(articles, "0", "1");
        }
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, final @NonNull LoadCallback<String, Article> callback) {
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Article> callback) {
    }
}
