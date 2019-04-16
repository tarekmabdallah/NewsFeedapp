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

package com.gmail.tarekmabdallah91.news.apis;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Dispatcher;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.gmail.tarekmabdallah91.news.utils.Constants.THREE;
import static com.gmail.tarekmabdallah91.news.utils.Constants.URL_KEYWORD;
import static java.util.concurrent.TimeUnit.SECONDS;

public class APIClient {

    private static final String BASE_URL = "https://content.guardianapis.com/";

    private static Retrofit retrofit;

    public static Retrofit getInstance(final Context context) {
        if (retrofit == null) {

            Interceptor interceptor = new Interceptor() {
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

            // this is the critical point that helped me a lot.
            // we using only one retrofit instance in our application
            // and it uses this dispatcher which can only do 1 request at the same time
            // the docs says : Set the maximum number of requests to execute concurrently.
            // Above this requests queue in memory, waiting for the running calls to complete.
            Dispatcher dispatcher = new Dispatcher();
            dispatcher.setMaxRequests(THREE); // to make 3 requites in the same time

            final int TIMEOUT = 60, CASH_SIZE = 10 * 1024 * 1024 /*10MB*/;
            Cache cache = new Cache(context.getCacheDir(), CASH_SIZE);
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(TIMEOUT, SECONDS)
                    .readTimeout(TIMEOUT, SECONDS)
                    .writeTimeout(TIMEOUT, SECONDS)
                    .addInterceptor(interceptor)
                    .cache(cache)
                    .dispatcher(dispatcher);

            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(builder.build()).
                    addConverterFactory(GsonConverterFactory.create()).build();
            retrofit = new Retrofit.Builder().
                    baseUrl(BASE_URL).
                    addConverterFactory(GsonConverterFactory.create()).
                    build();
        }
        return retrofit;
    }

    /**
     * https://github.com/square/retrofit/blob/master/samples/src/main/java/com/example/retrofit/ErrorHandlingAdapter.java
     *
     * @param call-
     * @param dataFetcherCallback-
     */
    public static void getResponse(Call call, final DataFetcherCallback dataFetcherCallback) {
        final int errorImageResId = android.R.drawable.stat_notify_error;
        call.clone().enqueue(new Callback() { // call.clone() to avoid java.lang.IllegalStateException: Already executed.
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                int code = response.code();
                String message = response.message();
                Throwable throwable;
                printCallUrlInLogs(call);
                if (response.isSuccessful())
                    dataFetcherCallback.onDataFetched(response.body());
                else {
                    if (code >= 400 && code < 500) {
                        throwable = new Throwable(message);
                    } else if (code >= 500 && code < 600) //Internal Server Error "SERVER ERROR "
                        throwable = new Throwable(message);
                    else
                        throwable = new Throwable("FATAL ERROR\nUnexpected response\n" + message);
                    dataFetcherCallback.onFailure(throwable, errorImageResId);
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                printCallUrlInLogs(call);
                String message = t.getMessage();
                if (t instanceof IOException) message = "NETWORK ERROR " + message;
                else message = "FATAL ERROR\nUnexpected response " + message;
                dataFetcherCallback.onFailure(new Throwable(message), errorImageResId);
            }
        });
    }

    private static void printCallUrlInLogs(Call call){
        HttpUrl url = call.request().url();
        Log.d(URL_KEYWORD, url.toString());
    }

}