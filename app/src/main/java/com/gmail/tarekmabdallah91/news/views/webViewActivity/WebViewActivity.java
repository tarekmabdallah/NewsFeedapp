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

package com.gmail.tarekmabdallah91.news.views.webViewActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.models.articles.Article;
import com.gmail.tarekmabdallah91.news.views.bases.BaseActivityNoMenu;

import static com.gmail.tarekmabdallah91.news.utils.Constants.ARTICLES_KEYWORD;
import static com.gmail.tarekmabdallah91.news.utils.Constants.URL_KEYWORD;

public class WebViewActivity extends BaseActivityNoMenu {

    @Override
    protected String getActivityTitle() {
        return getString(R.string.article_details_label);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_web_view;
    }

    public static void openUrlOrHtmlInWebViewActivity(Context context, String urlOrHtml){
        Intent openWebViewActivityArticle =
                new Intent(context, WebViewActivity.class).putExtra(URL_KEYWORD, urlOrHtml);
        context.startActivity(openWebViewActivityArticle);
    }

    public static void openArticleWebViewActivity(Context context, Article article){
        Intent openWebViewActivityArticle =
                new Intent(context, WebViewActivity.class).putExtra(ARTICLES_KEYWORD, article);
        context.startActivity(openWebViewActivityArticle);
    }
}

