package com.gmail.tarekmabdallah91.news.views.splashActivity;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.views.bases.BaseActivityNoMenu;

import butterknife.OnClick;

import static com.gmail.tarekmabdallah91.news.views.easyViewActivity.EasyViewActivity.openEasyViewActivity;
import static com.gmail.tarekmabdallah91.news.views.mainSection.MainSectionActivity.openMainSectionActivity;

public class SplashActivity extends BaseActivityNoMenu {


    @Override
    public int getLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected String getActivityTitle() {
        return "Choose style";
    }

    @OnClick(R.id.simple_view_btn)
    void onClickSimpleViewBtn (){
        openMainSectionActivity(this, false);
        //openSimpleActivity(this,"brazil","empty", true);
    }

    @OnClick(R.id.easy_view_btn)
    void onClickEasyViewBtn(){
        openEasyViewActivity(this);
    }
}
