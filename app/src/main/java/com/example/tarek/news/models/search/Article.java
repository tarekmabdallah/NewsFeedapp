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

package com.example.tarek.news.models.search;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Article {

    @SerializedName("sectionName")
    private String sectionName;

    @SerializedName("pillarName")
    private String pillarName;

    @SerializedName("webPublicationDate")
    private String webPublicationDate;

    @SerializedName("apiUrl")
    private String apiUrl;

    @SerializedName("webUrl")
    private String webUrl;

    @SerializedName("isHosted")
    private boolean isHosted;

    @SerializedName("pillarId")
    private String pillarId;

    @SerializedName("webTitle")
    private String webTitle;

    @SerializedName("id")
    private String id;

    @SerializedName("sectionId")
    private String sectionId;

    @SerializedName("type")
    private String type;

    @SerializedName("fields")
    private Fields fields;

    @SerializedName("tags")
    private List<Tags> tags; //it was used just to get the author name, and now it returns also in fields

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setPillarName(String pillarName) {
        this.pillarName = pillarName;
    }

    public String getPillarName() {
        return pillarName;
    }

    public void setWebPublicationDate(String webPublicationDate) {
        this.webPublicationDate = webPublicationDate;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setIsHosted(boolean isHosted) {
        this.isHosted = isHosted;
    }

    public boolean isIsHosted() {
        return isHosted;
    }

    public void setPillarId(String pillarId) {
        this.pillarId = pillarId;
    }

    public String getPillarId() {
        return pillarId;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    public List<Tags> getTags() {
        return tags;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }

    public Fields getFields() {
        return fields;
    }

    @NonNull
    @Override
    public String toString() {
        return
                "Article{" +
                        "sectionName = '" + sectionName + '\'' +
                        ",pillarName = '" + pillarName + '\'' +
                        ",webPublicationDate = '" + webPublicationDate + '\'' +
                        ",apiUrl = '" + apiUrl + '\'' +
                        ",webUrl = '" + webUrl + '\'' +
                        ",isHosted = '" + isHosted + '\'' +
                        ",pillarId = '" + pillarId + '\'' +
                        ",webTitle = '" + webTitle + '\'' +
                        ",id = '" + id + '\'' +
                        ",sectionId = '" + sectionId + '\'' +
                        ",type = '" + type + '\'' +
                        ",fields = '" + fields + '\'' +
                        ",tags = '" + tags + '\'' +
                        "}";
    }
}