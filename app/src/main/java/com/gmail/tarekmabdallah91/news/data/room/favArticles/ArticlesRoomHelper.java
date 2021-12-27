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

package com.gmail.tarekmabdallah91.news.data.room.favArticles;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.gmail.tarekmabdallah91.news.data.room.NewsDb;
import com.gmail.tarekmabdallah91.news.models.articles.Article;

import java.util.List;

import static com.gmail.tarekmabdallah91.news.utils.Constants.ZERO;

public final class ArticlesRoomHelper {

    private NewsDb articlesDb;
    private static ArticlesRoomHelper articlesRoomHelper;

    private ArticlesRoomHelper(Context context) {
        articlesDb = NewsDb.getNewsDbInstance(context);
    }

    public static ArticlesRoomHelper getInstance(Context context) {
        if (null == articlesRoomHelper) articlesRoomHelper = new ArticlesRoomHelper(context);
        return articlesRoomHelper;
    }

    public void insertArticle(final Article article) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                articlesDb.articleDao().addArticle(article);
            }
        }).start();
    }

    public void getArticlesList (final RetrieveArticleData retrieveArticleData) {
        final Handler handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                retrieveArticleData.onComplete((List<Article>) msg.obj);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Article> articlesList = articlesDb.articleDao().getArticles();
                Message message = handler.obtainMessage(ZERO, articlesList);
                message.sendToTarget();
            }
        }).start();
    }

    public void deleteArticle(final Article article) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                articlesDb.articleDao().deleteArticleFromDb(article);
            }
        }).start();
    }

    public void deleteArticleById(final String id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                articlesDb.articleDao().deleteArticleById(id);
            }
        }).start();
    }

    public void clearDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                articlesDb.articleDao().clearFavArticlesDb();
            }
        }).start();
    }

}