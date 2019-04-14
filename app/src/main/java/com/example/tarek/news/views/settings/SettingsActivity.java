/*
Copyright 2019 tarekmabdallah91@gmail.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.example.tarek.news.views.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.example.tarek.news.R;
import com.example.tarek.news.apis.APIClient;
import com.example.tarek.news.apis.APIServices;
import com.example.tarek.news.apis.DataFetcherCallback;
import com.example.tarek.news.data.sp.SharedPreferencesHelper;
import com.example.tarek.news.models.sections.ResponseSections;
import com.example.tarek.news.models.sections.Section;
import com.example.tarek.news.utils.GsonObject;
import com.example.tarek.news.views.bases.BaseActivityNoMenu;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static com.example.tarek.news.utils.Constants.EMPTY_STRING;
import static com.example.tarek.news.utils.Constants.SECTIONS_KEYWORD;
import static com.example.tarek.news.utils.Constants.ZERO;
import static com.example.tarek.news.utils.ViewsUtils.getQueriesMap;
import static com.example.tarek.news.utils.ViewsUtils.showProgressBar;
import static com.example.tarek.news.utils.ViewsUtils.showShortToastMsg;
import static com.example.tarek.news.views.sections.SectionsFragment.saveSectionsListsInSP;

public class SettingsActivity extends BaseActivityNoMenu {

    @Override
    protected int getLayoutResId() {
        return R.layout.settings_layout;
    }

    @Override
    protected String getSectionTitle() {
        return getString(R.string.settings_label);
    }

    public static class ArticlePreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, DataFetcherCallback {

        private SharedPreferencesHelper sharedPreferencesHelper;
        private List<String> sectionsTitles, sectionsIds;
        private Activity activity;

        /**
         * check if the sharedPreferences has responseSections or not, if not we will call the api to get it and save it.
         */
        protected void initiateValues() {
            activity = getActivity();
            sharedPreferencesHelper = SharedPreferencesHelper.getInstance(activity);
            String responseSectionsString = sharedPreferencesHelper.getResponseSections();
            if (EMPTY_STRING.equals(responseSectionsString)){
                showProgressBar(activity, true);
                APIServices apiServices = APIClient.getInstance(activity).create(APIServices.class);
                Map <String, Object> queries = getQueriesMap(activity);
                Call getSections = apiServices.getSections(SECTIONS_KEYWORD, queries);
                APIClient.getResponse(getSections, this);
            }else {
                Gson gson = GsonObject.getGsonInstance();
                ResponseSections responseSections = gson.fromJson(responseSectionsString, ResponseSections.class);
                setSectionsLists(responseSections.getResponse().getResults());
                setPreferencesViews();
            }
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);
            initiateValues();
        }

        private void setPreferencesViews(){
            ListPreference sectionsPreference = (ListPreference) findPreference(getString(R.string.sections_list_key));
            sectionsPreference.setEntries(sectionsTitles.toArray(new CharSequence[ZERO]));
            sectionsPreference.setEntryValues(sectionsIds.toArray(new CharSequence[ZERO]));
            setSummaryToPreference(sectionsPreference , getString(R.string.sections_list_key));

            Preference orderedBy = findPreference(getString(R.string.order_by_list_key));
            setSummaryToPreference(orderedBy , getString(R.string.order_by_list_key));

            Preference orderedDate = findPreference(getString(R.string.order_date_list_key));
            setSummaryToPreference(orderedDate , getString(R.string.order_date_list_key));
        }

        private void setSummaryToPreference(Preference preferences , String preferenceStringValue) {
            preferences.setOnPreferenceChangeListener(this);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(preferences.getContext());
              String value = sharedPreferences.getString(preferenceStringValue, EMPTY_STRING);
             onPreferenceChange(preferences, value);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            sharedPreferencesHelper.saveIsSPUpdated(true);
            preference.setSummary(getLatestChoicesFromPreference(preference, newValue));
            return true;
        }

        private CharSequence getLatestChoicesFromPreference (Preference preference, Object newValue){
            String value = newValue.toString();
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(value);
                if (prefIndex >= ZERO) {
                    CharSequence[] labels = listPreference.getEntries();
                    return labels[prefIndex];
                }
            }
            return EMPTY_STRING;
        }

        @Override
        public void onDataFetched(Object body) {
            showProgressBar(activity, false);
            if (body instanceof ResponseSections) {
                ResponseSections responseSections = (ResponseSections) body;
                List sectionList = responseSections.getResponse().getResults();
                if (sectionList != null && !sectionList.isEmpty()){
                    saveSectionsListsInSP(activity, responseSections);
                    setSectionsLists(responseSections.getResponse().getResults());
                    setPreferencesViews();
                }
            }
        }

        @Override
        public void onFailure(Throwable t, int errorImageResId) {
            showProgressBar(activity, false);
            showShortToastMsg(activity, t.getMessage());
            activity.finish();
        }

        private void setSectionsLists(List<Section> sections){
            sectionsTitles = new ArrayList<>();
            sectionsIds = new ArrayList<>();
            for(Section section: sections){
                sectionsTitles.add(section.getWebTitle());
                sectionsIds.add(section.getId());
            }
        }
    }

    public static void openSettingsActivity(Context context) {
        Intent openSettingsActivity = new Intent(context, SettingsActivity.class);
        context.startActivity(openSettingsActivity);
    }
}
