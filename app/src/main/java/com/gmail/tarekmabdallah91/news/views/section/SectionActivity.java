package com.gmail.tarekmabdallah91.news.views.section;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.apis.APIClient;
import com.gmail.tarekmabdallah91.news.data.room.news.DbViewModel;
import com.gmail.tarekmabdallah91.news.data.sp.SharedPreferencesHelper;
import com.gmail.tarekmabdallah91.news.models.articles.Article;
import com.gmail.tarekmabdallah91.news.utils.NetworkState;
import com.gmail.tarekmabdallah91.news.paging.ItemAdapter;
import com.gmail.tarekmabdallah91.news.paging.ItemViewModel;
import com.gmail.tarekmabdallah91.news.paging.OnArticleClickListener;
import com.gmail.tarekmabdallah91.news.views.bases.BaseActivity;

import java.util.List;

import butterknife.BindView;
import retrofit2.Retrofit;

import static com.gmail.tarekmabdallah91.news.utils.Constants.EMPTY_STRING;
import static com.gmail.tarekmabdallah91.news.utils.Constants.IS_COUNTRY_SECTION;
import static com.gmail.tarekmabdallah91.news.utils.Constants.IS_FAVOURITE_LIST;
import static com.gmail.tarekmabdallah91.news.utils.Constants.ONE;
import static com.gmail.tarekmabdallah91.news.utils.Constants.PAGE_SIZE;
import static com.gmail.tarekmabdallah91.news.utils.Constants.SCROLL_POSITION;
import static com.gmail.tarekmabdallah91.news.utils.Constants.SECTION_ID_KEYWORD;
import static com.gmail.tarekmabdallah91.news.utils.Constants.TEN;
import static com.gmail.tarekmabdallah91.news.utils.Constants.TITLE_KEYWORD;
import static com.gmail.tarekmabdallah91.news.utils.Constants.TWO;
import static com.gmail.tarekmabdallah91.news.utils.Constants.ZERO;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.handelErrorMsg;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.makeViewGone;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.makeViewVisible;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.restartActivity;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.showNormalProgressBar;
import static com.gmail.tarekmabdallah91.news.views.webViewActivity.WebViewActivity.openArticleWebViewActivity;

public class SectionActivity extends BaseActivity {

    @BindView(R.id.articles_recycler_view)
    protected RecyclerView articlesRecyclerView;

    private Retrofit retrofit;
    protected ItemAdapter itemAdapter;
    private int scrollPosition;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_articles;
    }

    @Override
    public void setUI() {
        SharedPreferencesHelper sharedPreferencesHelper = SharedPreferencesHelper.getInstance(this);
        boolean isSPUpdated = sharedPreferencesHelper.getIsSPUpdated();
        if (isSPUpdated) {
            sharedPreferencesHelper.saveIsSPUpdated(false);
            restartActivity(this);
        }
    }

    @Override
    protected String getActivityTitle(){
        Intent comingIntent = getIntent();
        return comingIntent.getStringExtra(TITLE_KEYWORD);
    }

    @Override
    public void initiateValues() {
        retrofit = APIClient.getInstance(this);
        setItemAdapter();
        setArticlesRecyclerView();
        //must set ViewModel here to recalled if the screen rotated
        if (IS_FAVOURITE_LIST.equals(getSectionId())) setDbViewModel();
        else setPagingViewModel(null);
    }



    @Override
    public void reSetActivityWithSaveInstanceState(Bundle savedInstanceState) {
        scrollPosition = savedInstanceState.getInt(SCROLL_POSITION);
        moveRecyclerViewToPosition(scrollPosition);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        int scrollingPosition = getRecyclerViewPosition();
        savedInstanceState.putInt (SCROLL_POSITION, scrollingPosition);
        super.onSaveInstanceState(savedInstanceState);
    }

    private int getRecyclerViewPosition (){
        int[] scrollPosition = new int[]{ZERO, ZERO};
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) articlesRecyclerView.getLayoutManager();
        if (null != linearLayoutManager) {
            int position = linearLayoutManager.findFirstVisibleItemPosition();
            View v = linearLayoutManager.getChildAt(position);
            int offset = (v == null) ? ZERO : (v.getTop() - linearLayoutManager.getPaddingTop());
            scrollPosition [ZERO] = position;
            scrollPosition [ONE] = offset;
            return position;
        }
        return ZERO;
    }

    /**
     * to scroll the recycler view to wanted position
     * https://stackoverflow.com/a/43505830/5055780
     */
    private void moveRecyclerViewToPosition (int scrollPosition){
        LinearLayoutManager layoutManager = (LinearLayoutManager) articlesRecyclerView.getLayoutManager();
        LinearSmoothScroller smoothScroller = new LinearSmoothScroller(this) {
            @Override protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
        smoothScroller.setTargetPosition(scrollPosition);
        if (null != layoutManager) layoutManager.startSmoothScroll(smoothScroller);
    }

    protected String getSectionId() {
        return getIntent().getStringExtra(SECTION_ID_KEYWORD);
    }

    protected void setPagingViewModel(String searchKeyword){
        makeViewVisible(progressBar);
        String sectionId = getSectionId();
        ItemViewModel itemViewModel =  new ItemViewModel(this, sectionId, searchKeyword, retrofit);
        itemViewModel.getItemPagedList().observe(this, new Observer<PagedList<Article>>() {
            @Override
            public void onChanged(@Nullable PagedList<Article> items) {
                itemAdapter.submitList(items);
            }
        });
        itemViewModel.getNetworkState().observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(@Nullable NetworkState networkState) {
                observeNetworkState(networkState);
                // must called here after the layout of progress bar is gone
                if (ZERO < scrollPosition) {
                    moveRecyclerViewToPosition(scrollPosition);
                    scrollPosition = ZERO;
                }
            }
        });
    }

    private void setDbViewModel() {
        // failed to inject observers
        DbViewModel viewModel = ViewModelProviders.of(this).get(DbViewModel.class);
        viewModel.getData().observe(this, new Observer<List<Article>>() {

            @Override
            public void onChanged(@Nullable List<Article> articlesInDb) {
                if (null != articlesInDb && !articlesInDb.isEmpty())
                    itemAdapter.swapList(articlesInDb);
                else {
                    observeNetworkState(new NetworkState(NetworkState.Status.FAILED,
                            getString(R.string.empty_db_msg)));
                    makeViewGone(progressBar);
                }
            }
        });
    }

    private void setItemAdapter(){
        itemAdapter = new ItemAdapter();
        itemAdapter.setOnArticleClickListener(new OnArticleClickListener() {
            @Override
            public void onClickArticle(Article article) {
                openArticleWebViewActivity(getBaseContext(), article);
            }

            @Override
            public void onClickArticleSection(Article article) {
                String sectionName = article.getSectionName();
                String activityTitle = getTitle().toString();
                if (!activityTitle.equals(sectionName))
                    openSectionActivity(getBaseContext(), article.getSectionId(), article.getSectionName(), false);
            }
        });
    }

    private void setArticlesRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        articlesRecyclerView.setLayoutManager(layoutManager);
        articlesRecyclerView.setHasFixedSize(true);
        articlesRecyclerView.setAdapter(itemAdapter);
    }

    private void observeNetworkState(@Nullable NetworkState networkState){
        itemAdapter.setNetworkState(networkState);
        boolean hasExtraRows = itemAdapter.hasExtraRow();
        showNormalProgressBar(progressBar, hasExtraRows);
        String errorMsg = EMPTY_STRING;
        if (null != networkState) errorMsg = networkState.getMsg();
        // when the page size is larger than the total size in the Guardian API >> change it to equal 1 and restart the activity
        final String HTTP_BAD_REQUEST = "HTTP 400 Bad Request";
        if (HTTP_BAD_REQUEST.equals(errorMsg)){
            PAGE_SIZE /= TWO ;
            restartActivity(this);
        }
        handelErrorMsg(networkState, errorLayout, progressBar, errorTV, errorIV);
    }

    public static void openSectionActivity(Context context, String sectionId, String sectionTitle, boolean isCountrySection){
        Intent openSectionActivity = new Intent(context, SectionActivity.class);
        openSectionActivity.putExtra(SECTION_ID_KEYWORD, sectionId);
        openSectionActivity.putExtra(TITLE_KEYWORD, sectionTitle);
        openSectionActivity.putExtra(IS_COUNTRY_SECTION, isCountrySection);
        context.startActivity(openSectionActivity);
    }
}
