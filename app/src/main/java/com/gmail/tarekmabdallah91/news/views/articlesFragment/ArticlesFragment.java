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
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.paging.reloadLayoutListener;
import com.gmail.tarekmabdallah91.news.views.bases.BaseFragment;

import butterknife.BindView;

import static com.gmail.tarekmabdallah91.news.utils.Constants.IS_LOADED_BEFORE;
import static com.gmail.tarekmabdallah91.news.utils.Constants.ONE;
import static com.gmail.tarekmabdallah91.news.utils.Constants.SCROLL_POSITION;
import static com.gmail.tarekmabdallah91.news.utils.Constants.SECTION_ID_KEYWORD;

public class ArticlesFragment extends BaseFragment {

    @BindView(R.id.articles_recycler_view)
    RecyclerView articlesRecyclerView;
    // TODO: 8/22/2019 after rotation the view pager skip the 1st fragment (after the current position) not loaded
    private ArticleFragmentPresenter articleFragmentPresenter;
    private String sectionId;
    private reloadLayoutListener reloadLayoutListener;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_articles;
    }

    @Override
    public void initiateValues() {
        articleFragmentPresenter = ArticleFragmentPresenter.getInstance();
        articleFragmentPresenter.setReloadLayoutListener(reloadLayoutListener);
        Bundle args = getArguments();
        if (null != args){
            sectionId = args.getString(SECTION_ID_KEYWORD);
        }
    }

    /**
     * this method is not part of the fragment lifecycle
     * it used here to load only the visible fragment after be sure that the fragment is attached (by the delay of the handler)
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // handler needed to wait sometime till the fragment be completely VisibleToUser
            // TO load pages when the user swipe taps (not when the user swipe between fragments)
            final Bundle args = getArguments();
            if (null != args && !args.containsKey(IS_LOADED_BEFORE)){
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        args.putBoolean(IS_LOADED_BEFORE, true); // to prevent loading it again when be visible
                        setArguments(args);
                        showCurrentFragment();
                    }
                }, ONE);
            }
        }
    }

    private void showCurrentFragment(){
        // MUST USED HERE
        if (null != sectionId) activity.getIntent().putExtra(SECTION_ID_KEYWORD, sectionId);
        articleFragmentPresenter.initiateValues(activity, errorLayout, progressBar, errorTV, errorIV, articlesRecyclerView);
    }

    @Override
    public void reSetActivityWithSaveInstanceState(Bundle savedInstanceState) {
//        articleFragmentPresenter.reSetActivityWithSaveInstanceState(savedInstanceState, articlesRecyclerView);
//        sectionId = activity.getIntent().getStringExtra(SECTION_ID_KEYWORD);
//        Log.d(SCROLL_POSITION + " Bundle", position + " " + sectionId);
        int scrollPosition = savedInstanceState.getInt(SCROLL_POSITION);
        articleFragmentPresenter.moveRecyclerViewToPosition(articlesRecyclerView, scrollPosition);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
//        articleFragmentPresenter = ArticleFragmentPresenter.getInstance();
//        articleFragmentPresenter.onSaveInstanceState(savedInstanceState, articlesRecyclerView);
        if (null != articleFragmentPresenter && null != articlesRecyclerView) {
            int scrollingPosition = articleFragmentPresenter.getRecyclerViewPosition(articlesRecyclerView);
            savedInstanceState.putInt(SCROLL_POSITION, scrollingPosition);
        }
        super.onSaveInstanceState(savedInstanceState);
    }

    public void setReloadLayoutListener(reloadLayoutListener reloadLayoutListener) {
        this.reloadLayoutListener = reloadLayoutListener;
    }

    public static ArticlesFragment newInstance(String sectionId, reloadLayoutListener reloadLayoutListener) {
        Bundle args = new Bundle();
        args.putString(SECTION_ID_KEYWORD, sectionId);
        ArticlesFragment fragment = new ArticlesFragment();
        fragment.setArguments(args);
        fragment.setReloadLayoutListener(reloadLayoutListener);
        return fragment;
    }
}