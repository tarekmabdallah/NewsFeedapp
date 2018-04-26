package com.example.tarek.newsfeedapp.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.tarek.newsfeedapp.article.Article;
import com.example.tarek.newsfeedapp.utils.ArticleQueryUtils;

import java.util.List;

public class ArticleLoader extends AsyncTaskLoader<List<Article>> {

    // --Commented out by Inspection (26/04/2018 08:52 ص):private static final String TAG = ArticleLoader.class.getSimpleName();
    private final String url;

    public ArticleLoader(Context context , String url ) {
        super(context);
        this.url = url;
    }

    @Override
    public List<Article> loadInBackground() {
        return ArticleQueryUtils.fetchArticlesData(url);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

}
