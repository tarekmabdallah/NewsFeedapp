/*
 *
 * Copyright 2019 tarekmabdallah91@gmail.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.gmail.tarekmabdallah91.news.views.webViewActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.data.room.news.ArticlesRoomHelper;
import com.gmail.tarekmabdallah91.news.models.articles.Article;
import com.gmail.tarekmabdallah91.news.views.bases.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

import static com.gmail.tarekmabdallah91.news.utils.Constants.ARTICLES_KEYWORD;
import static com.gmail.tarekmabdallah91.news.utils.Constants.CALL_INTENT;
import static com.gmail.tarekmabdallah91.news.utils.Constants.EMAIL_INTENT;
import static com.gmail.tarekmabdallah91.news.utils.Constants.EMPTY_STRING;
import static com.gmail.tarekmabdallah91.news.utils.Constants.FIVE;
import static com.gmail.tarekmabdallah91.news.utils.Constants.HTML_TEXT;
import static com.gmail.tarekmabdallah91.news.utils.Constants.TEXT_PLAIN;
import static com.gmail.tarekmabdallah91.news.utils.Constants.UTF8;
import static com.gmail.tarekmabdallah91.news.utils.Constants.ZERO;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.checkIfFoundInWishListDb;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.setFabIcon;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.showProgressBar;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.showView;

public class WebViewFragment extends BaseFragment {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progress_bar)
    View progressBar;
    @BindView(R.id.fab_iv)
    ImageView addToFavouriteListFab;
    @BindView(R.id.fab_layout_id)
    RelativeLayout addToFavouriteListFabLayout;

    private String url;
    private String textHtml; // to show html view  blocks>body>bodyHtml
    private Article article;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_web_view;
    }

    @Override
    public void initiateValues() {
        setWebView();
    }

    protected void getComingIntent() {
        Intent comingIntent = activity.getIntent();
        article = comingIntent.getParcelableExtra(ARTICLES_KEYWORD);
        url = article.getWebUrl();
//        if (null != article){
//            url = article.getWebUrl();
//            textHtml = article.getFields().getBody();
//        }else {
//            url = comingIntent.getStringExtra(URL_KEYWORD);
//            if (!isUrl(url)) textHtml = comingIntent.getStringExtra(ARTICLE_HTML_KEYWORD);
//        }
        checkIfFoundInWishListDb(addToFavouriteListFabLayout, addToFavouriteListFab, article.getId());
    }

    @Override
    public void setUI() {
        getComingIntent();
        if (null != textHtml) webView.loadData(textHtml, HTML_TEXT, UTF8);
        else if (null != url) webView.loadUrl(url);
        else showToastMsg(getString(R.string.error_label));
    }

    @OnClick(R.id.fab_layout_id)
    void onClickFavFabBtn() {
        ArticlesRoomHelper articlesRoomHelper = ArticlesRoomHelper.getInstance(activity);
        boolean isFavorited = !((boolean) addToFavouriteListFab.getTag());
        if (isFavorited) {
            articlesRoomHelper.insertArticle(article);
            showToastMsg(activity.getString(R.string.added_successfully_msg));
        } else {
            articlesRoomHelper.deleteArticleById(article.getId());
            showToastMsg(activity.getString(R.string.removed_successfully_msg));
        }
        setFabIcon(addToFavouriteListFabLayout, addToFavouriteListFab, isFavorited);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        showProgressBar(progressBar, true);
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains(EMAIL_INTENT)){// if it was email in HTML formatted it will be begin with "mailto" keyword
                    String email = url.replace(EMAIL_INTENT, EMPTY_STRING);
                    sendEmailIntent(activity, email);
                } else if (url.contains(CALL_INTENT)){
                    callMobileIntent(url);
                } else if (isUrl(url)) {
                    view.loadUrl(url); // load any normal url
                }else {
                    showToastMsg(getString(R.string.not_valid_url_msg));
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                showProgressBar(progressBar, false);
                showView(addToFavouriteListFabLayout, true);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                showToastMsg(getString(R.string.error_label) + description);
            }
        };
        webView.setWebViewClient(webViewClient);
    }

    @SuppressLint("IntentReset")
    private void sendEmailIntent(Context context, String email){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse(EMAIL_INTENT));
        emailIntent.setType(TEXT_PLAIN);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        context.startActivity(emailIntent);
    }

    private boolean isUrl (String urlOrHtml){
        final String HTTPS = "https";
        return urlOrHtml.substring(ZERO,FIVE).equals(HTTPS);
    }

    private void callMobileIntent (String mobileNumber){
        Intent callNumber = new Intent(Intent.ACTION_DIAL);
        callNumber.setData(Uri.parse(mobileNumber));
        startActivity(callNumber);
    }
}
