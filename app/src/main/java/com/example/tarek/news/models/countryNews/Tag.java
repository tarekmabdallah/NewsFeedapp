package com.example.tarek.news.models.countryNews;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Tag{

	@SerializedName("sectionName")
	private String sectionName;

	@SerializedName("apiUrl")
	private String apiUrl;

	@SerializedName("webUrl")
	private String webUrl;

	@SerializedName("webTitle")
	private String webTitle;

	@SerializedName("id")
	private String id;

	@SerializedName("sectionId")
	private String sectionId;

	@SerializedName("type")
	private String type;

	public void setSectionName(String sectionName){
		this.sectionName = sectionName;
	}

	public String getSectionName(){
		return sectionName;
	}

	public void setApiUrl(String apiUrl){
		this.apiUrl = apiUrl;
	}

	public String getApiUrl(){
		return apiUrl;
	}

	public void setWebUrl(String webUrl){
		this.webUrl = webUrl;
	}

	public String getWebUrl(){
		return webUrl;
	}

	public void setWebTitle(String webTitle){
		this.webTitle = webTitle;
	}

	public String getWebTitle(){
		return webTitle;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setSectionId(String sectionId){
		this.sectionId = sectionId;
	}

	public String getSectionId(){
		return sectionId;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	@NonNull
	@Override
 	public String toString(){
		return 
			"Tag{" + 
			"sectionName = '" + sectionName + '\'' + 
			",apiUrl = '" + apiUrl + '\'' + 
			",webUrl = '" + webUrl + '\'' + 
			",webTitle = '" + webTitle + '\'' + 
			",id = '" + id + '\'' + 
			",sectionId = '" + sectionId + '\'' + 
			",type = '" + type + '\'' + 
			"}";
		}
}