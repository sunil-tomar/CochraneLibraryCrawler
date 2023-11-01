package com.cochrane;

public class Article {
        private String url;
        private String topic;
        private String title;
        private String author;
        private String date;
        private String reviews;

    public Article(String url, String topic, String title, String author, String publicationDate) {
        this.url=url;
        this.topic=topic;
        this.title=title;
        this.author=author;
        this.date=publicationDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "Article{" +
                "url='" + url + '\'' +
                ", topic='" + topic + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", date='" + date + '\'' +
                ", reviews='" + reviews + '\'' +
                '}';
    }
}
