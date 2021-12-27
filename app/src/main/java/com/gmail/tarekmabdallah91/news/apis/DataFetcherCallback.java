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

package com.gmail.tarekmabdallah91.news.apis;

public interface DataFetcherCallback {
    /**
     * called when there is a successful response to view the data in the UI
     */
    void onDataFetched(Object body);


    /**
     * called when the call failed to get the response for any reason (each case is handled in the Method which deal with all calls in RetrofitWrapper)
     */
    void onFailure(Throwable t, int errorImageResId);
}
