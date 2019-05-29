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
import android.support.annotation.Nullable;
import android.util.Log;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.apis.APIServices;
import com.gmail.tarekmabdallah91.news.models.articles.Article;
import com.gmail.tarekmabdallah91.news.models.countryNews.ResponseCountryNews;
import com.gmail.tarekmabdallah91.news.models.section.CommonResponse;
import com.gmail.tarekmabdallah91.news.models.section.ResponseSection;
import com.gmail.tarekmabdallah91.news.utils.NetworkState;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static com.gmail.tarekmabdallah91.news.utils.Constants.IS_COUNTRY_SECTION;
import static com.gmail.tarekmabdallah91.news.utils.Constants.ONE;
import static com.gmail.tarekmabdallah91.news.utils.Constants.QUERY_Q_KEYWORD;
import static com.gmail.tarekmabdallah91.news.utils.Constants.RX_KEYWORD;
import static com.gmail.tarekmabdallah91.news.utils.Constants.TWO;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.getQueriesMap;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.isConnected;

public class ItemDataSource extends PageKeyedDataSource<Integer, Article> {


    private Retrofit retrofit;
    private Activity activity;
    private String sectionId, searchKeyword;
    private MutableLiveData<NetworkState> networkState;
    private boolean isCountrySection;
    private Throwable noConnectionThrowable;
    private final static String LOAD_BEFORE = "LOAD_BEFORE", LOAD_AFTER = "LOAD_AFTER", LOAD_INITIAL = "LOAD_INITIAL";

    ItemDataSource(Activity activity, String sectionId, String searchKeyword, Retrofit retrofit){
        this.activity = activity;
        this.sectionId = sectionId;
        this.searchKeyword = searchKeyword;
        this.retrofit = retrofit;
        isCountrySection = activity.getIntent().getBooleanExtra(IS_COUNTRY_SECTION,false);
        networkState = new MutableLiveData<>();
        noConnectionThrowable = new Throwable(activity.getString(R.string.no_connection));
    }

    //this will be called once to load the initial data
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Article> callback) {
        networkState.postValue(NetworkState.LOADING);
        loadData(LOAD_INITIAL, ONE, callback, null);
    }

    //this will load the previous page
    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Article> callback) {
        loadData(LOAD_BEFORE, params.key, null, callback);
    }

    //this will load the next page
    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Article> callback) {
        loadData(LOAD_AFTER, params.key, null, callback);
    }

    private Observable getObservable(int pageNumber){
        APIServices apiServices = retrofit.create(APIServices.class);
        Map<String, Object> queries = getQueriesMap(activity, pageNumber);
        if (null != searchKeyword) queries.put(QUERY_Q_KEYWORD, searchKeyword);
        if (isCountrySection) return apiServices.getCountrySection(sectionId, queries);
        else return apiServices.getArticlesBySection(sectionId, queries);
    }

    private void loadData(final String state, final int paramsKey
            , @Nullable final LoadInitialCallback<Integer, Article> initialCallback
            , @Nullable final LoadCallback<Integer, Article> callback){

        Observer observer = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(RX_KEYWORD, "onSubscribe" + d.isDisposed());
            }

            @Override
            public void onNext(Object body) {
                Log.d(RX_KEYWORD, "onNext" + body.toString());
                CommonResponse response = null;
                if (body instanceof ResponseSection){
                    ResponseSection responseSection = (ResponseSection) body;
                    response = responseSection.getResponse();

                }else if (body instanceof ResponseCountryNews){
                    ResponseCountryNews responseCountryNews = (ResponseCountryNews) body;
                    response = responseCountryNews.getResponse();
                }
                if (null != response) {
                    int pagesNumber = response.getPages();
                    List<Article> articles = response.getResults();
                    if (null != articles && !articles.isEmpty()){
                        networkState.postValue(NetworkState.LOADED);
                        Integer key;
                        switch (state){
                            case LOAD_BEFORE:
                                key = (paramsKey > ONE) ? paramsKey - ONE : null;
                                callback.onResult(articles, key );
                                break;
                            case LOAD_AFTER:
                                key = pagesNumber > paramsKey ? paramsKey + ONE : null;
                                callback.onResult(articles, key );
                                break;
                            default:
                                initialCallback.onResult(articles, null, TWO);
                        }
                    }else {
                        networkState.postValue(new NetworkState(NetworkState.Status.FAILED,
                                activity.getString(R.string.no_news_found)));
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                handelFailureCase(e);
            }

            @Override
            public void onComplete() {
                Log.d(RX_KEYWORD, "onComplete");
            }
        };
        if (isConnected(activity)){
            getObservable(paramsKey).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe(observer);
        }else handelFailureCase(noConnectionThrowable);
    }

    MutableLiveData getNetworkState() {
        return networkState;
    }

    private void handelFailureCase(Throwable t){
        Log.d(RX_KEYWORD, "onError" + t.getMessage());
        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, t.getMessage()));
    }
}