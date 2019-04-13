package com.example.tarek.news.views.section.articlesFragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tarek.news.R;
import com.example.tarek.news.models.section.ResponseSection;
import com.example.tarek.news.models.section.articles.Article;
import com.example.tarek.news.views.bases.ArticleAdapter;
import com.example.tarek.news.views.bases.BaseDataLoaderFragment;

import java.util.List;

import butterknife.BindView;

import static com.example.tarek.news.views.webViewActivity.WebViewActivity.openWebViewActivityArticle;


public class ArticlesFragment extends BaseDataLoaderFragment {

    @BindView(R.id.list_view)
    ListView listView;

    protected ArticleAdapter adapter  ;
    protected List<Article> articles ;

    protected String sectionId;

    // TODO: 4/13/2019 use recyclerView instead of list view
    // TODO: 4/13/2019 create fragment to open the article with it's data (not the website's link)

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_articles;
    }

    @Override
    protected void initiateValues() {
        super.initiateValues();
        adapter = new ArticleAdapter(activity);
        setListView();
    }

    @Override
    public String getSectionId() {
        return sectionId;
    }

    private void setListView() {
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article article = articles.get(position);
                openWebViewActivityArticle(activity, article.getWebUrl());
            }
        });
    }

    @Override
    protected void whenDataFetchedGetResponse(Object response) {
        adapter.clear();
        if (response instanceof ResponseSection) {
            ResponseSection section = (ResponseSection) response;
            List<Article> articleList = section.getResponse().getItems();
            if (articleList != null && !articleList.isEmpty()) {
                adapter.addAll(articleList);
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
