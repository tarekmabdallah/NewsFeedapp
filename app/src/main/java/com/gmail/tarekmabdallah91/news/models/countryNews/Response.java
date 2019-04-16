package com.gmail.tarekmabdallah91.news.models.countryNews;

import android.support.annotation.NonNull;

import com.gmail.tarekmabdallah91.news.models.section.CommonResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response extends CommonResponse {

	@SerializedName("leadContent")
	private List<LeadContentItem> leadContent;

	@SerializedName("tag")
	private Tag tag;


	public void setLeadContent(List<LeadContentItem> leadContent){
		this.leadContent = leadContent;
	}

	public List<LeadContentItem> getLeadContent(){
		return leadContent;
	}


	public void setTag(Tag tag){
		this.tag = tag;
	}

	public Tag getTag(){
		return tag;
	}

	@NonNull
	@Override
 	public String toString(){
		return 
			"Response{" + 
			"userTier = '" + getUserTier() + '\'' +
			",total = '" + getTotal() + '\'' +
			",startIndex = '" + getStartIndex() + '\'' +
			",pages = '" + getPages() + '\'' +
			",leadContent = '" + leadContent + '\'' + 
			",pageSize = '" + getPageSize() + '\'' +
			",orderBy = '" + getOrderBy() + '\'' +
			",tag = '" + tag + '\'' + 
			",currentPage = '" + getCurrentPage() + '\'' +
			",results = '" + getResults() + '\'' +
			",status = '" + getStatus() + '\'' +
			"}";
		}
}