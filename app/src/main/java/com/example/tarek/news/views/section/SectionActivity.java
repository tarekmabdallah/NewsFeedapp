package com.example.tarek.news.views.section;

import android.content.Context;
import android.content.Intent;

import com.example.tarek.news.R;
import com.example.tarek.news.data.sp.SharedPreferencesHelper;
import com.example.tarek.news.views.bases.BaseActivity;
import com.example.tarek.news.views.section.articlesFragment.ArticlesFragment;

import static com.example.tarek.news.utils.Constants.SECTION_KEYWORD;
import static com.example.tarek.news.utils.Constants.TITLE_KEYWORD;
import static com.example.tarek.news.utils.ViewsUtils.restartActivity;

public class SectionActivity extends BaseActivity {

    protected ArticlesFragment fragment;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_section;
    }

    @Override
    protected void initiateValues() {
        fragment = ArticlesFragment.getInstance(getSectionId());
    }

    @Override
    protected void setActivityWhenSaveInstanceStateNull() {
        setFragmentToCommit(fragment, R.id.fragment_articles_container);
    }

    @Override
    protected void setUI() {
        SharedPreferencesHelper sharedPreferencesHelper = SharedPreferencesHelper.getInstance(this);
        boolean isSPUpdated = sharedPreferencesHelper.getIsSPUpdated();
        if (isSPUpdated) {
            sharedPreferencesHelper.saveIsSPUpdated(false);
            restartActivity(this);
        }
    }

    protected String getSectionId(){
        return String.valueOf(getValueFromIntent(SECTION_KEYWORD));
    }

    @Override
    protected String getSectionTitle(){
        return String.valueOf(getValueFromIntent(TITLE_KEYWORD));
    }

    public static void openSectionActivity(Context context, String sectionId, String sectionTitle){
        Intent openSectionActivity = new Intent(context, SectionActivity.class);
        openSectionActivity.putExtra(SECTION_KEYWORD, sectionId);
        openSectionActivity.putExtra(TITLE_KEYWORD, sectionTitle);
        context.startActivity(openSectionActivity);
    }
}
