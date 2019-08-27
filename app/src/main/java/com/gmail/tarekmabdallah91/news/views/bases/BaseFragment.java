package com.gmail.tarekmabdallah91.news.views.bases;

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

import com.gmail.tarekmabdallah91.news.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.showShortToastMsg;

public abstract class BaseFragment extends Fragment {

    @Nullable @BindView(R.id.msg_iv)
    protected ImageView errorIV;
    @Nullable @BindView(R.id.progress_bar)
    protected View progressBar;
    @Nullable @BindView(R.id.msg_tv)
    protected TextView errorTV;
    @Nullable @BindView(R.id.msg_layout)
    protected View errorLayout;

    protected Activity activity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    protected abstract int getLayoutResId();

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

    public void initiateValues (){}

    public void setActivityWhenSaveInstanceStateNull() {}

    public void reSetActivityWithSaveInstanceState(Bundle savedInstanceState) {}

    public void initiateValuesAfterCheckSaveInstanceState() {
    }

    public void setUI (){}

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

    /**
     * simple method to show Toast Msg and control all Toast's style in the app
     *
     * @param msg which will be shown
     */
    public void showToastMsg(String msg) {
        showShortToastMsg(activity, msg);
    }
}
