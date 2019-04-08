/*
 * Copyright 2019 tarekmabdallah91@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.tarek.news.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import com.example.tarek.news.BuildConfig;

import java.util.regex.Pattern;

public class Constants {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final String SPACE_REGEX = "[\\s]";
    public static final String EMPTY_STRING = "";
    public static final String COMMA = ",";
    public static final String SPACE = " ";
    public static final String NEW_LINE = "\n";
    public static final String QUERY_Q_KEYWORD = "q";
    public static final String QUERY_API_KEY_KEYWORD = "api-key";
    // to get the tags which contains the author name use this query and this value
    public static final String QUERY_TAGS_KEYWORD = "show-tags";
    public static final String CONTRIBUTOR_KEYWORD = "contributor";

    // TODO: you can put your API key in gradle.properties like THE_GUARDIAN_API_KEY = "put_your_key_here"
    public static final String API_KEY = BuildConfig.ApiKey;
    public static final String ACTIVITY_NOT_FOUND_EXCEPTION = "ActivityNotFoundException";

    public static final int INVALID = -1;
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;

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
