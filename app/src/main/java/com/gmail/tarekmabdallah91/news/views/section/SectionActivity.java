package com.gmail.tarekmabdallah91.news.views.section;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.views.bases.BaseActivity;
import com.gmail.tarekmabdallah91.news.views.bases.BasePresenter;

import butterknife.BindView;

import static com.gmail.tarekmabdallah91.news.utils.Constants.IS_COUNTRY_SECTION;
import static com.gmail.tarekmabdallah91.news.utils.Constants.SECTION_ID_KEYWORD;
import static com.gmail.tarekmabdallah91.news.utils.Constants.TITLE_KEYWORD;

public class SectionActivity extends BaseActivity {

    @BindView(R.id.articles_recycler_view)
    protected RecyclerView articlesRecyclerView;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_articles;
    }

    @Override
    protected BasePresenter getPresenter() {
        return SectionPresenter.getInstance();
    }

    @Override
    protected String getActivityTitle(){
        return presenter.getActivityTitle(this);
    }

    @Override
    public void initiateValues () {
        presenter.initiateValues(this, errorLayout, progressBar, errorTV, errorIV, articlesRecyclerView);
    }

    @Override
    public void reSetActivityWithSaveInstanceState(Bundle savedInstanceState) {
        presenter.reSetActivityWithSaveInstanceState(savedInstanceState, articlesRecyclerView);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        presenter.onSaveInstanceState(savedInstanceState, articlesRecyclerView);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void setUI() {
        presenter.setUI(this);
    }

    public static void openSectionActivity(Context context, String sectionId, String sectionTitle, boolean isCountrySection){
        Intent openSectionActivity = new Intent(context, SectionActivity.class);
        openSectionActivity.putExtra(SECTION_ID_KEYWORD, sectionId);
        openSectionActivity.putExtra(TITLE_KEYWORD, sectionTitle);
        openSectionActivity.putExtra(IS_COUNTRY_SECTION, isCountrySection);
        context.startActivity(openSectionActivity);
    }
}
