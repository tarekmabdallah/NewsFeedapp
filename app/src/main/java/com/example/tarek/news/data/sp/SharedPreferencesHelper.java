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

package com.example.tarek.news.data.sp;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.tarek.news.models.sections.ResponseSections;
import com.example.tarek.news.utils.GsonObject;
import com.google.gson.Gson;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.tarek.news.utils.Constants.EMPTY_STRING;
import static com.example.tarek.news.utils.Constants.IS_SP_UPDATED;
import static com.example.tarek.news.utils.Constants.SEARCH_HISTORY_KEYWORD;
import static com.example.tarek.news.utils.Constants.SECTIONS_KEYWORD;
import static com.example.tarek.news.utils.ViewsUtils.convertListToString;
import static com.example.tarek.news.utils.ViewsUtils.convertStringToList;

public class SharedPreferencesHelper {

    private static final String SHARED_PREF = "sp";
    private SharedPreferences sharedPreferences;
    private static SharedPreferencesHelper helper;

    private SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
    }

    public static SharedPreferencesHelper getInstance(Context context) {
        if (null == helper) helper = new SharedPreferencesHelper(context);
        return helper;
    }

    public void clear() {
        sharedPreferences.edit().clear().apply();
    }

    public void saveSearchHistory(List<String> stringList){
        String historyString = convertListToString(stringList);
        sharedPreferences.edit().putString(SEARCH_HISTORY_KEYWORD, historyString).apply();
    }

    public List<String> getSearchHistory(){
        return convertStringToList(sharedPreferences.getString(SEARCH_HISTORY_KEYWORD, EMPTY_STRING));
    }

    public void saveResponseSections(ResponseSections responseSections){
        Gson gson = GsonObject.getGsonInstance();
        String responseSectionsString = gson.toJson(responseSections);
        sharedPreferences.edit().putString(SECTIONS_KEYWORD, responseSectionsString).apply();
    }

    /**
     * @return string of ResponseSections .. it may be EMPTY_STRING , then sectionsAPI will be called
     */
    public String getResponseSections(){
        return sharedPreferences.getString(SECTIONS_KEYWORD, EMPTY_STRING);
    }

    public void saveIsSPUpdated(boolean updated){
        sharedPreferences.edit().putBoolean(IS_SP_UPDATED, updated).apply();
    }

    public boolean getIsSPUpdated(){
        return sharedPreferences.getBoolean(IS_SP_UPDATED, false);
    }
}
