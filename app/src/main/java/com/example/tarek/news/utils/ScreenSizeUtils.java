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
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class ScreenSizeUtils {

    private final Context context;
    private final DisplayMetrics displayMetrics;

    public ScreenSizeUtils(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        displayMetrics = getDisplayMetrics();
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        }
    }

    public int calculateSpanCount() {
        float dpWidth = getScreenWidthDp();
        final int SCALING_FACTOR = 180;
        final int TWO = 2;
        int noOfColumns = (int) (dpWidth / SCALING_FACTOR);
        if (noOfColumns < TWO)
            noOfColumns = TWO;
        return noOfColumns;
    }

    public DisplayMetrics getDisplayMetrics() {
        return context.getResources().getDisplayMetrics();
    }

    public float getScreenWidthDp() {
        return displayMetrics.widthPixels / displayMetrics.density;
    }

    public int getScreenWidthPx() {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public int getScreenHeightPx() {
        return displayMetrics.heightPixels;
    }

    /**
     * Method for converting Pixels value to actual DPs (return float value)
     * ref: https://android--code.blogspot.com/2015/09/android-how-to-convert-pixels-to-dp.html
     * px = dp * (dpi / 160)
     */
    public float getActualDPsFromPixels(int pixels) {
        Resources r = context.getResources();
        return pixels / (r.getDisplayMetrics().densityDpi / 160f);
    }


}