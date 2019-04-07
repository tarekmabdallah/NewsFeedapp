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

public class Font {

    public static Typeface VelinoSansBlack(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/VelinoSans-Black.otf");
    }

    static Typeface VelinoSansBold(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/VelinoSans-Bold.otf");
    }

    static Typeface VelinoSansBook(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/VelinoSans-Book.otf");
    }

    static Typeface VelinoSansLight(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/VelinoSans-Light.otf");
    }

    static Typeface VelinoSansMedium(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/VelinoSans-Medium.otf");
    }

    static Typeface VelinoSansThin(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/VelinoSans-Thin.otf");
    }
}
