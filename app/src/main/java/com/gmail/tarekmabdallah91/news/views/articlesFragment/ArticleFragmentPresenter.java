package com.gmail.tarekmabdallah91.news.views.articlesFragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.views.section.SectionPresenter;

public class ArticleFragmentPresenter extends SectionPresenter {

    private String sectionId;
    private String[] sectionsIds;
//    private int position;

    private static ArticleFragmentPresenter articleFragmentPresenter;

    public static ArticleFragmentPresenter getInstance() {
        if (null == articleFragmentPresenter) articleFragmentPresenter = new ArticleFragmentPresenter();
        return articleFragmentPresenter;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

//    /**
//     * @param activity not used here as section id is a field in this class and not stored in the intent
//     * @return SectionId
//     */
//    @Override
//    public String getSectionId(Activity activity) {
//        return sectionId;
//    }

    @Override
    public void reSetActivityWithSaveInstanceState(Bundle savedInstanceState, RecyclerView articlesRecyclerView) {
        super.reSetActivityWithSaveInstanceState(savedInstanceState, articlesRecyclerView);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState, RecyclerView articlesRecyclerView) {
        super.onSaveInstanceState(savedInstanceState, articlesRecyclerView); // saved scrolling article position
//        savedInstanceState.putInt(SCROLL_POSITION, position);
    }

    String getSectionId(int position) {
        sectionId = sectionsIds[position];
        return sectionId;
    }

//    public void setPosition(int position) {
//        this.position = position;
//    }

    void setSectionsIds(Activity activity) {
        this.sectionsIds = activity.getResources().getStringArray(R.array.sections_ids);
    }

//    public int getPosition() {
//        return position;
//    }
}
