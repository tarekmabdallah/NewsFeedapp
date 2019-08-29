/*
 *
 * Copyright 2019 tarekmabdallah91@gmail.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.gmail.tarekmabdallah91.news.models.newsDbPages;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "news")
public class NewsDbPage implements Parcelable {

    @PrimaryKey (autoGenerate = true)
    private int id;
    private int pageNumber;
//    private PagedList<Article> articles;

    public NewsDbPage (){

    }
    private NewsDbPage(Parcel in) {
        pageNumber = in.readInt();
    }

    public static final Creator<NewsDbPage> CREATOR = new Creator<NewsDbPage>() {
        @Override
        public NewsDbPage createFromParcel(Parcel in) {
            return new NewsDbPage(in);
        }

        @Override
        public NewsDbPage[] newArray(int size) {
            return new NewsDbPage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pageNumber);
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

//    public PagedList<Article> getArticles() {
//        return articles;
//    }

//    public void setArticles(PagedList<Article> articles) {
//        this.articles = articles;
//    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static Creator<NewsDbPage> getCREATOR() {
        return CREATOR;
    }
}
