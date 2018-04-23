package com.example.tarek.newsfeedapp.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.tarek.newsfeedapp.R;
import com.example.tarek.newsfeedapp.utils.ArticleQueryUtils;

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

            Preference searched = findPreference(getString(R.string.search_key));
            setSummaryToPreference(searched , getString(R.string.search_key));

        }

        private void setSummaryToPreference(Preference preferences , String preferenceStringValue) {
            preferences.setOnPreferenceChangeListener(this);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(preferences.getContext());
            String value = sharedPreferences.getString(preferenceStringValue, ArticleQueryUtils.EMPTY_NAME);
            onPreferenceChange(preferences, value);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String value = newValue.toString();

            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(value);
                if (prefIndex >= ArticleQueryUtils.ZERO) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            }else {
                // in case of the user searched for any thing
                preference.setSummary(value);
            }
            return true;
        }
    }
}
