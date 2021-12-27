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

package com.gmail.tarekmabdallah91.news.data.room.newsDb;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.gmail.tarekmabdallah91.news.data.room.NewsDb;
import com.gmail.tarekmabdallah91.news.models.newsDbPages.NewsDbPage;

import java.util.List;

import static com.gmail.tarekmabdallah91.news.utils.Constants.ZERO;

public final class NewsRoomHelper {

    private NewsDb newsDb;
    private static NewsRoomHelper newsRoomHelper;

    private NewsRoomHelper(Context context) {
        newsDb = NewsDb.getNewsDbInstance(context);
    }

    public static NewsRoomHelper getInstance(Context context) {
        if (null == newsRoomHelper) newsRoomHelper = new NewsRoomHelper(context);
        return newsRoomHelper;
    }

    public void insertNewsDbPage(final NewsDbPage page) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                newsDb.newsDao().addPage(page);
            }
        }).start();
    }

    public void getNewsDbPagesList (final RetrieveNewsData retrieveNewsData) {
        final Handler handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                retrieveNewsData.onComplete((List<NewsDbPage>) msg.obj);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<NewsDbPage> pagesList = newsDb.newsDao().getPages();
                Message message = handler.obtainMessage(ZERO, pagesList);
                message.sendToTarget();
            }
        }).start();
    }

    public void deleteNewsDbPage(final NewsDbPage page) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                newsDb.newsDao().deletePageFromDb(page);
            }
        }).start();
    }

    public void deleteNewsDbPageById(final String id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                newsDb.newsDao().deletePageById();//id
            }
        }).start();
    }

    public void clearDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                newsDb.newsDao().clearNewsDb();
            }
        }).start();
    }

}