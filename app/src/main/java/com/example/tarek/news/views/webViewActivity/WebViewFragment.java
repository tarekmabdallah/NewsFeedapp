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

package com.example.tarek.news.views.webViewActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.tarek.news.R;
import com.example.tarek.news.views.bases.BaseFragment;

import butterknife.BindView;

import static com.example.tarek.news.utils.Constants.ARTICLE_HTML_KEYWORD;
import static com.example.tarek.news.utils.Constants.CALL_INTENT;
import static com.example.tarek.news.utils.Constants.EMAIL_INTENT;
import static com.example.tarek.news.utils.Constants.EMPTY_STRING;
import static com.example.tarek.news.utils.Constants.FIVE;
import static com.example.tarek.news.utils.Constants.HTML_TEXT;
import static com.example.tarek.news.utils.Constants.TEXT_PLAIN;
import static com.example.tarek.news.utils.Constants.URL_KEYWORD;
import static com.example.tarek.news.utils.Constants.UTF8;
import static com.example.tarek.news.utils.Constants.ZERO;
import static com.example.tarek.news.utils.ViewsUtils.showProgressBar;

public class WebViewFragment extends BaseFragment {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progress_bar)
    View progressBar;

    private String url;
    private String textHtml;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_web_view;
    }

    @Override
    protected void initiateValues() {
        getComingIntent();
        setWebView();
    }

    protected void getComingIntent() {
        Intent comingIntent = activity.getIntent();
        url = comingIntent.getStringExtra(URL_KEYWORD);
        textHtml = comingIntent.getStringExtra(ARTICLE_HTML_KEYWORD);
    }

    @Override
    protected void setUI() {
        if (null != url) webView.loadUrl(url);
        else if (null != textHtml) webView.loadData(textHtml, HTML_TEXT, UTF8);
        else showToastMsg(getString(R.string.error_label));
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
                } else if (url.substring(ZERO,FIVE).equals("https")) {
                    view.loadUrl(url); // load any normal url
                }else {
                    showToastMsg("Sorry can not open this link");
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                showProgressBar(progressBar, false);
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

    private void callMobileIntent (String mobileNumber){
        Intent callNumber = new Intent(Intent.ACTION_DIAL);
        callNumber.setData(Uri.parse(mobileNumber));
        startActivity(callNumber);
    }
}
