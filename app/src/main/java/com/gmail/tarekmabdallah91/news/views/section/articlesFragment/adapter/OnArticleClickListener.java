package com.gmail.tarekmabdallah91.news.views.section.articlesFragment.adapter;

import com.gmail.tarekmabdallah91.news.models.articles.Article;

public interface OnArticleClickListener {
    /**
     * to open the details of the clicked article (by position)
     */
    void onClickArticle(Article article);

    /**
     * to open the section of the clicked article
     */
    void onClickArticleSection(Article article);
}
