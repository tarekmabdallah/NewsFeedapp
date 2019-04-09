package com.example.tarek.news.models.sections;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response{

	@SerializedName("userTier")
	private String userTier;

	@SerializedName("total")
	private int total;

	@SerializedName("results")
	private List<Section> results;

	@SerializedName("status")
	private String status;

	public void setUserTier(String userTier){
		this.userTier = userTier;
	}

	public String getUserTier(){
		return userTier;
	}

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setResults(List<Section> results){
		this.results = results;
	}

	public List<Section> getResults(){
		return results;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"userTier = '" + userTier + '\'' + 
			",total = '" + total + '\'' + 
			",results = '" + results + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}