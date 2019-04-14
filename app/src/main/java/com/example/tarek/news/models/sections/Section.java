package com.example.tarek.news.models.sections;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Section {

	@SerializedName("edition")
	private List<Edition> editions;

	@SerializedName("apiUrl")
	private String apiUrl;

	@SerializedName("webUrl")
	private String webUrl;

	@SerializedName("webTitle")
	private String webTitle;

	@SerializedName("id")
	private String id;

	@SerializedName("activeSponsorships")
	private List<ActiveSponsorshipsItem> activeSponsorships;

	public void setEditions(List<Edition> editions){
		this.editions = editions;
	}

	public List<Edition> getEditions(){
		return editions;
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

	public void setActiveSponsorships(List<ActiveSponsorshipsItem> activeSponsorships){
		this.activeSponsorships = activeSponsorships;
	}

	public List<ActiveSponsorshipsItem> getActiveSponsorships(){
		return activeSponsorships;
	}

	@NonNull
	@Override
 	public String toString(){
		return 
			"Section{" +
			"editions : '" + editions + '\'' +
			",apiUrl : '" + apiUrl + '\'' +
			",webUrl : '" + webUrl + '\'' +
			",webTitle : '" + webTitle + '\'' +
			",id : '" + id + '\'' +
			",activeSponsorships : '" + activeSponsorships + '\'' +
			"}";
		}
}