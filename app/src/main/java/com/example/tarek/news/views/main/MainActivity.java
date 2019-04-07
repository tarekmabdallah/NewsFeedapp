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

package com.example.tarek.news.views.main;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tarek.news.R;
import com.example.tarek.news.apis.APIClient;
import com.example.tarek.news.apis.APIServices;
import com.example.tarek.news.apis.DataFetcherCallback;
import com.example.tarek.news.models.Search.Article;
import com.example.tarek.news.models.Search.ResponseSearchForKeyWord;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

import static com.example.tarek.news.apis.APIClient.getResponse;
import static com.example.tarek.news.utils.Constants.ACTIVITY_NOT_FOUND_EXCEPTION;
import static com.example.tarek.news.utils.Constants.API_KEY;
import static com.example.tarek.news.utils.ViewsUtils.isConnected;
import static com.example.tarek.news.utils.ViewsUtils.makeViewGone;
import static com.example.tarek.news.utils.ViewsUtils.makeViewVisible;
import static com.example.tarek.news.utils.ViewsUtils.printLogException;
import static com.example.tarek.news.utils.ViewsUtils.showFailureMsg;
import static com.example.tarek.news.utils.ViewsUtils.showProgressBar;
import static com.example.tarek.news.utils.ViewsUtils.showShortToastMsg;

public class MainActivity extends AppCompatActivity implements DataFetcherCallback {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ArticleArrayAdapter adapter  ;

    @BindView(R.id.list)
    ListView listView;
    @BindView(R.id.no_article_text_view)
    TextView emptyTextView;
    @BindView(R.id.no_article_image)
    ImageView image;
    @BindView(R.id.progressBar)
    View progressBar;

    // TODO: 07-Apr-19 to save some articles using Room db 
    // TODO: 07-Apr-19 to use paging  
    // TODO: 07-Apr-19 to use shared preferences to save some data  
    // TODO: 07-Apr-19 to use Fb sdk for logging  
    // TODO: 07-Apr-19 to use firebase for crash analytics and messing 
    // TODO: 07-Apr-19 to use the rest APIs 
    // TODO: 07-Apr-19 to use dragger 2 and RxJava
    // TODO: 07-Apr-19 to update the UI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initiateValues();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUI();
    }

    private void initiateValues() {
        adapter = new ArticleArrayAdapter(this, new ArrayList<Article>());
        listView.setEmptyView(emptyTextView);
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
                    printLogException(TAG, e, ACTIVITY_NOT_FOUND_EXCEPTION);
                }
            }
        });
    }

    private void setUI(){
        if (isConnected(this)) {
            showProgressBar(progressBar, true);
            callAPi();
        } else {
            showProgressBar(progressBar, false);
            adapter.clear(); // if there was sny data clear them to clear the background 
            // TODO: 07-Apr-19 to get vectors instead of png
            setEmptyMsgView(R.drawable.icons8_disconnected, getString(R.string.no_connection));
            View.OnClickListener onClickNoConnectionListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // re call this method again
                    setUI();
                    makeViewGone(image);
                }
            };
            image.setOnClickListener(onClickNoConnectionListener);
            emptyTextView.setOnClickListener(onClickNoConnectionListener);
        }
    }

    private void callAPi() {
        APIServices apiServices = APIClient.getInstance(this).create(APIServices.class);
        Call<ResponseSearchForKeyWord> searchForKeyWord = apiServices.searchForKeyword("salah", API_KEY);
        getResponse(searchForKeyWord, this);
    }

    @Override
    public void onDataFetched(Response response) {
        showProgressBar(progressBar, false);
        adapter.clear();
        // check if there are articles then show them
        if (response.body() instanceof ResponseSearchForKeyWord) {
            ResponseSearchForKeyWord responseSearchForKeyWord = (ResponseSearchForKeyWord) response.body();
            List<Article> articleList = responseSearchForKeyWord.getResponse().getItems();
            if (articleList != null && !articleList.isEmpty()) {
                adapter.addAll(articleList);
            } else setEmptyMsgView(R.drawable.icons8_empty_box, getString(R.string.no_news_found));
        }
    }

    private void setEmptyMsgView(int imageResIntId, String msg) {
        makeViewVisible(image);
        image.setImageResource(imageResIntId);
        emptyTextView.setText(msg);
    }

    @Override
    public void onFailure(Throwable t) {
        showFailureMsg(progressBar, null, t);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_settings) {
//            Intent intentToSettings = new Intent(MainActivity.this , SettingsActivity.class);
//            startActivity(intentToSettings);
            showShortToastMsg(this, "sorry, no ready now");
        }
        return true;
    }
}
