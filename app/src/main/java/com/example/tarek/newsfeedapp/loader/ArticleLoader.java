/*
Copyright 2018 tarekmabdallah91@gmail.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.example.tarek.newsfeedapp.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.tarek.newsfeedapp.article.Article;
import com.example.tarek.newsfeedapp.utils.ArticleQueryUtils;

import java.util.List;

public class ArticleLoader extends AsyncTaskLoader<List<Article>> {

    // --Commented out by Inspection (26/04/2018 08:52 ص):private static final String TAG = ArticleLoader.class.getSimpleName();
    private final String url;


    public ArticleLoader(Context context , String url ) {
        super(context);
        this.url = url;
    }

    @Override
    public List<Article> loadInBackground() {
        return ArticleQueryUtils.fetchArticlesData(url);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

}
