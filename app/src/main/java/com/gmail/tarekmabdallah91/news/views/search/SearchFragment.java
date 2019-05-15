package com.gmail.tarekmabdallah91.news.views.search;

import com.gmail.tarekmabdallah91.news.views.section.articlesFragment.ArticlesFragment;

import static com.gmail.tarekmabdallah91.news.utils.Constants.SEARCH_KEYWORD;

public class SearchFragment extends ArticlesFragment implements onClickItemListener {
    // TODO: 5/15/2019  to keep data after rotating this screen
    @Override // empty to do nothing here till we want to call super func.
    protected void setPagingViewModel(String searchKeyword) {}

    @Override
    public void onClickItem(String searchKeyword) {
        if (null != itemAdapter) itemAdapter.clear();
        super.setPagingViewModel(searchKeyword);
    }

    @Override
    public String getSectionId() {
        return SEARCH_KEYWORD;
    }

    public static SearchFragment getInstance() {
        return new SearchFragment();
    }

    @Override // not used here
    public void removeItem(String item) {}
}
