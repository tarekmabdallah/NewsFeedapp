package com.gmail.tarekmabdallah91.news.models.countryNews;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LeadContentItem{

	@SerializedName("webPublicationDate")
	private String webPublicationDate;

	@SerializedName("references")
	private List<Object> references;

	@SerializedName("isHosted")
	private boolean isHosted;

	@SerializedName("webTitle")
	private String webTitle;

	@SerializedName("sectionId")
	private String sectionId;

	@SerializedName("type")
	private String type;

	@SerializedName("tags")
	private List<Object> tags;

	@SerializedName("sectionName")
	private String sectionName;

	@SerializedName("pillarName")
	private String pillarName;

	@SerializedName("apiUrl")
	private String apiUrl;

	@SerializedName("webUrl")
	private String webUrl;

	@SerializedName("pillarId")
	private String pillarId;

	@SerializedName("id")
	private String id;

	public void setWebPublicationDate(String webPublicationDate){
		this.webPublicationDate = webPublicationDate;
	}

	public String getWebPublicationDate(){
		return webPublicationDate;
	}

	public void setReferences(List<Object> references){
		this.references = references;
	}

	public List<Object> getReferences(){
		return references;
	}

	public void setIsHosted(boolean isHosted){
		this.isHosted = isHosted;
	}

	public boolean isIsHosted(){
		return isHosted;
	}

	public void setWebTitle(String webTitle){
		this.webTitle = webTitle;
	}

	public String getWebTitle(){
		return webTitle;
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

	public void setTags(List<Object> tags){
		this.tags = tags;
	}

	public List<Object> getTags(){
		return tags;
	}

	public void setSectionName(String sectionName){
		this.sectionName = sectionName;
	}

	public String getSectionName(){
		return sectionName;
	}

	public void setPillarName(String pillarName){
		this.pillarName = pillarName;
	}

	public String getPillarName(){
		return pillarName;
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

	public void setPillarId(String pillarId){
		this.pillarId = pillarId;
	}

	public String getPillarId(){
		return pillarId;
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
			"LeadContentItem{" + 
			"webPublicationDate = '" + webPublicationDate + '\'' + 
			",references = '" + references + '\'' + 
			",isHosted = '" + isHosted + '\'' + 
			",webTitle = '" + webTitle + '\'' + 
			",sectionId = '" + sectionId + '\'' + 
			",type = '" + type + '\'' + 
			",tags = '" + tags + '\'' + 
			",sectionName = '" + sectionName + '\'' + 
			",pillarName = '" + pillarName + '\'' + 
			",apiUrl = '" + apiUrl + '\'' + 
			",webUrl = '" + webUrl + '\'' + 
			",pillarId = '" + pillarId + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}