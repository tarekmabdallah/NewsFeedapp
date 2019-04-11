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
import static com.example.tarek.news.utils.Constants.TITLE_KEYWORD;
import static com.example.tarek.news.utils.ViewsUtils.getQueriesMap;
import static com.example.tarek.news.views.articlesFragment.ArticlesFragment.setArticlesFragmentToCommit;

public class SectionActivity extends BaseActivity {

    protected APIServices apiServices;
    protected Map<String, Object> queries;
    protected Call call;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_section;
    }

    @Override
    protected void initiateValues() {
        apiServices = APIClient.getInstance(this).create(APIServices.class);
        queries = getQueriesMap();
        call = getCall();
        setTitle(getSectionTitle());
    }

    /**
     * to @return value from intent by @param key
     */
    protected Object getValueFromIntent(String key) {
        Intent comingIntent = getIntent();
        return comingIntent.getStringExtra(key);
    }

    protected String getSectionId(){
        return String.valueOf(getValueFromIntent(SECTION_KEYWORD));
    }

    protected String getSectionTitle(){
        return String.valueOf(getValueFromIntent(TITLE_KEYWORD));
    }

    protected Call getCall(){
        return apiServices.getSectionArticles(getSectionId(), queries);
    }

    protected void callAPi() {
        getResponse(call, this);
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

    public static void openSectionActivity(Context context, String sectionId, String sectionTitle){
        Intent openSectionActivity = new Intent(context, SectionActivity.class);
        openSectionActivity.putExtra(SECTION_KEYWORD, sectionId);
        openSectionActivity.putExtra(TITLE_KEYWORD, sectionTitle);
        context.startActivity(openSectionActivity);
    }
}
