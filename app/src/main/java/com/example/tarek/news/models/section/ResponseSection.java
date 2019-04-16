
package com.example.tarek.news.models.section;

import com.google.gson.annotations.SerializedName;

public class ResponseSection {

    @SerializedName("response")
    private CommonResponse response;

    public CommonResponse getResponse() {
        return response;
    }

    public void setResponse(CommonResponse response) {
        this.response = response;
    }

}
