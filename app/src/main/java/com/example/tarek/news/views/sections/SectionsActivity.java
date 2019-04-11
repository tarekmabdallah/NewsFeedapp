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

package com.example.tarek.news.views.sections;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tarek.news.R;
import com.example.tarek.news.models.sections.ResponseSections;
import com.example.tarek.news.models.sections.Section;
import com.example.tarek.news.views.section.SectionActivity;

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;

public class SectionsActivity extends SectionActivity {

    @BindView(R.id.list_view)
    ListView listView;

    private SectionsAdapter sectionsAdapter;
    private List<Section> sectionList;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_sections;
    }

    @Override
    protected void initiateValues() {
        super.initiateValues();
        setListView();
    }

    @Override
    protected String getSectionTitle() {
        return getString(R.string.sections_label);
    }

    private void setListView() {
        sectionsAdapter = new SectionsAdapter(this);
        listView.setAdapter(sectionsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Section section = sectionList.get(position);
                openSectionActivity(getBaseContext(), section.getId(), section.getWebTitle());
            }
        });
    }

    @Override
    protected Call getCall() {
        return apiServices.getSections(queries);
    }

    @Override
    protected void whenDataFetchedGetResponse(Object response) {
        sectionsAdapter.clear();
        if (response instanceof ResponseSections) {
            ResponseSections responseSections = (ResponseSections) response;
            sectionList = responseSections.getResponse().getResults();
            if (sectionList != null && !sectionList.isEmpty()) {
                for (Section section: sectionList) {
                    sectionsAdapter.add(section.getWebTitle());
                }
            } else handleNoDataFromResponse();
        }
    }

    public static Intent openSectionsActivity (Context context){
        return new Intent(context, SectionsActivity.class);
    }
}
