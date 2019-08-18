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

package com.gmail.tarekmabdallah91.news.views.settings;


import android.widget.TextView;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.data.sp.SharedPreferencesHelper;
import com.gmail.tarekmabdallah91.news.views.bases.BaseFragment;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.util.Calendar;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

import static com.gmail.tarekmabdallah91.news.utils.Constants.DASH;
import static com.gmail.tarekmabdallah91.news.utils.Constants.EMPTY_STRING;
import static com.gmail.tarekmabdallah91.news.utils.Constants.ONE;
import static com.gmail.tarekmabdallah91.news.utils.Constants.makeTypeFaceBtnStyle;
import static com.gmail.tarekmabdallah91.news.utils.Constants.makeTypeFaceTextStyle;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.appendStringToTextView;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.showView;

public class DatesFragment extends BaseFragment {

    @BindView(R.id.from_date_label)
    TextView fromDateLabel;
    @BindView(R.id.to_date_label)
    TextView toDateLabel;
    @BindView(R.id.clear_dates_btn)
    TextView clearDatesBtn;

    @BindString(R.string.from_date_label)
    String fromDateMsg;
    @BindString(R.string.to_date_label)
    String toDateMsg;

    private static final int MIN_YEAR = 1900;
    private static final int MIN_MONTH = 1;
    private static final int MIN_DAY = 1;

    private SharedPreferencesHelper sharedPreferencesHelper;
    private String fromDate, toDate;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_dates;
    }

    @Override
    public void initiateValues() {
        sharedPreferencesHelper = SharedPreferencesHelper.getInstance(activity);
        makeTypeFaceTextStyle(fromDateLabel, toDateLabel);
        makeTypeFaceBtnStyle(clearDatesBtn);
        fromDate = sharedPreferencesHelper.getFromDate();
        if (!EMPTY_STRING.equals(fromDate)) appendStringToTextView(fromDateLabel, fromDateMsg, fromDate);
        toDate = sharedPreferencesHelper.getToDate();
        if (!EMPTY_STRING.equals(toDate)) appendStringToTextView(toDateLabel, toDateMsg, toDate);
        showView(clearDatesBtn, !EMPTY_STRING.equals(fromDate) || !EMPTY_STRING.equals(toDate));
    }

    @OnClick(R.id.clear_dates_btn)
    void onClickClearDatesBtn(){
        appendStringToTextView(fromDateLabel, fromDateMsg, EMPTY_STRING);
        appendStringToTextView(toDateLabel, toDateMsg, EMPTY_STRING);
        sharedPreferencesHelper.saveFromDate(EMPTY_STRING);
        sharedPreferencesHelper.saveToDate(EMPTY_STRING);
        showView(clearDatesBtn, false);
    }

    @OnClick(R.id.from_date_label)
    void onClickFromDateLabel(){
        OnDateSetListener onDateSetListener = new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // date format: "2014-02-16"
                fromDate = year + DASH + (monthOfYear + ONE) + DASH + dayOfMonth;
                appendStringToTextView(fromDateLabel, fromDateMsg, fromDate);
                sharedPreferencesHelper.saveFromDate(fromDate);
                showView(clearDatesBtn, true);
            }
        };
        setDatePicker(onDateSetListener);
    }

    @OnClick(R.id.to_date_label)
    void onClickToDateLabel(){
        OnDateSetListener onDateSetListener = new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // date format: "2014-02-16"
                toDate = year + DASH + (monthOfYear + ONE) + DASH + dayOfMonth;
                appendStringToTextView(toDateLabel, toDateMsg, toDate);
                sharedPreferencesHelper.saveToDate(toDate);
                showView(clearDatesBtn, true);
            }
        };
        setDatePicker(onDateSetListener);
    }

    private void setDatePicker(OnDateSetListener onDateSetListener){
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        new SpinnerDatePickerDialogBuilder()
                .context(activity)
                .callback(onDateSetListener)
                .spinnerTheme(R.style.NumberPickerStyle)
                .showTitle(true)
                .showDaySpinner(true)
                .defaultDate(year, month, day)
                .maxDate(year, month, day)
                .minDate(MIN_YEAR, MIN_MONTH, MIN_DAY)
                .build()
                .show();
    }
}