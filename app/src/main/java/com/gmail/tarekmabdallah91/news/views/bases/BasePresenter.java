package com.gmail.tarekmabdallah91.news.views.bases;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.paging.ListItemClickListener;

import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.getTextFromEditText;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.restartActivity;

public class BasePresenter implements BaseInterface, ListItemClickListener {

    private static BasePresenter basePresenter;

    public static BasePresenter getInstance() {
        if (null == basePresenter) basePresenter = new BasePresenter();
        return basePresenter;
    }

    void setOnClickListenerForErrorMsg(ImageView errorIV, final TextView errorTV){
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickMsgIV(errorTV);
            }
        };
        if (null != errorIV) errorIV.setOnClickListener(onClickListener);
        if (null != errorTV) errorTV.setOnClickListener(onClickListener);
    }

    /**
     * to reload data if the user click on the error image and it was because failure in internet connection
     */
    private void onClickMsgIV(TextView errorTV){
        Context context = errorTV.getContext();
        String errorMsg = getTextFromEditText(errorTV);
        if (context.getString(R.string.no_connection).equals(errorMsg)) onRetryClick((Activity) context);
    }

    @Override
    public void onRetryClick(Activity activity) {
        restartActivity(activity);
    }

    @Override
    public void initiateValues(Activity activity, View...views) {}

    @Override
    public void initiateValuesAfterCheckSaveInstanceState() {

    }

    @Override
    public void setActivityWhenSaveInstanceStateNull() {

    }

    @Override
    public void reSetActivityWithSaveInstanceState(Bundle savedInstanceState, RecyclerView articlesRecyclerView) {

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState, RecyclerView articlesRecyclerView) {

    }

    @Override
    public void setUI(Activity activity) {

    }

    @Override
    public void showToastMsg(String msg) {

    }

    @Override
    public String getActivityTitle(Activity activity) {
        return null;
    }
}
