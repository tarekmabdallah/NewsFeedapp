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

package com.example.tarek.news.views;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.tarek.news.R;

public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
    }


    public static class ArticlePreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {


        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            Preference orderedByTag = findPreference(getString(R.string.tag_list_key));
            setSummaryToPreference(orderedByTag , getString(R.string.tag_list_key));

            Preference orderedByDate = findPreference(getString(R.string.date_list_key));
            setSummaryToPreference(orderedByDate , getString(R.string.date_list_key));
        }

        private void setSummaryToPreference(Preference preferences , String preferenceStringValue) {
            preferences.setOnPreferenceChangeListener(this);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(preferences.getContext());
            //  String value = sharedPreferences.getString(preferenceStringValue, ArticleQueryUtils.EMPTY_NAME);
            // onPreferenceChange(preferences, value);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String value = newValue.toString();

            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(value);
//                if (prefIndex >= ArticleQueryUtils.ZERO) {
//                    CharSequence[] labels = listPreference.getEntries();
//                    preference.setSummary(labels[prefIndex]);
//                }
            }
            return true;
        }
    }
}
