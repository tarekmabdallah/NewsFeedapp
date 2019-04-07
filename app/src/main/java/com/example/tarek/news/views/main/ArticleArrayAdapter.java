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

package com.example.tarek.news.views.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tarek.news.R;
import com.example.tarek.news.models.Search.Article;

import java.util.List;


public class ArticleArrayAdapter extends ArrayAdapter<Article> {

    private final Context context;
    private static final String NEW_LINE = "\n";
    private static final int ZERO = 0;
    private static final int MAX_LENGTH_LINE_1 = 11;
    private static final String T = "T";
    private static final String Z = "Z";
    private static final String EMPTY_TEXT = "";


    ArticleArrayAdapter(@NonNull Context context, List<Article> articles) {
        super(context, 0 , articles);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rootView = convertView;
        ViewHolderItem viewHolderItem ;
        if (rootView == null){
            rootView = LayoutInflater.from(context).inflate(R.layout.list_item, parent ,false);
            viewHolderItem = new ViewHolderItem();
            viewHolderItem.titleTextView = rootView.findViewById(R.id.title);
            viewHolderItem.sectionTextView = rootView.findViewById(R.id.section);
            viewHolderItem.authorTextView = rootView.findViewById(R.id.author);
            viewHolderItem.dateTextView = rootView.findViewById(R.id.date);
            rootView.setTag(viewHolderItem);
        }else {
            viewHolderItem = (ViewHolderItem) rootView.getTag();
        }
        Article currentArticle = getItem(position);
        if (currentArticle != null) { // TODO: 07-Apr-19 to get author name instead of type
            viewHolderItem.titleTextView.setText(currentArticle.getWebTitle());
            viewHolderItem.sectionTextView.setText(currentArticle.getSectionName());
            viewHolderItem.authorTextView.setText(currentArticle.getType());
            viewHolderItem.dateTextView.setText(makeDateInTwoLines(currentArticle.getWebPublicationDate()));
        }
        return rootView;
    }

    private String makeDateInTwoLines (String inputDate){
        String date = inputDate.substring(ZERO, MAX_LENGTH_LINE_1).replace(T, EMPTY_TEXT); // get first 11 chars to it (Date like 2018-3-13T)
        String time = inputDate.substring(MAX_LENGTH_LINE_1).replace(Z, EMPTY_TEXT); // get all remained chars
        return date + NEW_LINE + time;
    }


    private static class ViewHolderItem {
        TextView titleTextView ;
        TextView sectionTextView;
        TextView authorTextView;
        TextView dateTextView;
    }
}
