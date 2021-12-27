/*
 *
 * Copyright 2019 tarekmabdallah91@gmail.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.gmail.tarekmabdallah91.news.views.articlesFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.paging.ReloadLayoutListener;
import com.gmail.tarekmabdallah91.news.views.bases.BaseFragment;

import butterknife.BindView;

import static com.gmail.tarekmabdallah91.news.utils.Constants.SECTION_ID_KEYWORD;


public class ArticlesFragment extends BaseFragment {

    @BindView(R.id.articles_recycler_view)
    protected RecyclerView articlesRecyclerView;

    protected ArticleFragmentPresenter articleFragmentPresenter;
    protected String sectionId;
    protected ReloadLayoutListener reloadLayoutListener;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_articles;
    }

    @Override
    public void initiateValues() {
        setArticleFragmentPresenter ();
        showCurrentFragment();
    }

    @Override
    public void reSetActivityWithSaveInstanceState(Bundle savedInstanceState) {
        articleFragmentPresenter.reSetActivityWithSaveInstanceState(savedInstanceState, articlesRecyclerView);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        articleFragmentPresenter.onSaveInstanceState(savedInstanceState, articlesRecyclerView);
        super.onSaveInstanceState(savedInstanceState);
    }

    protected void setArticleFragmentPresenter (){
        articleFragmentPresenter = ArticleFragmentPresenter.getInstance();
        articleFragmentPresenter.setReloadLayoutListener(reloadLayoutListener);
        Bundle args = getArguments();
        if (null != args){
            sectionId = args.getString(SECTION_ID_KEYWORD);
        }
    }

    protected void showCurrentFragment(){
        // MUST USED HERE
        if (null != sectionId) activity.getIntent().putExtra(SECTION_ID_KEYWORD, sectionId);
        articleFragmentPresenter.initiateValues(activity, errorLayout, progressBar, errorTV, errorIV, articlesRecyclerView);
    }

    protected void setReloadLayoutListener(ReloadLayoutListener reloadLayoutListener) {
        this.reloadLayoutListener = reloadLayoutListener;
    }

    public static ArticlesFragment newInstance(String sectionId, ReloadLayoutListener reloadLayoutListener) {
        Bundle args = new Bundle();
        args.putString(SECTION_ID_KEYWORD, sectionId);
        ArticlesFragment fragment = new ArticlesFragment();
        fragment.setArguments(args);
        fragment.setReloadLayoutListener(reloadLayoutListener);
        return fragment;
    }

}