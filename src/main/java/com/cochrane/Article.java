package com.cochrane;


import org.jsoup.internal.StringUtil;

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
        this.url=removeCharaterAfterFull(url);
        this.topic=topic;
        this.title=title;
        this.author=author;
        this.date=dateParser(publicationDate);
    }

    private static String removeCharaterAfterFull(String url) {
        String substringToKeep = "/full";
        int index = url.indexOf(substringToKeep);
        return  (index != -1) ? url.substring(0, index + substringToKeep.length()) : url;
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
        return  url + " | " + topic + " | "+ title +" | "+ author +" | "+ date+"\n\n";
    }
}
