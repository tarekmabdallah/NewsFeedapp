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

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.gmail.tarekmabdallah91.news.apis.APIClient;
import com.gmail.tarekmabdallah91.news.apis.APIServices;
import com.gmail.tarekmabdallah91.news.models.articles.Article;
import com.gmail.tarekmabdallah91.news.models.section.ResponseSection;
import com.gmail.tarekmabdallah91.news.utils.NetworkState;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.subjects.ReplaySubject;

import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.getQueriesMap;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.printLog;

/**
 * Responsible for loading the data by page
 */

public class ItemDataSource extends PageKeyedDataSource<String, Article> {

    private final APIServices articlesService;
    private final MutableLiveData networkState;
    private final ReplaySubject<Article> articlesObservable;
    private Context context;
    private String sectionId;

    ItemDataSource(Context context, String sectionId) {
        this.context = context;
        this.articlesService = APIClient.getInstance(context).create(APIServices.class);
        this.networkState = new MutableLiveData();
        this.articlesObservable = ReplaySubject.create();
        this.sectionId = sectionId;
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public ReplaySubject<Article> getArticles() {
        return articlesObservable;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull final LoadInitialCallback<String, Article> callback) {
        printLog( "Loading Initial Rang, Count " + params.requestedLoadSize);

        networkState.postValue(NetworkState.LOADING);
        Call<ResponseSection> callBack = articlesService.getArticlesBySectionCall(sectionId, getQueriesMap(context, 1));
        callBack.enqueue(new Callback<ResponseSection>() {
            @Override
            public void onResponse(Call<ResponseSection> call, Response<ResponseSection> response) {
                if (response.isSuccessful()) {
                    callback.onResult(response.body().getResponse().getResults(), Integer.toString(1), Integer.toString(2));
                    networkState.postValue(NetworkState.LOADED);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        response.body().getResponse().getResults().forEach(new Consumer<Article>() {
                            @Override
                            public void accept(Article article) {
                                articlesObservable.onNext(article);
                            }
                        });
                    }else {
                        Toast.makeText(context, "not Build.VERSION_CODES.N", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.e("API CALL", response.message());
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                }
            }

            @Override
            public void onFailure(Call<ResponseSection> call, Throwable t) {
                String errorMessage;
                if (t.getMessage() == null) {
                    errorMessage = "unknown error";
                } else {
                    errorMessage = t.getMessage();
                }
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                callback.onResult(new ArrayList<Article>(), Integer.toString(1), Integer.toString(2));
            }
        });
    }



    @Override
    public void loadAfter(@NonNull LoadParams<String> params, final @NonNull LoadCallback<String, Article> callback) {
        printLog( "Loading page " + params.key );
        networkState.postValue(NetworkState.LOADING);
        final AtomicInteger page = new AtomicInteger(0);
        try {
            page.set(Integer.parseInt(params.key));
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        Call<ResponseSection> callBack = articlesService.getArticlesBySectionCall(sectionId,getQueriesMap(context, page.get()));
        callBack.enqueue(new Callback<ResponseSection>() {
            @Override
            public void onResponse(Call<ResponseSection> call, Response<ResponseSection> response) {
                if (response.isSuccessful()) {
                    callback.onResult(response.body().getResponse().getResults(), Integer.toString(page.get()+1));
                    networkState.postValue(NetworkState.LOADED);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        response.body().getResponse().getResults().forEach(new Consumer<Article>() {
                            @Override
                            public void accept(Article t) {
                                articlesObservable.onNext(t);
                            }
                        });
                    }else {
                        Toast.makeText(context, "not Build.VERSION_CODES.N", Toast.LENGTH_LONG).show();
                    }
                } else {
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    Log.e("API CALL", response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseSection> call, Throwable t) {
                String errorMessage;
                if (t.getMessage() == null) {
                    errorMessage = "unknown error";
                } else {
                    errorMessage = t.getMessage();
                }
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                callback.onResult(new ArrayList<Article>(), Integer.toString(page.get()));
            }
        });
    }


    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Article> callback) {

    }
}
