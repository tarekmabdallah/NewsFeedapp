package com.example.tarek.newsfeedapp.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.tarek.newsfeedapp.article.Article;
import com.example.tarek.newsfeedapp.utils.ArticleQueryUtils;

import java.util.List;

public class ArticleLoader extends AsyncTaskLoader<List<Article>> {

    private static final String TAG = ArticleLoader.class.getSimpleName();
    private String url ;

    public ArticleLoader(Context context , String url ) {
        super(context);
        this.url = url;
        ArticleQueryUtils.context = context;
    }

    @Override
    public List<Article> loadInBackground() {
        List<Article> articles = ArticleQueryUtils.fetchArticlesData(url);
        return articles;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

}
