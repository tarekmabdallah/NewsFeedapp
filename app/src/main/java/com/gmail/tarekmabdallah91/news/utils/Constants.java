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

package com.gmail.tarekmabdallah91.news.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import com.gmail.tarekmabdallah91.news.BuildConfig;

import java.util.regex.Pattern;

public class Constants {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final String SPACE_REGEX = "[\\s]";
    public static final String EMPTY_STRING = "";
    public static final String COMMA = ",";
    public static final String SPACE = " ";
    public static final String DASH = "-";
    public static final String NEW_LINE = "\n";

    private static final String QUERY_API_KEY_KEYWORD = "api-key";
    public static final String QUERY_FROM_DATE_KEYWORD = "from-date";
    public static final String QUERY_TO_DATE_KEYWORD = "to-date";
    public static final String QUERY_Q_KEYWORD = "q";
    static final String QUERY_LANGUAGE_KEYWORD = "lang";
    static final String QUERY_PAGE_SIZE_KEYWORD = "page-size";
    static final String QUERY_PAGE_KEYWORD = "page";
    static final String QUERY_ORDER_BY_KEYWORD = "order-by";
    static final String QUERY_ORDER_date_KEYWORD = "order-date";
    static final String QUERY_SHOW_FIELDS_KEYWORD = "show-fields";
    static final String ARTICLE_FIELDS = "byline,thumbnail,trailText,headline,trailText,byline,bodyText,body,wordcount"; // CAN ALSO USE "all" to get all fields, "trailText,headline,standfirst,byline,main,body"

    // TODO: you can put your API key in gradle.properties like THE_GUARDIAN_API_KEY = "put_your_key_here"
    private static final String API_KEY = BuildConfig.ApiKey;

    // HEADER'S DATA
    public static final String HEADER_API_KEY = QUERY_API_KEY_KEYWORD + ": " + API_KEY;

    public static final String SECTIONS_KEYWORD = "sections"; // used in API as section id "sections"
    public static final String SEARCH_KEYWORD = "search"; // used in API as section id "search"
    public static final String SEARCH_HISTORY_KEYWORD = "search history";
    public static final String TITLE_KEYWORD = "TITLE_KEYWORD";
    public static final String ARTICLE_HTML_KEYWORD = "ARTICLE_HTML_KEYWORD";
    public static final String ARTICLES_LIST_KEYWORD = "ARTICLES_LIST_KEYWORD";
    public static final String ARTICLES_KEYWORD = "ARTICLES_KEYWORD";
    public static final String URL_KEYWORD = "URL_KEYWORD";
    public static final String MAIN_SECTION_KEYWORD = "MAIN_SECTION_KEYWORD";
    public static final String SECTION_ID_KEYWORD = "SECTION_ID_KEYWORD";
    public static final String IS_SP_UPDATED = "IS_SP_UPDATED";
    public static final String IS_COUNTRY_SECTION = "IS_COUNTRY_SECTION";
    public static final String IS_FAVOURITE_LIST = "IS_FAVOURITE_LIST";

    public static final String EMAIL_INTENT = "mailto:";
    public static final String CALL_INTENT = "tel:";
    public static final String TEXT_PLAIN = "text/plain";
    public static final String HTML_TEXT = "text/html; charset=utf-8";
    public static final String UTF8 = "UTF-8";

    public static final int INVALID = -1;
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;
    public static final int FIVE = 5;
    public static final int PAGE_SIZE = 10;

    // these all methods used to set Font to the TextViews / EditTexts
    private static Typeface buttonTypeFace(Context context) {
        return Font.VelinoSansMedium(context);
    }

    public static Typeface productNameTypeFace(Context context) {
        return Font.VelinoSansLight(context);
    }

    public static Typeface productDetailsTypeFace(Context context) {
        return Font.VelinoSansThin(context);
    }

    public static Typeface textTypeFace(Context context) {
        return Font.VelinoSansBook(context);
    }

    private static Typeface smallTextTypeFace(Context context) {
        return Font.VelinoSansThin(context);
    }

    public static Typeface designerTypeFace(Context context) {
        return Font.VelinoSansMedium(context);
    }

    public static Typeface titleToolbarTypeFace(Context context) {
        //return Font.VelinoSansBold(context);
        return Font.VelinoSansMedium(context);
    }

    private static Typeface labelTypeFace(Context context) {
        return Font.VelinoSansBold(context);
    }

    public static void makeTypeFaceLabelStyle(TextView... textViews) {
        Context context = textViews[ZERO].getContext();
        for (TextView textView : textViews) textView.setTypeface(labelTypeFace(context));
    }

    public static void makeTypeFaceTextStyle(TextView... textViews) {
        Context context = textViews[ZERO].getContext();
        for (TextView textView : textViews) textView.setTypeface(textTypeFace(context));
    }

    public static void makeTypeFaceBtnStyle(TextView... textViews) {
        Context context = textViews[ZERO].getContext();
        for (TextView textView : textViews) textView.setTypeface(buttonTypeFace(context));
    }

    public static void makeTypeFaceSmallTextStyle(TextView... textViews) {
        Context context = textViews[ZERO].getContext();
        for (TextView textView : textViews) textView.setTypeface(smallTextTypeFace(context));
    }

    public static void setTypeFaceNoDataTv(TextView noDataTv) {
        if (null != noDataTv) makeTypeFaceLabelStyle(noDataTv);
    }

}
