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
import com.example.tarek.news.views.bases.BaseActivityNoMenu;

import butterknife.BindView;

import static com.example.tarek.news.utils.Constants.CALL_INTENT;
import static com.example.tarek.news.utils.Constants.EMAIL_INTENT;
import static com.example.tarek.news.utils.Constants.EMPTY_STRING;
import static com.example.tarek.news.utils.Constants.FIVE;
import static com.example.tarek.news.utils.Constants.TEXT_PLAIN;
import static com.example.tarek.news.utils.Constants.URL_KEYWORD;
import static com.example.tarek.news.utils.Constants.ZERO;
import static com.example.tarek.news.utils.ViewsUtils.showProgressBar;

public class WebViewActivity extends BaseActivityNoMenu {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progress_bar)
    View progressBar;

    private String url;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initiateValues() {
        getComingIntent();
        setWebView();
        setTitle(getString(R.string.article_details_label));
    }

    @Override
    protected void getComingIntent() {
        Intent comingIntent = getIntent();
        url = comingIntent.getStringExtra(URL_KEYWORD);
    }

    @Override
    protected void setUI() {
        if (null != url) webView.loadUrl(url);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setWebView() {
        final Context context = this;
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
                    sendEmailIntent(context, email);
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
                showToastMsg("Error:" + description);
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

    public static void openWebViewActivityArticle(Context context, String articleUrl){
        Intent openWebViewActivityArticle =
                new Intent(context, WebViewActivity.class).putExtra(URL_KEYWORD, articleUrl);
        context.startActivity(openWebViewActivityArticle);
    }
}

