package com.gmail.tarekmabdallah91.news.views.bases;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.tarekmabdallah91.news.R;

import butterknife.BindView;
import butterknife.OnClick;

import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.getTextFromEditText;

public abstract class BaseDataLoaderFragment extends BaseFragment {

    @BindView(R.id.msg_iv)
    protected ImageView errorIV;
    @BindView(R.id.progress_bar)
    protected View progressBar;
    @BindView(R.id.msg_tv)
    protected TextView errorTV;
    @BindView(R.id.msg_layout)
    protected View errorLayout;

    protected abstract String getSectionId();

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
}
