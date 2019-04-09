package com.example.tarek.news.models.Search;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Fields{

	@SerializedName("trailText")
	private String trailText;

	@SerializedName("standfirst")
	private String standfirst;

	@SerializedName("main")
	private String main;

	@SerializedName("body")
	private String body;

	@SerializedName("headline")
	private String headline;

	@SerializedName("byline")
	private String authorName;

	public void setTrailText(String trailText){
		this.trailText = trailText;
	}

	public String getTrailText(){
		return trailText;
	}

	public void setStandfirst(String standfirst){
		this.standfirst = standfirst;
	}

	public String getStandfirst(){
		return standfirst;
	}

	public void setMain(String main){
		this.main = main;
	}

	public String getMain(){
		return main;
	}

	public void setBody(String body){
		this.body = body;
	}

	public String getBody(){
		return body;
	}

	public void setHeadline(String headline){
		this.headline = headline;
	}

	public String getHeadline(){
		return headline;
	}

	public void setAuthorName(String authorName){
		this.authorName = authorName;
	}

	public String getAuthorName(){
		return authorName;
	}

	@NonNull
	@Override
 	public String toString(){
		return 
			"Fields{" + 
			"trailText = '" + trailText + '\'' + 
			",standfirst = '" + standfirst + '\'' + 
			",main = '" + main + '\'' + 
			",body = '" + body + '\'' + 
			",headline = '" + headline + '\'' + 
			",authorName = '" + authorName + '\'' +
			"}";
		}
}