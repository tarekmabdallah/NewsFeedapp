package com.gmail.tarekmabdallah91.news.views.bases;

import android.os.Bundle;

public interface BaseInterface {

    /**
     * called when the activity created for the first time (WhenSaveInstanceStateNull)
     */
    void setActivityWhenSaveInstanceStateNull();

    /**
     * called when the device rotated  (WhenSaveInstanceState IS NOT Null)
     *
     * @param savedInstanceState -
     */
    void reSetActivityWithSaveInstanceState(Bundle savedInstanceState);

    /**
     * to init some values once and will be called every time the device rotated
     */
    void initiateValues();

    /**
     * to init some values after check if SaveInstanceState is null or not
     */
    void initiateValuesAfterCheckSaveInstanceState();
    /**
     * override it to set the  UI
     * it is called in onResume() to recalled each time the activity resumed
     */
    void setUI();

    /**
     * @return the layout resource id for each activity
     */
    int getLayoutResId();

    /**
     * simple method to show Toast Msg and control all Toast's style in the app
     *
     * @param msg which will be shown
     */
    void showToastMsg(String msg);
}
