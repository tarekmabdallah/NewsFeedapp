package com.example.tarek.news.views.bases;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tarek.news.R;
import com.example.tarek.news.apis.DataFetcherCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.tarek.news.utils.ViewsUtils.getTextFromEditText;
import static com.example.tarek.news.utils.ViewsUtils.isConnected;
import static com.example.tarek.news.utils.ViewsUtils.makeViewGone;
import static com.example.tarek.news.utils.ViewsUtils.showFailureMsg;
import static com.example.tarek.news.utils.ViewsUtils.showProgressBar;
import static com.example.tarek.news.utils.ViewsUtils.showShortToastMsg;

public abstract class BaseFragment extends Fragment implements DataFetcherCallback {

    @BindView(R.id.msg_iv)
    protected ImageView errorIV;
    @BindView(R.id.progress_bar)
    protected View progressBar;
    @BindView(R.id.msg_tv)
    protected TextView errorTV;
    @BindView(R.id.msg_layout)
    protected View errorLayout;

    protected Activity activity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initiateValues();
        if (null == savedInstanceState) {
            setActivityWhenSaveInstanceStateNull();
        } else {
            reSetActivityWithSaveInstanceState(savedInstanceState);
        }
        initiateValuesAfterCheckSaveInstanceState();
    }

    @Override
    public void onResume() {
        super.onResume();
        setUI();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
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
     * called when the activity created for the first time (WhenSaveInstanceStateNull)
     */
    protected void setActivityWhenSaveInstanceStateNull() {
    }

    /**
     * called when the device rotated  (WhenSaveInstanceState IS NOT Null)
     *
     * @param savedInstanceState -
     */
    protected void reSetActivityWithSaveInstanceState(Bundle savedInstanceState) {
    }

    /**
     * to init some values once and will be called every time the device rotated
     */
    protected void initiateValues() {
    }

    /**
     * to init some values after check if SaveInstanceState is null or not
     */
    protected void initiateValuesAfterCheckSaveInstanceState(){

    }
    /**
     * override it to set the  UI
     * it is called in onResume() to recalled each time the activity resumed
     */
    protected void setUI() {
        if (isConnected(activity)) loadData();
        else handleCaseNoConnection();
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
     * to be override to call the api
     */
    protected void callAPi() {

    }

    /**
     * called after hiding the loading indicator (progress bar)
     */
    protected void whenDataFetchedGetResponse(Object response) {

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

    /**
     * to reload data if the user click on the error image and it was because failure in internet connection
     */
    @OnClick(R.id.msg_iv)
    void onClickMsgIV(){
        String errorMsg = getTextFromEditText(errorTV);
        if (getString(R.string.no_connection).equals(errorMsg)) setUI();
    }

    @OnClick(R.id.msg_tv)
    void onClickMsgTV(){
        onClickMsgIV();
    }

    /**
     * simple method to show Toast Msg and control all Toast's style in the app
     *
     * @param msg which will be shown
     */
    protected void showToastMsg(String msg) {
        showShortToastMsg(activity, msg);
    }

    /**
     * @return the layout resource id for each activity
     */
    protected abstract int getLayoutResId();
}
