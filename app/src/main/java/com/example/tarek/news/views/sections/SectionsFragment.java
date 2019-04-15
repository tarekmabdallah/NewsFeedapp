package com.example.tarek.news.views.sections;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tarek.news.R;
import com.example.tarek.news.data.sp.SharedPreferencesHelper;
import com.example.tarek.news.models.sections.ResponseSections;
import com.example.tarek.news.models.sections.Section;
import com.example.tarek.news.views.bases.BaseDataLoaderFragment;

import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;

import static com.example.tarek.news.utils.Constants.DASH;
import static com.example.tarek.news.utils.Constants.SECTIONS_KEYWORD;
import static com.example.tarek.news.utils.Constants.SPACE;
import static com.example.tarek.news.utils.Constants.SPACE_REGEX;
import static com.example.tarek.news.utils.ViewsUtils.setSpinnerAdapter;
import static com.example.tarek.news.views.section.SectionActivity.openSectionActivity;

public class SectionsFragment extends BaseDataLoaderFragment {

    @BindView(R.id.sections_spinner_layout)
    View sectionsSpinnerLayout;
    Spinner sectionsSpinner;
    TextView sectionsLabel;
    @BindView(R.id.countries_spinner_layout)
    View countriesSpinnerLayout;
    Spinner countriesSpinner;
    TextView countriesLabel;

    private SpinnerAdapter sectionsAdapter;
    private List<Section> sectionList;

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

    @Override
    protected String getSectionId() {
        return SECTIONS_KEYWORD;
    }

    @Override
    protected Call getCall() {
        return apiServices.getSections(getSectionId(), queries);
    }

    private void setSectionsSpinner() {
        sectionsSpinner = sectionsSpinnerLayout.findViewById(R.id.spinner);
        sectionsLabel = sectionsSpinnerLayout.findViewById(R.id.spinner_label);
        sectionsAdapter = new SpinnerAdapter(activity);
        sectionsLabel.setText(activity.getString(R.string.sections_label));
        SpinnerOnItemClickedListener spinnerOnItemClickedListener = new SpinnerOnItemClickedListener() {
            @Override
            public void onSelectItem(int position) {
                Section section = sectionList.get(position);
//                sectionsLabel.setText(section.getWebTitle());
//                sectionsAdapter.notifyDataSetChanged();
                openSectionActivity(activity, section.getId(), section.getWebTitle(), false);
            }
        };
        setSpinnerAdapter(sectionsSpinner, sectionsAdapter,spinnerOnItemClickedListener, sectionsLabel);
    }

    private void setCountriesSpinner() {
        countriesSpinner = countriesSpinnerLayout.findViewById(R.id.spinner);
        countriesLabel = countriesSpinnerLayout.findViewById(R.id.spinner_label);
        countriesLabel.setText(activity.getString(R.string.countries_news_label));
        final List<String> countriesList = Arrays.asList(activity.getResources().getStringArray(R.array.countries_array));
        SpinnerAdapter countriesAdapter = new SpinnerAdapter(activity);
        countriesAdapter.addAll(countriesList);
        SpinnerOnItemClickedListener spinnerOnItemClickedListener = new SpinnerOnItemClickedListener() {
            @Override
            public void onSelectItem(int position) {
                String countryName = countriesList.get(position);
                String sectionId = countryName.toLowerCase().replaceAll(SPACE_REGEX, DASH);
                String title = countryName + SPACE + activity.getString(R.string.news_label);
//                countriesLabel.setText(countryName);
//                countriesAdapter.notifyDataSetChanged();
                openSectionActivity(activity, sectionId, title, true);
            }
        };
        setSpinnerAdapter(countriesSpinner, countriesAdapter,spinnerOnItemClickedListener, countriesLabel);
    }

    @Override
    protected void whenDataFetchedGetResponse(Object body) {
        sectionsAdapter.clear();
        if (body instanceof ResponseSections) {
            ResponseSections responseSections = (ResponseSections) body;
            sectionList = responseSections.getResponse().getResults();
            if (sectionList != null && !sectionList.isEmpty()) {
                saveSectionsListsInSP(activity, responseSections);
                for (Section section: sectionList) sectionsAdapter.add(section.getWebTitle());
            } else handleNoDataFromResponse();
        }
    }

    public static void saveSectionsListsInSP(Context context, ResponseSections responseSections){
        SharedPreferencesHelper sharedPreferencesHelper = SharedPreferencesHelper.getInstance(context);
        sharedPreferencesHelper.saveResponseSections(responseSections);
    }

    public static SectionsFragment getInstance() {
        return new SectionsFragment();
    }
}
