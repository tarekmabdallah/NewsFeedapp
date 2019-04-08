/*
 * Copyright 2019 tarekmabdallah91@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.tarek.news.views.search;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.tarek.news.R;
import com.example.tarek.news.apis.APIClient;
import com.example.tarek.news.apis.APIServices;
import com.example.tarek.news.models.Search.Article;
import com.example.tarek.news.models.Search.ResponseSearchForKeyWord;
import com.example.tarek.news.views.bases.ArticleArrayAdapter;
import com.example.tarek.news.views.bases.BaseActivityNoMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import retrofit2.Call;

import static com.example.tarek.news.apis.APIClient.getResponse;
import static com.example.tarek.news.utils.Constants.QUERY_Q_KEYWORD;
import static com.example.tarek.news.utils.ViewsUtils.getQueriesMap;
import static com.example.tarek.news.utils.ViewsUtils.isValidString;

public class SearchActivity extends BaseActivityNoMenu {

    private ArticleArrayAdapter adapter;

    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.search_view)
    SearchView searchView;

    private Map<String, String> queries;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initiateValues() {
        queries = getQueriesMap();
        setListView();
        setSearchView();
        setEnterSoftKeyboardListener();
    }

    @Override
    protected void setUI() {
        String searchKeyword = getSearchKeyword();
        if (isValidString(searchKeyword)) super.setUI();
    }

    private void setListView() {
        adapter = new ArticleArrayAdapter(this, new ArrayList<Article>());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article article = adapter.getItem(position);
                try {
                    Intent openWebPage;
                    if (article != null) {
                        openWebPage = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getWebUrl()));
                        startActivity(openWebPage);
                    }
                } catch (ActivityNotFoundException e) {
                    showToastMsg(getString(R.string.no_browser_msg));
                }
            }
        });
    }

    public void setSearchView() {
        // TODO: 08-Apr-19 to save last 5 search words in shared preferences
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /**
             * called when the user clicked on the search Icon in the keyboard
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchForKeyWord(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchForKeyWord(newText);
                return false;
            }
        });
    }

    /**
     * to call searchForKeyWord method when the search icon in the keyboard is clicked
     */
    private void setEnterSoftKeyboardListener() {
        searchView.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            String searchKeyword = getSearchKeyword();
                            searchForKeyWord(searchKeyword);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
    }

    /**
     * set queries params with the @param searchKeyword
     */
    private void searchForKeyWord(String searchKeyword) {
        adapter.clear();
        if (isValidString(searchKeyword)) {
            queries.put(QUERY_Q_KEYWORD, searchKeyword);
            callAPi();
        }
    }

    private String getSearchKeyword() {
        return searchView.getQuery().toString();
    }

    protected void callAPi() {
        APIServices apiServices = APIClient.getInstance(this).create(APIServices.class);
        Call<ResponseSearchForKeyWord> searchForKeyWord = apiServices.searchForKeyword(queries);
        getResponse(searchForKeyWord, this);
    }

    @Override
    protected void whenDataFetchedGetResponse(Object response) {
        adapter.clear();
        if (response instanceof ResponseSearchForKeyWord) {
            ResponseSearchForKeyWord responseSearchForKeyWord = (ResponseSearchForKeyWord) response;
            List<Article> articleList = responseSearchForKeyWord.getResponse().getItems();
            if (articleList != null && !articleList.isEmpty()) {
                adapter.addAll(articleList);
            } else handleNoDataFromResponse();
        }
    }

    public static Intent openSearchActivity(Context context) {
        return new Intent(context, SearchActivity.class);
    }

}
