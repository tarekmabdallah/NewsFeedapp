package com.example.tarek.news.views.section;

import android.content.Context;
import android.content.Intent;

import com.example.tarek.news.R;
import com.example.tarek.news.apis.APIClient;
import com.example.tarek.news.apis.APIServices;
import com.example.tarek.news.models.articles.Article;
import com.example.tarek.news.models.section.ResponseSection;
import com.example.tarek.news.views.bases.BaseActivity;

import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static com.example.tarek.news.apis.APIClient.getResponse;
import static com.example.tarek.news.utils.Constants.SECTION_KEYWORD;
import static com.example.tarek.news.utils.ViewsUtils.getQueriesMap;
import static com.example.tarek.news.views.articlesFragment.ArticlesFragment.setArticlesFragmentToCommit;

public class SectionActivity extends BaseActivity {

    private String sectionId;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_section;
    }

    @Override
    protected void initiateValues() {
        getComingIntent();
    }

    @Override
    protected void getComingIntent() {
        Intent comingIntent = getIntent();
        sectionId = comingIntent.getStringExtra(SECTION_KEYWORD);
    }

    protected void callAPi() {
        APIServices apiServices = APIClient.getInstance(this).create(APIServices.class);
        Map<String, Object> queries = getQueriesMap();
        Call<ResponseSection> getSectionArticles = apiServices.getSectionArticles(sectionId, queries);
        getResponse(getSectionArticles, this);
    }

    @Override
    protected void whenDataFetchedGetResponse(Object response) {

        if (response instanceof ResponseSection) {
            ResponseSection section = (ResponseSection) response;
            List<Article> articleList = section.getResponse().getItems();
            if (articleList != null && !articleList.isEmpty()) {
                setArticlesFragmentToCommit(getSupportFragmentManager(), R.id.fragment_articles_container, articleList);
            } else handleNoDataFromResponse();
        }
    }

    public static void openSectionActivity(Context context, String sectionId){
        Intent openSectionActivity = new Intent(context, SectionActivity.class);
        openSectionActivity.putExtra(SECTION_KEYWORD, sectionId);
        context.startActivity(openSectionActivity);
    }
}
