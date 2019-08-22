package com.gmail.tarekmabdallah91.news.views.bases;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public interface BaseInterface {

    /**
     * to init some values once and will be called every time the device rotated
     */
    void initiateValues(Activity activity, View...views);

    /**
     * to init some values after check if SaveInstanceState is null or not
     */
    void initiateValuesAfterCheckSaveInstanceState();

    /**
     * called when the activity created for the first time (WhenSaveInstanceStateNull)
     */
    void setActivityWhenSaveInstanceStateNull();

    /**
     * called when the device rotated  (WhenSaveInstanceState IS NOT Null)
     *
     * @param savedInstanceState -
     */
    void reSetActivityWithSaveInstanceState(Bundle savedInstanceState, RecyclerView articlesRecyclerView);

    void onSaveInstanceState(Bundle savedInstanceState, RecyclerView articlesRecyclerView);

    /**
     * override it to set the  UI
     * it is called in onResume() to recalled each time the activity resumed
     */
    void setUI(Activity activity);

    /**
     * simple method to show Toast Msg and control all Toast's style in the app
     *
     * @param msg which will be shown
     */
    void showToastMsg(String msg);

    /**
     * @return activity title which saved in intent
     */
    String getActivityTitle(Activity activity);
}
