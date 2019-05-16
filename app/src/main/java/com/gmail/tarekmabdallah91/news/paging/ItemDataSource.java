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

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.apis.APIClient;
import com.gmail.tarekmabdallah91.news.apis.APIServices;
import com.gmail.tarekmabdallah91.news.apis.DataFetcherCallback;
import com.gmail.tarekmabdallah91.news.models.articles.Article;
import com.gmail.tarekmabdallah91.news.models.countryNews.ResponseCountryNews;
import com.gmail.tarekmabdallah91.news.models.section.ResponseSection;
import com.gmail.tarekmabdallah91.news.utils.NetworkState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static com.gmail.tarekmabdallah91.news.apis.APIClient.getResponse;
import static com.gmail.tarekmabdallah91.news.utils.Constants.IS_COUNTRY_SECTION;
import static com.gmail.tarekmabdallah91.news.utils.Constants.ONE;
import static com.gmail.tarekmabdallah91.news.utils.Constants.QUERY_Q_KEYWORD;
import static com.gmail.tarekmabdallah91.news.utils.Constants.TWO;
import static com.gmail.tarekmabdallah91.news.utils.Constants.ZERO;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.getQueriesMap;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.isConnected;

public class ItemDataSource extends PageKeyedDataSource<Integer, Article> {

    private Activity activity;
    private String sectionId, searchKeyword;
    private MutableLiveData<NetworkState> networkState;
    private boolean isCountrySection;
    private Throwable noConnectionThrowable;

    ItemDataSource(Activity activity, String sectionId, String searchKeyword){
        this.activity = activity;
        this.sectionId = sectionId;
        this.searchKeyword = searchKeyword;
        isCountrySection = activity.getIntent().getBooleanExtra(IS_COUNTRY_SECTION,false);
        networkState = new MutableLiveData<>();
        noConnectionThrowable = new Throwable(activity.getString(R.string.no_connection));
    }

    //this will be called once to load the initial data
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Article> callback) {
        networkState.postValue(NetworkState.LOADING);
        DataFetcherCallback dataFetcherCallback = new DataFetcherCallback() {
            @Override
            public void onDataFetched(Object body) {
                List<Article> articles = new ArrayList<>();
                if (body instanceof ResponseSection){
                    ResponseSection responseSection = (ResponseSection) body;
                    articles = responseSection.getResponse().getResults();
                }else if (body instanceof ResponseCountryNews){
                    ResponseCountryNews responseCountryNews = (ResponseCountryNews) body;
                    articles = responseCountryNews.getResponse().getResults();
                }
                networkState.postValue(NetworkState.LOADED);
                callback.onResult(articles, null , TWO );
            }

            @Override
            public void onFailure(Throwable t, int errorImageResId) {
                handelFailureCase(t);
            }
        };
        callApi(getCall(ONE), dataFetcherCallback);
    }

    //this will load the previous page
    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Article> callback) {
        DataFetcherCallback dataFetcherCallback = new DataFetcherCallback() {
            @Override
            public void onDataFetched(Object body) {
                List<Article> articles = new ArrayList<>();
                if (body instanceof ResponseSection){
                    ResponseSection responseSection = (ResponseSection) body;
                    articles = responseSection.getResponse().getResults();
                }else if (body instanceof ResponseCountryNews){
                    ResponseCountryNews responseCountryNews = (ResponseCountryNews) body;
                    articles = responseCountryNews.getResponse().getResults();
                }
                Integer adjacentKey = (params.key > ONE) ? params.key - ONE : null;
                networkState.postValue(NetworkState.LOADED);
                callback.onResult(articles, adjacentKey );
            }

            @Override
            public void onFailure(Throwable t, int errorImageResId) {
                handelFailureCase(t);
            }
        };
        callApi(getCall(params.key), dataFetcherCallback);
    }

    //this will load the next page
    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Article> callback) {
        DataFetcherCallback dataFetcherCallback = new DataFetcherCallback() {
            @Override
            public void onDataFetched(Object body) {
                List<Article> articles = new ArrayList<>();
                int pagesNumber = ZERO;
                if (body instanceof ResponseSection){
                    ResponseSection responseSection = (ResponseSection) body;
                    pagesNumber = responseSection.getResponse().getPages();
                    articles = responseSection.getResponse().getResults();
                }else if (body instanceof ResponseCountryNews){
                    ResponseCountryNews responseCountryNews = (ResponseCountryNews) body;
                    pagesNumber = responseCountryNews.getResponse().getPages();
                    articles = responseCountryNews.getResponse().getResults();
                }
                Integer key = pagesNumber > params.key ? params.key + ONE : null;
                networkState.postValue(NetworkState.LOADED);
                callback.onResult(articles, key);
            }

            @Override
            public void onFailure(Throwable t, int errorImageResId) {
                handelFailureCase(t);
            }
        };
        callApi(getCall(params.key), dataFetcherCallback);
    }

    private Call getCall (int pageNumber){
        APIServices apiServices = APIClient.getAPIServices(activity);
        Map<String, Object> queries = getQueriesMap(activity, pageNumber);
        if (null != searchKeyword) queries.put(QUERY_Q_KEYWORD, searchKeyword);
        if (isCountrySection) return apiServices.getCountrySection(sectionId, queries);
        else return apiServices.getArticlesBySection(sectionId, queries);
    }

    MutableLiveData getNetworkState() {
        return networkState;
    }

    private void handelFailureCase(Throwable t){
        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, t.getMessage()));
    }

    /**
     * to handle calling the APIs and get its response
     * @param call to call the API
     * @param dataFetcherCallback to handle success or failure cases
     */
    private void callApi(Call call, DataFetcherCallback dataFetcherCallback){
        if (isConnected(activity))getResponse(call, dataFetcherCallback);
        else handelFailureCase(noConnectionThrowable);
    }
}