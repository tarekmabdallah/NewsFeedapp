package com.gmail.tarekmabdallah91.news.models.countryNews;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class ResponseCountryNews{

	@SerializedName("response")
	private Response response;

	public void setResponse(Response response){
		this.response = response;
	}

	public Response getResponse(){
		return response;
	}

	@NonNull
	@Override
 	public String toString(){
		return 
			"ResponseCountryNews{" + 
			"response = '" + response + '\'' + 
			"}";
		}
}