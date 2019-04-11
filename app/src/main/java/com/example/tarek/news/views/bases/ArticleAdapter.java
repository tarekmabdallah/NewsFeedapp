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

package com.example.tarek.news.views.bases;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tarek.news.R;
import com.example.tarek.news.models.articles.Article;
import com.example.tarek.news.models.articles.Fields;

import static com.example.tarek.news.utils.Constants.ZERO;
import static com.example.tarek.news.utils.ViewsUtils.loadImage;
import static com.example.tarek.news.views.section.SectionActivity.openSectionActivity;


public class ArticleAdapter extends ArrayAdapter<Article> {

    private final Activity activity;

    public ArticleAdapter(@NonNull Context activity) {
        super(activity, ZERO);
        this.activity = (Activity) activity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rootView = convertView;
        ViewHolderItem holder ;
        if (rootView == null){
            rootView = LayoutInflater.from(activity).inflate(R.layout.list_item, parent ,false);
            holder = new ViewHolderItem();
            holder.articleImage = rootView.findViewById(R.id.article_image);
            holder.articleTitle = rootView.findViewById(R.id.article_title);
            holder.articleSection = rootView.findViewById(R.id.section);
            holder.articleAuthor = rootView.findViewById(R.id.author);
            holder.articleDate = rootView.findViewById(R.id.date);
            rootView.setTag(holder);
        }else {
            holder = (ViewHolderItem) rootView.getTag();
        }
        final Article currentArticle = getItem(position);
        if (currentArticle != null) {
            holder.articleTitle.setText(currentArticle.getWebTitle());
            holder.articleDate.setText(getDate(currentArticle.getWebPublicationDate()));
            Fields fields = currentArticle.getFields();
            if (null != fields) {
                holder.articleAuthor.setText(fields.getAuthorName());
                loadImage(fields.getThumbnail(), holder.articleImage);
            }
            View.OnClickListener onClickSectionTVListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String sectionName = currentArticle.getSectionName();
                    String activityTitle = activity.getTitle().toString();
                    if (!activityTitle.equals(sectionName))
                        openSectionActivity(activity, currentArticle.getSectionId(), currentArticle.getSectionName());
                }
            };
            holder.articleSection.setOnClickListener(onClickSectionTVListener);
            holder.articleSection.setText(currentArticle.getSectionName());

        }
        return rootView;
    }

    private String getDate(String inputDate){
        String t = "T";
        return inputDate.split(t)[ZERO];
    }

    private static class ViewHolderItem {
        ImageView articleImage;
        TextView articleTitle;
        TextView articleSection;
        TextView articleAuthor;
        TextView articleDate;
    }
}
