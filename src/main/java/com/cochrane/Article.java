package com.cochrane;

import org.jsoup.helper.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Article {
        private String url;
        private String topic;
        private String title;
        private String author;
        private String date;

    public Article(String url, String topic, String title, String author, String publicationDate) {
        this.url=url;
        this.topic=topic;
        this.title=title;
        this.author=author;
        this.date=dateParser(publicationDate);
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

    public static String dateParser(String inputDate) {
        if (StringUtil.isBlank(inputDate)) return inputDate;
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = inputDateFormat.parse(inputDate);
            String formattedDate = outputDateFormat.format(date);
            System.out.println("Original Date: " + inputDate);
            System.out.println("Formatted Date: " + formattedDate);
            return formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
            //TODO add logger.error()
            System.err.println(e.getMessage());
        }
        return inputDate;
    }

    @Override
    public String toString() {
        return  url + " | " + topic + " | "+ title +" | "+ author +" | "+ date+"\n";
        /*http://onlinelibrary.wiley.com/doi/10.1002/14651858.CD002204.pub4/full| Allergy &
        intolerance|Antifungal therapies for allergic bronchopulmonary aspergillosis in people with cystic
        fibrosis|Heather E Elphick, Kevin W Southern|2016-11-08*/
    }
}
