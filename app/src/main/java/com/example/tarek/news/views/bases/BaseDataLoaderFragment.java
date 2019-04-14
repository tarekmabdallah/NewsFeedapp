package com.example.tarek.news.views.bases;

import com.example.tarek.news.apis.APIClient;
import com.example.tarek.news.apis.APIServices;

import java.util.Map;

import retrofit2.Call;

import static com.example.tarek.news.apis.APIClient.getResponse;
import static com.example.tarek.news.utils.ViewsUtils.getQueriesMap;

public abstract class BaseDataLoaderFragment extends BaseFragment {

    protected APIServices apiServices;
    protected Map<String, Object> queries;
    protected Call call;

    @Override
    protected void initiateValues() {
        apiServices = APIClient.getInstance(activity).create(APIServices.class);
        queries = getQueriesMap(activity);
    }

    @Override
    protected void initiateValuesAfterCheckSaveInstanceState() {
        call = getCall();
    }

    protected Call getCall(){
        return apiServices.getArticlesBySection(getSectionId(), queries);
    }

    protected abstract String getSectionId();

    protected void callAPi() {
        getResponse(call, this);
    }
}
