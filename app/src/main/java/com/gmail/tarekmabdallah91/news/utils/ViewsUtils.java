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

package com.gmail.tarekmabdallah91.news.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.data.room.favArticles.ArticlesRoomHelper;
import com.gmail.tarekmabdallah91.news.data.room.favArticles.RetrieveArticleData;
import com.gmail.tarekmabdallah91.news.data.sp.SharedPreferencesHelper;
import com.gmail.tarekmabdallah91.news.models.articles.Article;
import com.gmail.tarekmabdallah91.news.views.sections.SpinnerAdapter;
import com.gmail.tarekmabdallah91.news.views.sections.SpinnerOnItemClickedListener;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.gmail.tarekmabdallah91.news.utils.Constants.ARTICLE_FIELDS;
import static com.gmail.tarekmabdallah91.news.utils.Constants.COMMA;
import static com.gmail.tarekmabdallah91.news.utils.Constants.EMPTY_STRING;
import static com.gmail.tarekmabdallah91.news.utils.Constants.INVALID;
import static com.gmail.tarekmabdallah91.news.utils.Constants.ONE;
import static com.gmail.tarekmabdallah91.news.utils.Constants.PAGE_SIZE;
import static com.gmail.tarekmabdallah91.news.utils.Constants.QUERY_FROM_DATE_KEYWORD;
import static com.gmail.tarekmabdallah91.news.utils.Constants.QUERY_ORDER_BY_KEYWORD;
import static com.gmail.tarekmabdallah91.news.utils.Constants.QUERY_ORDER_date_KEYWORD;
import static com.gmail.tarekmabdallah91.news.utils.Constants.QUERY_PAGE_KEYWORD;
import static com.gmail.tarekmabdallah91.news.utils.Constants.QUERY_PAGE_SIZE_KEYWORD;
import static com.gmail.tarekmabdallah91.news.utils.Constants.QUERY_SHOW_FIELDS_KEYWORD;
import static com.gmail.tarekmabdallah91.news.utils.Constants.QUERY_TO_DATE_KEYWORD;
import static com.gmail.tarekmabdallah91.news.utils.Constants.SPACE_REGEX;
import static com.gmail.tarekmabdallah91.news.utils.Constants.THREE;
import static com.gmail.tarekmabdallah91.news.utils.Constants.TWO;
import static com.gmail.tarekmabdallah91.news.utils.Constants.VALID_EMAIL_ADDRESS_REGEX;
import static com.gmail.tarekmabdallah91.news.utils.Constants.ZERO;
import static com.gmail.tarekmabdallah91.news.utils.Constants.makeTypeFaceLabelStyle;

public final class ViewsUtils {
    /**
     * @return true if it was empty
     */
    private static boolean isEditTextEmpty(EditText editText) {
        Context context = editText.getContext();
        if (getTextFromEditText(editText).isEmpty()) {
            editText.setError(context.getString(R.string.required_msg));
            return true;
        }
        return false;
    }

    /**
     * used for required fields
     *
     * @param editText to check it's state
     * @return true if was valid
     */
    public static boolean isValidText(EditText editText) {
        String text = getTextFromEditText(editText);
        if (!isEditTextEmpty(editText)) return isValidText(editText, text);
        return false;
    }

    /**
     * used for not required fields not show rquired msg if was empty
     *
     * @return true if was valid
     */
    private static boolean isValidNotRequiredText(EditText editText) {
        String text = getTextFromEditText(editText);
        return text.isEmpty() || isValidText(editText, text);
    }

    /**
     * to refuse the special characters
     *
     * @return true if valid
     */
    private static boolean isValidText(EditText editText, String text) {
        Context context = editText.getContext();
        Pattern alphaNumeric = Pattern.compile("[a-zA-Z0-9]");
        Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]"); // "[!@#$%&*()_+=|<>?{}\\[\\]~-]"

        Matcher hasAlphaNumeric = alphaNumeric.matcher(text);
        Matcher hasSpecial = special.matcher(text);

        boolean isValid = hasAlphaNumeric.find() && !hasSpecial.find();
        if (!isValid) {
            editText.setError(context.getString(R.string.special_chars_msg));
            return false;
        } else return true;
    }

    /**
     * to validate the entries
     *
     * @return List<Boolean> each item for each EditText entry .. if has 1 field not valid , the boolean list will has 1 item false so the user will be noticed
     */
    public static List<Boolean> validateFields(EditText... editTexts) {
        List<Boolean> booleans = new ArrayList<>();
        for (EditText editText : editTexts) booleans.add(isValidText(editText));
        return booleans;
    }

    /**
     * to validate not require fields as it may be empty , but if it has data it must be valid
     *
     * @return List<Boolean> each item for each EditText entry .. if has 1 field not valid , the boolean list will has 1 item false so the user will be noticed
     */
    public static List<Boolean> validateNotRequiredFields(EditText... editTexts) {
        List<Boolean> booleans = new ArrayList<>();
        for (EditText editText : editTexts) booleans.add(isValidNotRequiredText(editText));
        return booleans;
    }

    public static String getTextFromEditText(TextView view) {
        return view.getText().toString().trim();
    }

    /**
     * @return true if the @param entry is not empty else return false
     */
    public static boolean isValidString(String entry) {
        return !entry.replaceAll(SPACE_REGEX, EMPTY_STRING).isEmpty();
    }

    /**
     * to validate the password
     *
     * @return true if vaild
     */
    public static boolean isValidPassword(EditText editText) {
        Context context = editText.getContext();
        String password = getTextFromEditText(editText);
        if (password.length() >= 6) {
            Pattern smallLetter = Pattern.compile("[a-z]");
            Pattern capitalLetter = Pattern.compile("[A-Z]");
            Pattern digit = Pattern.compile("[0-9]");
            Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

            Matcher hasSmallLetter = smallLetter.matcher(password);
            Matcher hasCapitalLetter = capitalLetter.matcher(password);
            Matcher hasDigit = digit.matcher(password);
            Matcher hasSpecial = special.matcher(password);

            boolean isValid = hasSmallLetter.find() && hasCapitalLetter.find() && hasDigit.find() && hasSpecial.find();
            if (!isValid) editText.setError(context.getString(R.string.password_requirements_msg));
            else return true;
        } else editText.setError(context.getString(R.string.password_length_msg));
        return false;
    }

    /**
     * @return true if valid or false if not valid
     */
    public static boolean isValidEmail(Context context, String email) {
        if (email.isEmpty()) {
            showShortToastMsg(context, "please enter your email");
            return false;
        } else if (!isEmailFormat(email)) {
            showShortToastMsg(context, "please enter valid email");
            return false;
        }
        return true;
    }

    public static boolean isValidPhoneNumber(EditText editText) {
        String phoneNumber = getTextFromEditText(editText);
        if (phoneNumber.isEmpty() || phoneNumber.length() < 11) {
            editText.setError("please enter valid phone number");
            return false;
        } else return true;

    }

    /**
     * * https://stackoverflow.com/questions/8204680/java-regex-email
     *
     * @return true if valid or false if not valid
     */
    private static boolean isEmailFormat(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    public static void makeViewDimmed(View... views) {
        for (View view : views) {
            view.setEnabled(false);
            view.setClickable(false);
        }
    }

    public static void makeViewInvisible(View... views) {
        for (View view : views) view.setVisibility(INVISIBLE);
    }

    public static void makeViewGone(View... views) {
        for (View view : views) view.setVisibility(GONE);
    }

    public static void makeViewVisible(View... views) {
        for (View view : views) view.setVisibility(VISIBLE);
    }

    public static String formatPrice(float price) {
        return formatFloatValue(price) + " EGP ";
    }

    private static String formatFloatValue(float price) {
        NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
        nf.setMaximumFractionDigits(ZERO); // may need to add decimal values
        return nf.format(price);
    }

    private static String formatSale(float sale, float percentage) {
        return String.format("%s %s", formatPrice(sale), formatFloatValue(percentage));
    }

    private static String formatSaleForPayment(float sale, float percentage) {
        return String.format("%s\n%s", formatPrice(sale), formatFloatValue(percentage));
    }

    public static String getSaleString(float sale, float percentage) {
        return sale > ZERO ? formatSale(sale, percentage) : EMPTY_STRING;
    }

    public static String getSaleStringForPayment(float sale, float percentage) {
        return sale > ZERO ? formatSaleForPayment(sale, percentage) : EMPTY_STRING;
    }

    public static String formatText(String format, int val) {
        return String.format(format, val);
    }

    /**
     * to control loading the images in the app
     */
    public static void loadImage(String imageUrl, ImageView imageView) {
        Picasso.get().load(imageUrl)
                .placeholder(R.drawable.progress_animated)
                .error(R.drawable.progress_animated)
                .into(imageView);
    }

    public static void loadImage(int imageResId, ImageView imageView) {
        Picasso.get().load(imageResId)
                .placeholder(R.drawable.progress_animated)
                .error(R.drawable.progress_animated)
                .into(imageView);
    }

    /**
     * to control showing dialog fragments by tag
     */
    public static void showDialogFragment(FragmentTransaction fragmentTransaction, Fragment fragment, DialogFragment dialogFragment, String tag) {
        if (fragment != null) {
            fragmentTransaction.remove(fragment);
        }
        fragmentTransaction.addToBackStack(null);
        dialogFragment.show(fragmentTransaction, tag);
    }

    public static void printLogException(String tag, Exception e, String msg) {
        Log.e(tag, msg + e);
    }

    /**
     * to cancel retrofit calls when needed
     */
    public static void cancelRetrofitCalls(Call... calls) {
        for (Call call : calls) {
            if (null != call && !call.isCanceled()) call.cancel();
        }
    }

    /**
     * to restart the current activity
     */
    public static void restartActivity(Activity activity) {
        Intent intent = activity.getIntent();
        activity.finish();
        activity.startActivity(intent);
    }

    /**
     * to scale sizes in px
     */
    public static int setScaleInPx(Context context, float sizeInP) {
        ScreenSizeUtils screenSizeUtils = new ScreenSizeUtils(context);
        float fpixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, sizeInP, screenSizeUtils.getDisplayMetrics());
        return Math.round(fpixels);
    }

    /**
     * used when we need to block the UI while some data getMyApplication loaded and the progressBar is visible
     */
    public static void showProgressBar(View progressBar, boolean show) {
        AppCompatActivity activity = (AppCompatActivity) progressBar.getContext();
        if (show) {
            progressBar.setVisibility(VISIBLE);
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } else {
            progressBar.setVisibility(GONE);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    public static void showNormalProgressBar(View progressBar, boolean show) {
        if (show) makeViewVisible(progressBar);
        else makeViewGone(progressBar);
    }

    /**
     * used when want to show / hide a view
     *
     * @param show boolean to show / hide
     */
    public static void showView(View view, boolean show) {
        if (null != view) {
            if (show) makeViewVisible(view);
            else makeViewGone(view);
        }
    }

    /**
     * change the state of the FAB as the state of the item if it was added to the FavList or not
     * @param fabLayout to change it's color background
     * @param fab  to change it's icon
     * @param isWishList - to getMyApplication it's state and set the UI
     */
    @SuppressLint("NewApi")
    public static void setFabIcon(View fabLayout, ImageView fab, boolean isWishList) {
        Context context = fab.getContext();
        if (isWishList) {
            fab.setImageDrawable(ContextCompat.getDrawable(context, android.R.drawable.star_big_on));
        } else {
            fab.setImageDrawable(ContextCompat.getDrawable(context, android.R.drawable.star_big_off));
        }
        fab.setTag(isWishList);
        fabLayout.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorAccent)));
    }

    /**
     * to check if the opened item's details is in the wishList / fav list in local db
     */
    public static void checkIfFoundInWishListDb(final View addToWishListFabLayout, final ImageView addToWishListFab, final String id) {
        final Context context = addToWishListFab.getContext();
        final ArticlesRoomHelper articlesRoomHelper = ArticlesRoomHelper.getInstance(context);
        RetrieveArticleData retrieveWishListData = new RetrieveArticleData() {

            @Override
            public void onComplete(List<Article> articlesList) {
                int rowId = isItemInWishList(articlesList, id);
                boolean isFoundInDb = INVALID < rowId;
                setFabIcon(addToWishListFabLayout, addToWishListFab, isFoundInDb);
            }
        };
        articlesRoomHelper.getArticlesList(retrieveWishListData);
    }

    /**
     * search for the item in FavList in db by @param id
     * @return rowId if founded or -1 if not
     */
    private static int isItemInWishList(List<Article> articlesList, String id) {
        if (null != articlesList && !articlesList.isEmpty()) {
            for (Article article : articlesList)
                if (id.equals(article.getId())) return article.getRowIdDb();
        }
        return INVALID;
    }


    /**
     * to control showing toast msg in all the app
     */
    private static void showToastMsgOnFailLoadingData(Context context, String msg) {
        showShortToastMsg(context, msg);
    }

    /**
     * to check internet connection before running some codes
     *
     * @return true if connected or false and show msg that there is not connection
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * to handle the failure msg in almost calls of the app
     * views[0] layout must be visible here to make it's childes visible
     * views[1] progress bar
     * views[2] TextView to show msg
     * views[3] ImageView to show icon / image
     * the views may have just 2 elements progressbar and textView so we check if length is > 2
     */
    public static void showFailureMsg(Throwable t, int imageResIntId, View... views) {
        View errorLayout = views[ZERO];
        makeViewVisible(errorLayout);
        View progressBar = views[ONE];
        TextView noDataTV = (TextView) views[TWO];
        ImageView noDataIV;
        if (THREE < views.length) {
            noDataIV = (ImageView) views[THREE];
            showNoDataImage(noDataIV, imageResIntId);
        }
        showProgressBar(progressBar, false);
        String errorMsg = t.getMessage();
        final String ERROR_TIMEOUT_CONNECTION = "NETWORK ERROR Read error"; //"NETWORK ERROR Read error: ssl=0xb47da400: I/O error during system call, Connection timed out"
        if (null != noDataTV) {
            Context context = noDataTV.getContext();
            Log.e(context.getClass().getSimpleName(), context.getString(R.string.throwable) + errorMsg);
            if (errorMsg.contains(ERROR_TIMEOUT_CONNECTION))
                errorMsg = context.getString(R.string.no_connection);
            showNoDataMsg(noDataTV, errorMsg);
        }
    }

    public static void showNoDataMsg(TextView noDataTV, String msg) {
        makeViewVisible(noDataTV);
        makeTypeFaceLabelStyle(noDataTV);
        if (null == msg) msg = "unknown error !";
        noDataTV.setText(msg);
    }

    public static void showNoDataImage(ImageView noDataIV, int imageResIntId) {
        makeViewVisible(noDataIV);
        loadImage(imageResIntId, noDataIV);
    }

    /**
     * simple method to show Toast Msg and control all Toast's style in the app
     * and to print the message as a log
     *
     * @param msg which will be shown
     */
    public static void showShortToastMsg(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        Log.d(context.getPackageName(), context.getString(R.string.app_name) + msg);
    }

    /**
     * use this method to convert String to List using for loop to avoid UnsupportedOperationException
     * https://stackoverflow.com/questions/2965747/why-do-i-get-an-unsupportedoperationexception-when-trying-to-remove-an-element-f
     *
     * @param stringList - string contains list each element is separated by (,)
     * @return - DES array list
     */
    public static List<String> convertStringToList(String stringList) {
        List<String> stringsList = new ArrayList<>();
        if (null != stringList) {
            String[] strings = stringList.trim().split(COMMA);
            for (int i = strings.length - ONE; i >= ZERO; i--) {
                String item = strings[i];
                if (!item.isEmpty()) stringsList.add(item);
            }
        }
        return stringsList;
    }

    /**
     * to convert list of strings to one string and separate between items by COMMA
     */
    public static String convertListToString(List<String> stringsList) {
        if (null != stringsList && !stringsList.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (String s : stringsList)
                if (null != s && !s.isEmpty()) builder.append(s).append(COMMA);
            if (builder.length() > ONE) builder.deleteCharAt(builder.lastIndexOf(COMMA));
            return builder.toString();
        } else {
            return EMPTY_STRING; // to getMyApplication all products from api as it needed " " and not needed as null in the api
        }
    }

    /**
     * to getMyApplication the current time with this pattern "yyyy-MM-dd,hh:mm:ss" 2019-04-16
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentTime() {
        Date time = new java.util.Date(System.currentTimeMillis());
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dayFormat.format(time);
    }

    /**
     * @return map contains filter keywords
     */
    public static Map<String, Object> getQueriesMap(Context context, int pageNumber) {
        SharedPreferencesHelper sharedPreferencesHelper = SharedPreferencesHelper.getInstance(context);
        String fromDate = sharedPreferencesHelper.getFromDate();
        String toDate = sharedPreferencesHelper.getToDate();
        String orderBy = getValueFromPreferencesByKey(context, R.string.order_by_list_key, R.string.order_by_list_default_value);
        String orderDate = getValueFromPreferencesByKey(context, R.string.order_date_list_key, R.string.order_date_list_default_value);
        Map<String, Object> queries = new HashMap<>();
        queries.put(QUERY_SHOW_FIELDS_KEYWORD, ARTICLE_FIELDS);
        queries.put(QUERY_PAGE_SIZE_KEYWORD, PAGE_SIZE);
        queries.put(QUERY_ORDER_BY_KEYWORD, orderBy);
        queries.put(QUERY_ORDER_date_KEYWORD, orderDate);
        if (ZERO < pageNumber) queries.put(QUERY_PAGE_KEYWORD, pageNumber);
        if (!EMPTY_STRING.equals(fromDate)) queries.put(QUERY_FROM_DATE_KEYWORD, fromDate);
        if (!EMPTY_STRING.equals(toDate)) queries.put(QUERY_TO_DATE_KEYWORD, toDate);
        return queries;
    }

    /**
     * to detect @param keyword language
     * @return 'ar' or "en
     */
    public static String getKeywordLanguage(String keyword){
        char ch = keyword.charAt(ZERO);
        Character.UnicodeBlock block = Character.UnicodeBlock.of(ch);
        if (Character.UnicodeBlock.ARABIC.equals(block)) return "ar";
        else return "en";
    }

    public static String getValueFromPreferencesByKey(Context context, int keyStringId, int defaultValueStringId){
        String key = context.getString(keyStringId);
        String defaultValue = context.getString(defaultValueStringId);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, defaultValue);
    }

    public static void openUrlInWebBrowser(Context context, String url){
        try {
            Intent openWebPage;
            openWebPage = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(openWebPage);

        } catch (ActivityNotFoundException e) {
            showShortToastMsg(context, context.getString(R.string.no_browser_msg));
        }
    }

    public static void appendStringToTextView (TextView label, String defaultText, String textToAppend){
        label.setText(defaultText);
        label.append(textToAppend);
    }

    public static void handelErrorMsg(NetworkState networkState, View... views){
        if (networkState != null && networkState.getStatus() == NetworkState.Status.FAILED) {
            showFailureMsg(new Throwable(networkState.getMsg()), android.R.drawable.stat_notify_sync, views);
        }else {
            View errorLayout = views[ZERO];
            makeViewGone(errorLayout);
        }
    }

    /**
     * to control almost spinners in the app
     * @param spinner to set it's articleAdapter
     * @param dataAdapter which will display the data and to control it's DropDownViewResource
     * @param spinnerOnItemClickedListener the run a soporific code when an item clicked
     * @param label as the spinner is invisible, and when the label is clicked it will trigger the spinner
     */
    public static void setSpinnerAdapter (final Spinner spinner, ArrayAdapter dataAdapter, SpinnerOnItemClickedListener spinnerOnItemClickedListener, TextView label){
        if (dataAdapter instanceof SpinnerAdapter){
            ((SpinnerAdapter) dataAdapter).setSpinnerOnItemClickedListener(spinnerOnItemClickedListener);
        }
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        makeTypeFaceLabelStyle(label);
        label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.performClick();
            }
        });
    }

    public static String getSectionId (Context context, int position){
        return context.getResources().getStringArray(R.array.sections_ids)[position];
    }

    public static void printLog (String statement){
        final String TAG = "APP_LOGS";
        Log.d(TAG, statement);
    }
}
