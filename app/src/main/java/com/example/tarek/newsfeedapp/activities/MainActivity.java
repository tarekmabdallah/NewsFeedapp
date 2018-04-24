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
import android.widget.ListView;
import android.widget.TextView;

import com.example.tarek.newsfeedapp.R;
import com.example.tarek.newsfeedapp.adpater.ArticleArrayAdapter;
import com.example.tarek.newsfeedapp.article.Article;
import com.example.tarek.newsfeedapp.loader.ArticleLoader;
import com.example.tarek.newsfeedapp.utils.ArticleQueryUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String QUERY_SEARCH_TAG = "q";
    private static final String QUERY_SECTION_TAG = "section";
    private static final String QUERY_DATE_TAG = "from-date";
    private static final String QUERY_CONTRIBUTOR_TAG = "show-tags";
    private static final String CONTRIBUTOR_VALUE = "contributor";
    private static final String QUERY_API_KEY_TAG = "api-key";
    private static final String API_KEY_VALUE = "test";
    private static final String URL_API = "https://content.guardianapis.com/search";
    private static final String ACTIVITY_NOT_FOUND_EXCEPTION = "ActivityNotFoundException";
    private static final int LOADER_ID = 1;
    private TextView emptyTextView;
    private View progressBar ;
    private ArticleArrayAdapter adapter  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            emptyTextView.setText(getText(R.string.empty_text));
            progressBar.setVisibility(View.GONE);
        }

    }

    private void setUI(){
        ListView listView = findViewById(R.id.list);
        emptyTextView = findViewById(R.id.empty_text_view);
        progressBar = findViewById(R.id.indicator);
        adapter = new ArticleArrayAdapter(this, new ArrayList<Article>());
        listView.setEmptyView(emptyTextView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article article = adapter.getItem(position);
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

        String searched = preferences.getString(
                getString(R.string.search_key),getString(R.string.search_default_value));

        Uri uri = Uri.parse(URL_API);
        Uri.Builder builder = uri.buildUpon();

        builder.appendQueryParameter(QUERY_SEARCH_TAG, searched);
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
