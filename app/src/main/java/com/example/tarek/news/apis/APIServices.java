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

package com.example.tarek.news.apis;

import com.example.tarek.news.models.countryNews.ResponseCountryNews;
import com.example.tarek.news.models.section.ResponseSection;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

import static com.example.tarek.news.utils.Constants.HEADER_API_KEY;

public interface APIServices {

//    https://square.github.io/retrofit/

    @Headers({HEADER_API_KEY}) /*HEADER_FORMAT, HEADER_LANG,  are set in the interceptor in APIClient */
    @GET("{section}")
    Call<ResponseSection> getArticlesBySection(@Path("section") String section, @QueryMap Map<String, Object> queries);

    @Headers({HEADER_API_KEY})
    @GET("world/{section}") // to get country news / section
    Call<ResponseCountryNews> getCountrySection(@Path("section") String section, @QueryMap Map<String, Object> queries);
}
