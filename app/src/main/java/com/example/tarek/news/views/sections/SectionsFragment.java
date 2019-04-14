package com.example.tarek.news.views.sections;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tarek.news.R;
import com.example.tarek.news.data.sp.SharedPreferencesHelper;
import com.example.tarek.news.models.sections.ResponseSections;
import com.example.tarek.news.models.sections.Section;
import com.example.tarek.news.views.bases.BaseDataLoaderFragment;

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;

import static com.example.tarek.news.utils.Constants.SECTIONS_KEYWORD;
import static com.example.tarek.news.views.section.SectionActivity.openSectionActivity;

public class SectionsFragment extends BaseDataLoaderFragment {

    @BindView(R.id.list_view)
    ListView listView;

    private SectionsAdapter sectionsAdapter;
    private List<Section> sectionList;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_sections;
    }

    @Override
    protected void initiateValues() {
        super.initiateValues();
        setListView();
    }

    @Override
    protected String getSectionId() {
        return SECTIONS_KEYWORD;
    }

    @Override
    protected Call getCall() {
        return apiServices.getSections(getSectionId(), queries);
    }

    private void setListView() {
        sectionsAdapter = new SectionsAdapter(activity);
        listView.setAdapter(sectionsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Section section = sectionList.get(position);
                openSectionActivity(activity, section.getId(), section.getWebTitle());
            }
        });
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
