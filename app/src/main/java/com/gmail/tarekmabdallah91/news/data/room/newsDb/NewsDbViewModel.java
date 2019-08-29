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

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.gmail.tarekmabdallah91.news.data.room.NewsDb;
import com.gmail.tarekmabdallah91.news.models.newsDbPages.NewsDbPage;

import java.util.List;

final public class NewsDbViewModel extends AndroidViewModel {

    private final LiveData<List<NewsDbPage>> data;

    public NewsDbViewModel(Application application) {
        super(application);
        NewsDb database = NewsDb.getNewsDbInstance(this.getApplication());
        data = database.newsDao().getPagesList();
    }

    public LiveData<List<NewsDbPage>> getData() {
        return data;
    }
}