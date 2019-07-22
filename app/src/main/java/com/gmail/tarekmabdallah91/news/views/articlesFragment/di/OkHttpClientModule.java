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

package com.gmail.tarekmabdallah91.news.views.articlesFragment.di;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.gmail.tarekmabdallah91.news.utils.Constants.THREE;
import static java.util.concurrent.TimeUnit.SECONDS;

@Module
public class OkHttpClientModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache, Interceptor headerInterceptor, HttpLoggingInterceptor loggingInterceptor, Dispatcher dispatcher) {
        final int TIMEOUT = 60;
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(TIMEOUT, SECONDS)
                .readTimeout(TIMEOUT, SECONDS)
                .writeTimeout(TIMEOUT, SECONDS)
                .addInterceptor(headerInterceptor)
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .dispatcher(dispatcher);
        return client.build();
    }

    @Provides
    @Singleton
    Cache provideHttpCache(Context Context) {
        final int CASH_SIZE = 10 * 1024 * 1024 /*10MB*/;
        return new Cache(Context.getCacheDir(), CASH_SIZE);
    }

    @Provides
    @Singleton
    Dispatcher provideDispatcher (){
        // this is the critical point that helped me a lot.
        // we using only one retrofit instance in our application
        // and it uses this dispatcher which can only do 1 request at the same time
        // the docs says : Set the maximum number of requests to execute concurrently.
        // Above this requests queue in memory, waiting for the running calls to complete.
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(THREE); // to make 3 requites in the same time
        return dispatcher;
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideLoggingInterceptor (){
        //this is the part where you will see all the logs of retrofit requests
        //and responses
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    @Provides
    @Singleton
    Interceptor provideHeaderInterceptor (){
        return new Interceptor() {
            @NonNull
            @Override
            public synchronized okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder requestBuilder = request.newBuilder();
                // requestBuilder.addHeader("Content-Type", "application/json");
                requestBuilder.addHeader("format", "json");
                requestBuilder.addHeader("Accept-Language", "en-US");
                // requestBuilder.addHeader(QUERY_API_KEY_KEYWORD, API_KEY); // API's does not read it here so not used as the header is set in each Call in APIServices
                return chain.proceed(requestBuilder.build());
            }
        };
    }
}
