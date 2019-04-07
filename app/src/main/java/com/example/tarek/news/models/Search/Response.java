/*
 * Copyright 2019 tarekmabdallah91@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.tarek.news.models.Search;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Response {

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
    private List<Article> items;

    @SerializedName("status")
    private String status;

    public void setUserTier(String userTier) {
        this.userTier = userTier;
    }

    public String getUserTier() {
        return userTier;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPages() {
        return pages;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setItems(List<Article> items) {
        this.items = items;
    }

    public List<Article> getItems() {
        return items;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @NonNull
    @Override
    public String toString() {
        return
                "Response{" +
                        "userTier = '" + userTier + '\'' +
                        ",total = '" + total + '\'' +
                        ",startIndex = '" + startIndex + '\'' +
                        ",pages = '" + pages + '\'' +
                        ",pageSize = '" + pageSize + '\'' +
                        ",orderBy = '" + orderBy + '\'' +
                        ",currentPage = '" + currentPage + '\'' +
                        ",items = '" + items + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}