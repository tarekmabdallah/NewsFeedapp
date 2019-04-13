
package com.example.tarek.news.models.section;

import com.example.tarek.news.models.section.articles.Article;
import com.example.tarek.news.models.sections.Edition;
import com.example.tarek.news.models.sections.Section;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {

    @SerializedName("currentPage")
    private Long currentPage;

    @SerializedName("edition")
    private Edition edition;

    @SerializedName("pageSize")
    private Long pageSize;

    @SerializedName("pages")
    private Long pages;

    @SerializedName("results")
    private List<Article> articles;

    @SerializedName("section")
    private Section section;

    @SerializedName("startIndex")
    private Long startIndex;

    @SerializedName("status")
    private String status;

    @SerializedName("total")
    private Long total;

    @SerializedName("userTier")
    private String userTier;

    public Long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getPages() {
        return pages;
    }

    public void setPages(Long pages) {
        this.pages = pages;
    }

    public List<Article> getItems() {
        return articles;
    }

    public void setResults(List<Article> results) {
        this.articles = results;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Long getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Long startIndex) {
        this.startIndex = startIndex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getUserTier() {
        return userTier;
    }

    public void setUserTier(String userTier) {
        this.userTier = userTier;
    }

}
