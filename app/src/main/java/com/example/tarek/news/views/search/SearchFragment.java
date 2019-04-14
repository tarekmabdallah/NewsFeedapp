package com.example.tarek.news.views.search;

import com.example.tarek.news.views.section.articlesFragment.ArticlesFragment;

import static com.example.tarek.news.utils.Constants.QUERY_Q_KEYWORD;

public class SearchFragment extends ArticlesFragment implements onClickItemListener {

    @Override
    public void onClickItem(String searchKeyword) {
        if (null != adapter) adapter.clear();
        if (null != queries) {
            queries.put(QUERY_Q_KEYWORD, searchKeyword);
            loadData();
        }
    }

    public static SearchFragment getInstance(String sectionId) {
        SearchFragment fragment =  new SearchFragment();
        fragment.setSectionId(sectionId);
        return fragment;
    }

    @Override // not used here
    public void removeItem(String item) {

    }
}
