package com.example.tarek.news.models.sections;

import com.google.gson.annotations.SerializedName;

public class Edition {

	@SerializedName("code")
	private String code;

	@SerializedName("apiUrl")
	private String apiUrl;

	@SerializedName("webUrl")
	private String webUrl;

	@SerializedName("webTitle")
	private String webTitle;

	@SerializedName("id")
	private String id;

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
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

	@Override
 	public String toString(){
		return 
			"Edition{" +
			"code = '" + code + '\'' + 
			",apiUrl = '" + apiUrl + '\'' + 
			",webUrl = '" + webUrl + '\'' + 
			",webTitle = '" + webTitle + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}