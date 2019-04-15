package com.example.tarek.news.views.search;

import com.example.tarek.news.views.section.articlesFragment.ArticlesFragment;

import static com.example.tarek.news.utils.Constants.QUERY_Q_KEYWORD;
import static com.example.tarek.news.utils.Constants.SEARCH_KEYWORD;

public class SearchFragment extends ArticlesFragment implements onClickItemListener {

    @Override
    public void onClickItem(String searchKeyword) {
        if (null != adapter) adapter.clear();
        if (null != queries) {
            queries.put(QUERY_Q_KEYWORD, searchKeyword);
            loadData();
        }
    }

    @Override
    public String getSectionId() {
        return SEARCH_KEYWORD;
    }

    public static SearchFragment getInstance() {
        return new SearchFragment();
    }

    @Override // not used here
    public void removeItem(String item) {

    }
}
