/*
 * Copyright 2019 tarekmabdallah91@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.tarek.news.views.main;

import com.example.tarek.news.R;
import com.example.tarek.news.apis.APIClient;
import com.example.tarek.news.apis.APIServices;
import com.example.tarek.news.models.search.Article;
import com.example.tarek.news.models.search.ResponseSearchForKeyWord;
import com.example.tarek.news.views.bases.BaseActivity;

import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static com.example.tarek.news.apis.APIClient.getResponse;
import static com.example.tarek.news.utils.Constants.QUERY_Q_KEYWORD;
import static com.example.tarek.news.utils.ViewsUtils.getQueriesMap;
import static com.example.tarek.news.views.articles.ArticlesFragment.setArticlesFragmentToCommit;

public class MainActivity extends BaseActivity {


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }


    protected void callAPi() {
        APIServices apiServices = APIClient.getInstance(this).create(APIServices.class);
        Map<String, Object> queries = getQueriesMap();
        queries.put(QUERY_Q_KEYWORD, "egypt");
        Call<ResponseSearchForKeyWord> searchForKeyWord = apiServices.searchForKeyword(queries);
        getResponse(searchForKeyWord, this);
    }

    @Override
    protected void whenDataFetchedGetResponse(Object response) {

        if (response instanceof ResponseSearchForKeyWord) {
            ResponseSearchForKeyWord responseSearchForKeyWord = (ResponseSearchForKeyWord) response;
            List<Article> articleList = responseSearchForKeyWord.getResponse().getItems();
            if (articleList != null && !articleList.isEmpty()) {
                setArticlesFragmentToCommit(getSupportFragmentManager(), R.id.fragment_articles_container, articleList);
            } else handleNoDataFromResponse();
        }
    }
}
