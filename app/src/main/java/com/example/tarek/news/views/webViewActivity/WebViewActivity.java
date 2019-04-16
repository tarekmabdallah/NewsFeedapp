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

package com.example.tarek.news.views.webViewActivity;

import android.content.Context;
import android.content.Intent;

import com.example.tarek.news.R;
import com.example.tarek.news.views.bases.BaseActivityNoMenu;

import static com.example.tarek.news.utils.Constants.ARTICLE_HTML_KEYWORD;
import static com.example.tarek.news.utils.Constants.URL_KEYWORD;

public class WebViewActivity extends BaseActivityNoMenu {

    @Override
    protected String getActivityTitle() {
        return getString(R.string.article_details_label);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web_view;
    }

    public static void openArticleUrlInWebViewActivity(Context context, String articleUrl){
        Intent openWebViewActivityArticle =
                new Intent(context, WebViewActivity.class).putExtra(URL_KEYWORD, articleUrl);
        context.startActivity(openWebViewActivityArticle);
    }

    public static void openArticleHtmlInWebViewActivity(Context context, String articleHtml){
        Intent openWebViewActivityArticle =
                new Intent(context, WebViewActivity.class).putExtra(ARTICLE_HTML_KEYWORD, articleHtml);
        context.startActivity(openWebViewActivityArticle);
    }
}

