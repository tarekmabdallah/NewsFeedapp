package com.gmail.tarekmabdallah91.news.models.section;

import com.gmail.tarekmabdallah91.news.models.section.articles.Article;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommonResponse {

    @SerializedName("userTier")
    private String userTier;

    @SerializedName("total")
    private int total;

    @SerializedName("startIndex")
    private int startIndex;

    @SerializedName("pages")
    private int pages;

    @SerializedName("pageSize")
    private int pageSize;

    @SerializedName("orderBy")
    private String orderBy;

    @SerializedName("currentPage")
    private int currentPage;

    @SerializedName("results")
    private List<Article> articles;

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

    public void setStartIndex(int startIndex){
        this.startIndex = startIndex;
    }

    public int getStartIndex(){
        return startIndex;
    }

    public void setPages(int pages){
        this.pages = pages;
    }

    public int getPages(){
        return pages;
    }

    public void setPageSize(int pageSize){
        this.pageSize = pageSize;
    }

    public int getPageSize(){
        return pageSize;
    }

    public void setOrderBy(String orderBy){
        this.orderBy = orderBy;
    }

    public String getOrderBy(){
        return orderBy;
    }

    public void setCurrentPage(int currentPage){
        this.currentPage = currentPage;
    }

    public int getCurrentPage(){
        return currentPage;
    }

    public void setResults(List<Article> results){
        this.articles = results;
    }

    public List<Article> getResults(){
        return articles;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }

}
