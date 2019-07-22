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

package com.gmail.tarekmabdallah91.news.views.articlesFragment.paging.diAdapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.models.articles.Article;
import com.gmail.tarekmabdallah91.news.models.articles.Fields;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.loadImage;

//@AutoFactory(implementing = ItemAdapterViewHolderFactory.class) will be used when inject item adapter
class ArticleViewHolder extends RecyclerView.ViewHolder {

    private ItemAdapter itemAdapter;
    @BindView(R.id.article_image)
    ImageView articleImage;
    @BindView(R.id.article_title)
    TextView articleTitle;
    @BindView(R.id.section)
    TextView articleSection;
    @BindView(R.id.author)
    TextView articleAuthor;
    @BindView(R.id.date)
    TextView articleDate;

    ArticleViewHolder(final ItemAdapter itemAdapter, @NonNull ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false));
        ButterKnife.bind(this, itemView);
        this.itemAdapter = itemAdapter;
        ButterKnife.bind(this, itemView);
        View.OnClickListener onArticleClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                itemAdapter.listener.onClickArticle(itemAdapter.getItem(position));
            }
        };
        itemView.setOnClickListener(onArticleClickListener);
    }

    @OnClick(R.id.section)
    void onClickSectionTV() {
        int position = getAdapterPosition();
        itemAdapter.listener.onClickArticleSection(itemAdapter.getItem(position));
    }

    void bindTo(Article currentArticle) {
        articleTitle.setText(currentArticle.getWebTitle());
        articleDate.setText(itemAdapter.getDate(currentArticle.getWebPublicationDate()));
        Fields fields = currentArticle.getFields();
        if (null != fields) {
            articleAuthor.setText(fields.getAuthorName());
            loadImage(fields.getThumbnail(), articleImage);
        }
        articleSection.setText(currentArticle.getSectionName());
    }
}
