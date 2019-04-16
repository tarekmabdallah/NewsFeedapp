package com.gmail.tarekmabdallah91.news.views.section.articlesFragment.adapter;

public interface OnArticleClickListener {
    /**
     * to open the details of the clicked article (by position)
     */
    void onClickArticle(int position);

    /**
     * to open the section of the clicked article
     */
    void onClickArticleSection(int position);
}
