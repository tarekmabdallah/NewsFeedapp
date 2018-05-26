/*
Copyright 2018 tarekmabdallah91@gmail.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.example.tarek.newsfeedapp.activities;

import android.app.LoaderManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tarek.newsfeedapp.R;
import com.example.tarek.newsfeedapp.adpater.ArticleArrayAdapter;
import com.example.tarek.newsfeedapp.article.Article;
import com.example.tarek.newsfeedapp.loader.ArticleLoader;
import com.example.tarek.newsfeedapp.utils.ArticleQueryUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String QUERY_SECTION_TAG = "section";
    private static final String QUERY_DATE_TAG = "from-date";
    private static final String QUERY_CONTRIBUTOR_TAG = "show-tags";
    private static final String CONTRIBUTOR_VALUE = "contributor";
    private static final String QUERY_API_KEY_TAG = "api-key";
    private static final String API_KEY_VALUE = "test";
    private static final String URL_API = "https://content.guardianapis.com/search";
    private static final String ACTIVITY_NOT_FOUND_EXCEPTION = "ActivityNotFoundException";
    private static final int LOADER_ID = 1;
    private ArticleArrayAdapter adapter  ;

    @BindView(R.id.list)
    ListView listView;
    @BindView(R.id.no_article_text_view)
    TextView emptyTextView;
    @BindView(R.id.no_article_image)
    ImageView image;
    @BindView(R.id.indicator)
    View progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // set UI
        setUI();

        ConnectivityManager connectivityManager =
                (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

        if(isConnected){
            // initiate  loader
            getLoaderManager().initLoader(LOADER_ID ,null ,this); // .forceLoad();
        }else {
            progressBar.setVisibility(View.GONE);
            image.setVisibility(View.VISIBLE);
            image.setImageResource(R.drawable.icons8_disconnected);
            emptyTextView.setText(getText(R.string.empty_text));
        }
    }

    private void setUI(){

        adapter = new ArticleArrayAdapter(this, new ArrayList<Article>());
        listView.setEmptyView(emptyTextView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article article = adapter.getItem(position);
                view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                try {
                    Intent openWebPage = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
                    startActivity(openWebPage);
                }catch (ActivityNotFoundException e) {
                    ArticleQueryUtils.printLogException(TAG, e, ACTIVITY_NOT_FOUND_EXCEPTION);
                }
            }
        });
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String section = preferences.getString(
                getString(R.string.tag_list_key),getString(R.string.tag_list_default_value));


        String date = preferences.getString(
                getString(R.string.date_list_key),getString(R.string.date_list_default_value));

        Uri uri = Uri.parse(URL_API);
        Uri.Builder builder = uri.buildUpon();

        builder.appendQueryParameter(QUERY_SECTION_TAG, section);
        builder.appendQueryParameter(QUERY_CONTRIBUTOR_TAG, CONTRIBUTOR_VALUE);
        builder.appendQueryParameter(QUERY_DATE_TAG, date);
        builder.appendQueryParameter(QUERY_API_KEY_TAG, API_KEY_VALUE);

        // execute ArticleLoader
        return new ArticleLoader(this , builder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> articleList) {
        // clear adapter
        progressBar.setVisibility(View.GONE);

        adapter.clear();
        // check if there are articles then show them
        if (articleList != null && !articleList.isEmpty()){
            adapter.addAll(articleList);
        } else {
            image.setVisibility(View.VISIBLE);
            image.setImageResource(R.drawable.icons8_empty_box);
            emptyTextView.setText(getString(R.string.no_news_found));
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        // clear adapter
        adapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_settings){
            Intent intentToSettings = new Intent(MainActivity.this , SettingsActivity.class);
            startActivity(intentToSettings);
        }
        return true;
    }


}
