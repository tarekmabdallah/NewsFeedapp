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

package com.gmail.tarekmabdallah91.news.views.search;

import com.gmail.tarekmabdallah91.news.views.articlesFragment.ItemArticlesFragment;

import static com.gmail.tarekmabdallah91.news.utils.Constants.SEARCH_KEYWORD;

public class SearchFragmentItem extends ItemArticlesFragment implements onClickItemListener {
    // TODO: 5/15/2019  to keep data after rotating this screen

    @Override
    public void onClickItem(String searchKeyword) {
//        if (null != itemAdapter) itemAdapter.submitList(null);
       // super.setPagingViewModel(searchKeyword);
    }

//    @Override
    public String getSectionId() {
        return SEARCH_KEYWORD;
    }

    public static SearchFragmentItem getInstance() {
        return new SearchFragmentItem();
    }

//    @Override // empty to do nothing here till we want to call super func.
    protected void setPagingViewModel(String searchKeyword) {}

    @Override // not used here
    public void removeItem(String item) {}
}
