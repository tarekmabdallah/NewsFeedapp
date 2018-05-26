/*
Copyright 2018 tarekmabdallah91@gmail.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.example.tarek.newsfeedapp.article;


public class Article {

    private final String title;
    private final String section;
    private final String author;
    private final String date;
    private final String url;

    public Article (String title , String section , String author , String date , String url){
        this.title = title;
        this.section = section;
        this.author = author;
        this.date = date;
        this.url = url ;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    public String getSection() {
        return section;
    }

    public String getTitle() {
        return title;
    }
}
