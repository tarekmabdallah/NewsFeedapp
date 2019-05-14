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

package com.gmail.tarekmabdallah91.news.paging;

import android.arch.paging.PageKeyedDataSource;
import android.content.Context;
import android.support.annotation.NonNull;

import com.gmail.tarekmabdallah91.news.apis.APIClient;
import com.gmail.tarekmabdallah91.news.apis.APIServices;
import com.gmail.tarekmabdallah91.news.apis.DataFetcherCallback;
import com.gmail.tarekmabdallah91.news.models.articles.Article;
import com.gmail.tarekmabdallah91.news.models.section.ResponseSection;

import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static com.gmail.tarekmabdallah91.news.apis.APIClient.getResponse;
import static com.gmail.tarekmabdallah91.news.utils.Constants.ONE;
import static com.gmail.tarekmabdallah91.news.utils.Constants.QUERY_Q_KEYWORD;
import static com.gmail.tarekmabdallah91.news.utils.Constants.TWO;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.getQueriesMap;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.showShortToastMsg;

public class ItemDataSource extends PageKeyedDataSource<Integer, Article> {

    private Context context;
    private String sectionId, searchKeyword;

    ItemDataSource(Context context, String sectionId, String searchKeyword){
        this.context = context;
        this.sectionId = sectionId;
        this.searchKeyword = searchKeyword;
    }

    //this will be called once to load the initial data
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Article> callback) {
        DataFetcherCallback dataFetcherCallback = new DataFetcherCallback() {
            @Override
            public void onDataFetched(Object body) {
                ResponseSection responseSection = (ResponseSection) body;
                List<Article> articles = responseSection.getResponse().getResults();
                callback.onResult(articles, null , TWO );
            }

            @Override
            public void onFailure(Throwable t, int errorImageResId) {
                showShortToastMsg(context, t.getMessage());
            }
        };
        getResponse(getCall(ONE), dataFetcherCallback);
    }

    //this will load the previous page
    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Article> callback) {
        DataFetcherCallback dataFetcherCallback = new DataFetcherCallback() {
            @Override
            public void onDataFetched(Object body) {
                ResponseSection responseSection = (ResponseSection) body;
                List<Article> articles = responseSection.getResponse().getResults();
                Integer adjacentKey = (params.key > ONE) ? params.key - ONE : null;
                callback.onResult(articles, adjacentKey );
            }

            @Override
            public void onFailure(Throwable t, int errorImageResId) {
                showShortToastMsg(context, t.getMessage());
            }
        };
        getResponse(getCall(params.key), dataFetcherCallback);
    }

    //this will load the next page
    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Article> callback) {
        DataFetcherCallback dataFetcherCallback = new DataFetcherCallback() {
            @Override
            public void onDataFetched(Object body) {
                ResponseSection responseSection = (ResponseSection) body;
                if (responseSection != null) {
                    Integer key = responseSection.getResponse().getPages() > params.key ? params.key + ONE : null;
                    List<Article> articles = responseSection.getResponse().getResults();
                    callback.onResult(articles, key);
                }
            }

            @Override
            public void onFailure(Throwable t, int errorImageResId) {
                showShortToastMsg(context, t.getMessage());
            }
        };
        getResponse(getCall(params.key), dataFetcherCallback);
    }

    private Call<ResponseSection> getCall (int pageNumber){
        APIServices apiServices = APIClient.getAPIServices(context);
        Map<String, Object> queries = getQueriesMap(context, pageNumber);
        if (null != searchKeyword) queries.put(QUERY_Q_KEYWORD, searchKeyword);
        return apiServices.getArticlesBySection(sectionId, queries);
    }
}