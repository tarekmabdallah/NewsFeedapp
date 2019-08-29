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

import android.arch.paging.DataSource;

import com.gmail.tarekmabdallah91.smooth.service.repository.storge.ArticleDao;


public class DBArticlesDataSourceFactory extends DataSource.Factory {

    private DBArticlesPageKeyedDataSource articlesPageKeyedDataSource;
    public DBArticlesDataSourceFactory(ArticleDao dao) {
        articlesPageKeyedDataSource = new DBArticlesPageKeyedDataSource(dao);
    }

    @Override
    public DataSource create() {
        return articlesPageKeyedDataSource;
    }

}
