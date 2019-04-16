package com.gmail.tarekmabdallah91.news.views.bases;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.apis.APIClient;
import com.gmail.tarekmabdallah91.news.apis.APIServices;
import com.gmail.tarekmabdallah91.news.apis.DataFetcherCallback;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

import static com.gmail.tarekmabdallah91.news.apis.APIClient.getResponse;
import static com.gmail.tarekmabdallah91.news.utils.Constants.ZERO;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.getQueriesMap;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.getTextFromEditText;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.isConnected;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.makeViewGone;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.showFailureMsg;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.showProgressBar;

public abstract class BaseDataLoaderFragment extends BaseFragment implements DataFetcherCallback {

    @BindView(R.id.msg_iv)
    protected ImageView errorIV;
    @BindView(R.id.progress_bar)
    protected View progressBar;
    @BindView(R.id.msg_tv)
    protected TextView errorTV;
    @BindView(R.id.msg_layout)
    protected View errorLayout;

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
     * to reload data if the user click on the error image and it was because failure in internet connection
     */
    @OnClick(R.id.msg_iv)
    void onClickMsgIV(){
        String errorMsg = getTextFromEditText(errorTV);
        if (activity.getString(R.string.no_connection).equals(errorMsg)) setUI();
    }

    @OnClick(R.id.msg_tv)
    void onClickMsgTV(){
        onClickMsgIV();
    }

    /**
     * called when there response has empty list
     */
    protected void handleNoDataFromResponse() {
        Throwable noDataThrowable = new Throwable(activity.getString(R.string.no_news_found));
        onFailure(noDataThrowable, android.R.drawable.stat_notify_error);
    }

    /**
     * if there is not internet connection before calling the api and refresh after 1 second
     */
    protected void handleCaseNoConnection() {
        Throwable noInternetConnectionThrowable = new Throwable(activity.getString(R.string.no_connection));
        onFailure(noInternetConnectionThrowable, android.R.drawable.stat_notify_sync);
    }
}
