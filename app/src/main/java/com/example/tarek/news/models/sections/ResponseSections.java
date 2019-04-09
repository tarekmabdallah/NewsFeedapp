package com.example.tarek.news.models.sections;

import com.google.gson.annotations.SerializedName;

public class ResponseSections{

	@SerializedName("response")
	private Response response;

	public void setResponse(Response response){
		this.response = response;
	}

	public Response getResponse(){
		return response;
	}

	@Override
 	public String toString(){
		return 
			"ResponseSections{" + 
			"response = '" + response + '\'' + 
			"}";
		}
}