
package com.example.tarek.news.models.section;

import com.google.gson.annotations.SerializedName;

public class ResponseSection {

    @SerializedName("response")
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
