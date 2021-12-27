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

package com.gmail.tarekmabdallah91.smooth.service.repository.network.paging;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.apis.APIClient;
import com.gmail.tarekmabdallah91.news.apis.APIServices;
import com.gmail.tarekmabdallah91.news.apis.DataFetcherCallback;
import com.gmail.tarekmabdallah91.news.models.articles.Article;
import com.gmail.tarekmabdallah91.news.models.countryNews.ResponseCountryNews;
import com.gmail.tarekmabdallah91.news.models.section.CommonResponse;
import com.gmail.tarekmabdallah91.news.models.section.ResponseSection;
import com.gmail.tarekmabdallah91.news.utils.NetworkState;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import rx.subjects.ReplaySubject;

import static com.gmail.tarekmabdallah91.news.apis.APIClient.getResponse;
import static com.gmail.tarekmabdallah91.news.utils.Constants.COUNTRY_SECTION;
import static com.gmail.tarekmabdallah91.news.utils.Constants.ONE;
import static com.gmail.tarekmabdallah91.news.utils.Constants.QUERY_PAGE_KEYWORD;
import static com.gmail.tarekmabdallah91.news.utils.Constants.QUERY_Q_KEYWORD;
import static com.gmail.tarekmabdallah91.news.utils.Constants.RX_KEYWORD;
import static com.gmail.tarekmabdallah91.news.utils.Constants.TWO;
import static com.gmail.tarekmabdallah91.news.utils.Constants.ZERO;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.getQueriesMap;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.getSectionIdOrCountrySection;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.isConnected;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.printLog;

/**
 * Responsible for loading the data by page
 */

public class ItemDataSource extends PageKeyedDataSource<String, Article> {

    private final MutableLiveData<NetworkState> networkState;
    private final ReplaySubject<Article> articlesObservable;
    private Activity activity;
    private String sectionId, searchKeyword;
    private boolean isCountrySection;
    private Throwable noConnectionThrowable;
    private Call currentCall;
    private Map<String, Object> queries;

    ItemDataSource(Activity activity) {
        this.activity = activity;
        this.networkState = new MutableLiveData<>();
        this.articlesObservable = ReplaySubject.create();
        Intent intent = activity.getIntent();
        this.sectionId = getSectionIdOrCountrySection (activity.getIntent());
        this.isCountrySection = null != intent.getStringExtra(COUNTRY_SECTION);
        this.searchKeyword = null;
        noConnectionThrowable = new Throwable(activity.getString(R.string.no_connection));
        queries = getQueriesMap(activity);
        if (null != searchKeyword) queries.put(QUERY_Q_KEYWORD, searchKeyword);
        currentCall = getCurrentCall(queries);
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull final LoadInitialCallback<String, Article> callback) {
        printLog( "Loading Initial Rang, Count " + params.requestedLoadSize);
        networkState.postValue(NetworkState.LOADING);
        loadData(ONE, callback, null);
    }


    @Override
    public void loadAfter(@NonNull LoadParams<String> params, final @NonNull LoadCallback<String, Article> callback) {
        printLog( "Loading page " + params.key );
        final AtomicInteger page = new AtomicInteger(ZERO);
        try {
            page.set(Integer.parseInt(params.key));
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        loadData(page.get()+ ONE, null, callback);
    }


    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Article> callback) {}

    private void loadData(final int pageNumber,final LoadInitialCallback<String, Article> initialCallback,final LoadCallback<String, Article> callback){
        DataFetcherCallback dataFetcherCallback = new DataFetcherCallback() {
            @Override
            public void onDataFetched(Object body) {
                CommonResponse response = null;
                if (body instanceof ResponseSection){
                    ResponseSection responseSection = (ResponseSection) body;
                    response = responseSection.getResponse();
                }else if (body instanceof ResponseCountryNews){
                    ResponseCountryNews responseCountryNews = (ResponseCountryNews) body;
                    response = responseCountryNews.getResponse();
                }
                if (null != response) {
                    List<Article> articles = response.getResults();
                    if (null != articles && !articles.isEmpty()){
                        networkState.postValue(NetworkState.LOADED);
                        if (ONE == pageNumber) {
                            initialCallback.onResult(articles, String.valueOf(ONE), String.valueOf(TWO));
                        } else {
                            callback.onResult(articles, String.valueOf(pageNumber));
                        }
                    }else {
                        networkState.postValue(new NetworkState(NetworkState.Status.FAILED,
                                activity.getString(R.string.no_news_found)));
                    }
                }
            }

            @Override
            public void onFailure(Throwable t, int errorImageResId) {
                handelFailureCase(t);
            }
        };
        updateQuery(pageNumber);
        callApi(currentCall, dataFetcherCallback);
    }

    /**
     * to handle calling the APIs and get its response
     * @param call to call the API
     * @param dataFetcherCallback to handle success or failure cases
     */
    private void callApi(Call call, DataFetcherCallback dataFetcherCallback){
        networkState.postValue(NetworkState.LOADING);
        if (isConnected(activity))getResponse(call, dataFetcherCallback);
        else handelFailureCase(noConnectionThrowable);
    }

    private void updateQuery (int pageNumber){
        if (ZERO < pageNumber) queries.put(QUERY_PAGE_KEYWORD, pageNumber);
    }

    private Call getCurrentCall(Map<String, Object> queries) {
        APIServices apiServices = APIClient.getAPIServices(activity);
        if (isCountrySection) return apiServices.getCountrySectionCall(sectionId.toLowerCase(), queries);
        else return apiServices.getArticlesBySectionCall(sectionId, queries);
    }

    private void handelFailureCase(Throwable t){
        Log.d(RX_KEYWORD, "onError" + t.getMessage());
        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, t.getMessage()));
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public ReplaySubject<Article> getArticles() {
        return articlesObservable;
    }
}
