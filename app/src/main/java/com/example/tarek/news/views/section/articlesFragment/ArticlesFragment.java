package com.example.tarek.news.views.section.articlesFragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.tarek.news.R;
import com.example.tarek.news.models.section.ResponseSection;
import com.example.tarek.news.models.section.articles.Article;
import com.example.tarek.news.views.bases.BaseDataLoaderFragment;
import com.example.tarek.news.views.section.articlesFragment.adapter.ArticleAdapter;
import com.example.tarek.news.views.section.articlesFragment.adapter.OnArticleClickListener;

import java.util.List;

import butterknife.BindView;

import static com.example.tarek.news.views.section.SectionActivity.openSectionActivity;
import static com.example.tarek.news.views.webViewActivity.WebViewActivity.openWebViewActivityArticle;


public class ArticlesFragment extends BaseDataLoaderFragment {

    @BindView(R.id.articles_recycler_view)
    RecyclerView articlesRecyclerView;

    protected ArticleAdapter adapter  ;
    protected List<Article> articles ;

    protected String sectionId;

    // TODO: 4/13/2019 create fragment to open the article with it's data (not the website's link)

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_articles;
    }

    @Override
    protected void initiateValues() {
        super.initiateValues();

        setArticlesRecyclerView();
    }

    @Override
    public String getSectionId() {
        return sectionId;
    }

    private void setArticlesRecyclerView() {
        OnArticleClickListener onArticleClickListener = new OnArticleClickListener() {

            @Override
            public void onClickArticle(int position) {
                Article article = articles.get(position);
                openWebViewActivityArticle(activity, article.getWebUrl());
            }

            @Override
            public void onClickArticleSection(int position) {
                Article article = articles.get(position);
                String sectionName = article.getSectionName();
                String activityTitle = activity.getTitle().toString();
                if (!activityTitle.equals(sectionName))
                    openSectionActivity(activity, article.getSectionId(), article.getSectionName());
            }
        };
        adapter = new ArticleAdapter(activity);
        adapter.setOnArticleClickListener(onArticleClickListener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        articlesRecyclerView.setLayoutManager(layoutManager);
        articlesRecyclerView.setHasFixedSize(true);
        articlesRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void whenDataFetchedGetResponse(Object response) {
        if (response instanceof ResponseSection) {
            ResponseSection section = (ResponseSection) response;
            List<Article> articleList = section.getResponse().getItems();
            if (articleList != null && !articleList.isEmpty()) {
                adapter.swapList(articleList);
            } else handleNoDataFromResponse();
        }
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public static ArticlesFragment getInstance(String sectionId) {
        ArticlesFragment fragment =  new ArticlesFragment();
        fragment.setSectionId(sectionId);
        return fragment;
    }
}
