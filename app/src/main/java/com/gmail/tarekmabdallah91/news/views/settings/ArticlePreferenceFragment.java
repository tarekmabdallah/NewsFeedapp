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

package com.gmail.tarekmabdallah91.news.views.settings;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.data.sp.SharedPreferencesHelper;

import java.util.Arrays;
import java.util.List;

import static com.gmail.tarekmabdallah91.news.utils.Constants.EMPTY_STRING;
import static com.gmail.tarekmabdallah91.news.utils.Constants.ZERO;

public class ArticlePreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    private Activity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_main);
        setPreferencesViews();
    }

    private void setPreferencesViews(){
        List<String> sectionsTitles, sectionsIds;
        sectionsTitles = Arrays.asList(activity.getResources().getStringArray(R.array.sections_labels));
        sectionsIds = Arrays.asList(activity.getResources().getStringArray(R.array.sections_ids));

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
        preference.setSummary(getLatestChoicesFromPreference(preference, newValue));
        return true;
    }

    private CharSequence getLatestChoicesFromPreference (Preference preference, Object newValue){
        SharedPreferencesHelper sharedPreferencesHelper = SharedPreferencesHelper.getInstance(activity);
        sharedPreferencesHelper.saveIsSPUpdated(true);
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
}