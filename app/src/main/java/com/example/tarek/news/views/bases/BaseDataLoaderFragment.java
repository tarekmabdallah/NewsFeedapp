package com.example.tarek.news.views.bases;

import com.example.tarek.news.R;
import com.example.tarek.news.apis.APIClient;
import com.example.tarek.news.apis.APIServices;
import com.example.tarek.news.apis.DataFetcherCallback;

import java.util.Map;

import retrofit2.Call;

import static com.example.tarek.news.apis.APIClient.getResponse;
import static com.example.tarek.news.utils.Constants.ZERO;
import static com.example.tarek.news.utils.ViewsUtils.getQueriesMap;
import static com.example.tarek.news.utils.ViewsUtils.isConnected;
import static com.example.tarek.news.utils.ViewsUtils.makeViewGone;
import static com.example.tarek.news.utils.ViewsUtils.showFailureMsg;
import static com.example.tarek.news.utils.ViewsUtils.showProgressBar;

public abstract class BaseDataLoaderFragment extends BaseFragment implements DataFetcherCallback {

    protected APIServices apiServices;
    protected Map<String, Object> queries;
    protected Call call;

    @Override
    protected void initiateValues() {
        apiServices = APIClient.getInstance(activity).create(APIServices.class);
        queries = getQueriesMap(activity);
    }

    @Override
    protected void initiateValuesAfterCheckSaveInstanceState() {
        call = getCall();
    }

    @Override
    protected void setUI() {
        if (ZERO >= getAdapterCount()){// to check if the data loaded before so don't reload it again onResume (after return back to the activity/fragment)
            if (isConnected(activity)) loadData();
            else handleCaseNoConnection();
        }
    }

    protected abstract int getAdapterCount();

    protected Call getCall(){
        return apiServices.getArticlesBySection(getSectionId(), queries);
    }

    protected abstract String getSectionId();

    protected void callAPi() {
        getResponse(call, this);
    }

    /**
     * to call the api after showing the progressbar
     * can be called when reloading data is needed
     */
    protected void loadData() {
        showProgressBar(progressBar, true);
        callAPi();
        makeViewGone(errorLayout);
    }

    /**
     * called after hiding the loading indicator (progress bar)
     */
    protected void whenDataFetchedGetResponse(Object response) {

    }

    @Override
    public void onDataFetched(Object response) {
        showProgressBar(progressBar, false);
        makeViewGone(errorLayout); // to hide if the data reloaded after it was empty
        whenDataFetchedGetResponse(response);
    }

    @Override
    public void onFailure(Throwable t, int errorImageResId) {
        showFailureMsg(t, errorImageResId, errorLayout, progressBar, errorTV, errorIV);
    }

    /**
     * called when there response has empty list
     */
    protected void handleNoDataFromResponse() {
        Throwable noDataThrowable = new Throwable(getString(R.string.no_news_found));
        onFailure(noDataThrowable, R.drawable.icons8_empty_box);
    }

    /**
     * if there is not internet connection before calling the api and refresh after 1 second
     */
    protected void handleCaseNoConnection() {
        Throwable noInternetConnectionThrowable = new Throwable(getString(R.string.no_connection));
        onFailure(noInternetConnectionThrowable, android.R.drawable.stat_notify_sync);
    }
}
