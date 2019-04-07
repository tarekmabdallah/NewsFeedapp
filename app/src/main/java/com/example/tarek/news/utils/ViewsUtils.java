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

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tarek.news.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.example.tarek.news.utils.Constants.COMMA;
import static com.example.tarek.news.utils.Constants.EMPTY_STRING;
import static com.example.tarek.news.utils.Constants.ONE;
import static com.example.tarek.news.utils.Constants.VALID_EMAIL_ADDRESS_REGEX;
import static com.example.tarek.news.utils.Constants.ZERO;
import static com.example.tarek.news.utils.Constants.makeTypeFaceLabelStyle;

public class ViewsUtils {
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

    public static String getTextFromEditText(EditText editText) {
        return editText.getText().toString().trim();
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
    public static void restartActivity(AppCompatActivity activity) {
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
     * used when we need to block the UI while some data get loaded and the progressBar is visible
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
        boolean isConnected = networkInfo != null && networkInfo.isConnected();
        if (!isConnected) showShortToastMsg(context, context.getString(R.string.no_connection));
        return isConnected;
    }

    /**
     * to handle the failure msg in almost calls of the app
     */
    public static void showFailureMsg(View progressBar, TextView noDataTV, Throwable t) {
        showProgressBar(progressBar, false);
        String errorMsg = t.getMessage();
        if (null != noDataTV) {
            Context context = noDataTV.getContext();
            Log.e(context.getClass().getSimpleName(), context.getString(R.string.throwable) + errorMsg);
            if (!isConnected(context)) errorMsg = context.getString(R.string.no_connection);
            showNoDataMsg(noDataTV, errorMsg);
        }
    }

    private static void showNoDataMsg(TextView noDataTV, String msg) {
        makeViewVisible(noDataTV);
        makeTypeFaceLabelStyle(noDataTV);
        if (null == msg) msg = "unknown error !";
        noDataTV.setText(msg);
    }

    /**
     * simple method to show Toast Msg and control all Toast's style in the app
     * and to print the message as a log
     *
     * @param msg which will be shown
     */
    public static void showShortToastMsg(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        Log.d(context.getPackageName(), "MITCHA_LOG : " + msg);
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
            return EMPTY_STRING; // to get all products from api as it needed " " and not needed as null in the api
        }
    }

    /**
     * to get the current time with this pattern "yyyy-MM-dd,hh:mm:ss"
     */
    @SuppressLint("SimpleDateFormat")
    static String getCurrentTime() {
        Date time = new java.util.Date(System.currentTimeMillis());
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd,hh:mm:ss");
        return dayFormat.format(time);
    }

    /**
     * to handle the error which contains model state keyword
     */
    private static void handleModelStateCase(TextView noDataTV, String msg) {
        if (null != noDataTV) {
            makeViewGone(noDataTV);
            showToastMsgOnFailLoadingData(noDataTV.getContext(), msg);
        }
    }

}
