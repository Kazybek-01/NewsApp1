package com.example.newsapp;

public class Articles {

    private Source source;

    private String date;
    private String author;
    private String title;
    private String description;
    private String publishedAt;
    private String url;
    private String urlToImage;

    private String titleOfNews;
    private String timeAgo;
    private String dateDetails;

    public String getTitleOfNews() {
        return titleOfNews;
    }

    public String getTimeAgo() {
        return timeAgo;
    }

    public String getDateDetails() {
        return dateDetails;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public Source getSource() {
        return source;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }
}
