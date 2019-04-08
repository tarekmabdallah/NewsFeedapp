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

public class Tags {

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("apiUrl")
    private String apiUrl;

    @SerializedName("references")
    private List<Object> references;

    @SerializedName("twitterHandle")
    private String twitterHandle;

    @SerializedName("webUrl")
    private String webUrl;

    @SerializedName("webTitle")
    private String webTitle;

    @SerializedName("bio")
    private String bio;

    @SerializedName("id")
    private String id;

    @SerializedName("type")
    private String type;

    @SerializedName("bylineImageUrl")
    private String bylineImageUrl;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setReferences(List<Object> references) {
        this.references = references;
    }

    public List<Object> getReferences() {
        return references;
    }

    public void setTwitterHandle(String twitterHandle) {
        this.twitterHandle = twitterHandle;
    }

    public String getTwitterHandle() {
        return twitterHandle;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBio() {
        return bio;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setBylineImageUrl(String bylineImageUrl) {
        this.bylineImageUrl = bylineImageUrl;
    }

    public String getBylineImageUrl() {
        return bylineImageUrl;
    }

    @NonNull
    @Override
    public String toString() {
        return
                "Tags{" +
                        "firstName = '" + firstName + '\'' +
                        ",lastName = '" + lastName + '\'' +
                        ",apiUrl = '" + apiUrl + '\'' +
                        ",references = '" + references + '\'' +
                        ",twitterHandle = '" + twitterHandle + '\'' +
                        ",webUrl = '" + webUrl + '\'' +
                        ",webTitle = '" + webTitle + '\'' +
                        ",bio = '" + bio + '\'' +
                        ",id = '" + id + '\'' +
                        ",type = '" + type + '\'' +
                        ",bylineImageUrl = '" + bylineImageUrl + '\'' +
                        "}";
    }
}