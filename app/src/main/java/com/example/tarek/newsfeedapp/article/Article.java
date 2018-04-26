package com.example.tarek.newsfeedapp.article;


public class Article {

    private final String title;
    private final String section;
    private final String author;
    private final String date;
    private final String url;

    public Article (String title , String section , String author , String date ,String url){
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
