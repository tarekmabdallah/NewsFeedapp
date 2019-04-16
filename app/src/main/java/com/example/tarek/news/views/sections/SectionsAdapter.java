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
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tarek.news.R;
import com.example.tarek.news.views.search.SearchHistoryAdapter;

import static com.example.tarek.news.utils.Constants.makeTypeFaceLabelStyle;

public class SectionsAdapter extends SearchHistoryAdapter {

    SectionsAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Context context = parent.getContext();
        SectionViewHolder holder;
        if (null == convertView){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_section, parent, false);
            holder = new SectionViewHolder();
            holder.sectionTitle = convertView.findViewById(R.id.section_title);
            makeTypeFaceLabelStyle(holder.sectionTitle);
            convertView.setTag(holder);
        }else {
            holder = (SectionViewHolder) convertView.getTag();
        }
        String sectionTitle = getItem(position);
        holder.sectionTitle.setText(sectionTitle);
        return convertView;
    }

    class SectionViewHolder {
        TextView sectionTitle ;
    }
}
