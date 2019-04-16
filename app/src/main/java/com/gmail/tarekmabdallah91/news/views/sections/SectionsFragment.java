package com.gmail.tarekmabdallah91.news.views.sections;

import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.views.bases.BaseFragment;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

import static com.gmail.tarekmabdallah91.news.utils.Constants.DASH;
import static com.gmail.tarekmabdallah91.news.utils.Constants.SPACE_REGEX;
import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.setSpinnerAdapter;
import static com.gmail.tarekmabdallah91.news.views.section.SectionActivity.openSectionActivity;

public class SectionsFragment extends BaseFragment {

    @BindView(R.id.sections_spinner_layout)
    View sectionsSpinnerLayout;
    @BindView(R.id.countries_spinner_layout)
    View countriesSpinnerLayout;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_sections;
    }

    @Override
    protected void initiateValues() {
        super.initiateValues();
        setSectionsSpinner();
        setCountriesSpinner();
    }

    private void setSectionsSpinner() {
        Spinner sectionsSpinner = sectionsSpinnerLayout.findViewById(R.id.spinner);
        final TextView sectionsLabel = sectionsSpinnerLayout.findViewById(R.id.spinner_label);
        sectionsLabel.setText(activity.getString(R.string.sections_label));
        final List<String> sectionNamesList = Arrays.asList(activity.getResources().getStringArray(R.array.sections_labels));
        final List<String> sectionIdsList = Arrays.asList(activity.getResources().getStringArray(R.array.sections_ids));
        final SpinnerAdapter sectionsAdapter = new SpinnerAdapter(activity);
        sectionsAdapter.addAll(sectionNamesList);
        SpinnerOnItemClickedListener spinnerOnItemClickedListener = new SpinnerOnItemClickedListener() {
            @Override
            public void onSelectItem(int position) {
                setSpinnerOnItemClickedListener(position,false, sectionNamesList, sectionIdsList, sectionsLabel, sectionsAdapter);
            }
        };
        setSpinnerAdapter(sectionsSpinner, sectionsAdapter,spinnerOnItemClickedListener, sectionsLabel);
    }

    private void setCountriesSpinner() {
        Spinner countriesSpinner = countriesSpinnerLayout.findViewById(R.id.spinner);
        final TextView countriesLabel = countriesSpinnerLayout.findViewById(R.id.spinner_label);
        countriesLabel.setText(activity.getString(R.string.countries_news_label));
        final List<String> countriesNamesList = Arrays.asList(activity.getResources().getStringArray(R.array.countries_labels));
        final List<String> countriesIdsList = Arrays.asList(activity.getResources().getStringArray(R.array.countries_ids));
        final SpinnerAdapter countriesAdapter = new SpinnerAdapter(activity);
        countriesAdapter.addAll(countriesNamesList);
        SpinnerOnItemClickedListener spinnerOnItemClickedListener = new SpinnerOnItemClickedListener() {
            @Override
            public void onSelectItem(int position) {
                setSpinnerOnItemClickedListener(position, true, countriesNamesList, countriesIdsList, countriesLabel, countriesAdapter);
            }
        };
        setSpinnerAdapter(countriesSpinner, countriesAdapter,spinnerOnItemClickedListener, countriesLabel);
    }

    private void setSpinnerOnItemClickedListener (int position, boolean isContrySection , List<String> labels, List<String> ids, TextView labelTV, SpinnerAdapter adapter){
        String countryName = labels.get(position);
        String sectionId = ids.get(position).toLowerCase().replaceAll(SPACE_REGEX, DASH);
        labelTV.setText(countryName);
        adapter.notifyDataSetChanged();
        openSectionActivity(activity, sectionId, countryName, isContrySection);
    }

    public static SectionsFragment getInstance() {
        return new SectionsFragment();
    }
}
