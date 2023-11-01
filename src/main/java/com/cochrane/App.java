package com.cochrane;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        //cochraneLib(args[0]);
        cochraneLib("Allergy");

    }

    static void cochraneLib(String searchTopic){
        System.out.println(" searched Topic : "+searchTopic);
        //Website URL.
        String cochraneUrl = "https://www.cochranelibrary.com/cdsr/reviews/topics";
        try {
            // Connect to the Cochrane Library's review collection page
            Document doc = Jsoup.connect(cochraneUrl).get();

            // Find and select the elements that contain review information
            String classHierarchy=".site-container #content .columns-1 .portlet-content-container .portlet-body .container .row-fluid .dl-section .browse-by-list-item";
            Elements reviewElements = doc.select(classHierarchy);
            System.out.println(" total topic count : "+reviewElements.size());
            List<Article> articleList=reviewElements
                                        .stream()
                                        .filter( element -> {
                                            boolean isEmpty=StringUtil.isBlank(searchTopic)
                                                            && StringUtil.isBlank(element.select(".btn-link").text());
                                             String topicName=element.select(".btn-link").text();
                                            if (isEmpty) return !isEmpty;
                                            boolean matching=topicName.toLowerCase().contains(searchTopic.toLowerCase());
                                            return matching;
                                        })
                                        .map(reviewElement -> {
                                            // Extract data from each review element {URL, Topic, Title, Author, Date, }
                                            String url = reviewElement.select(".browse-by-list-item a").attr("href");
                                            String topic = reviewElement.select(".btn-link").text();
                                            String title = reviewElement.select(".btn-link").text();
                                            String author = reviewElement.select(".author-class").text();
                                            String publicationDate = reviewElement.select(".date-class").text();
                                           return new Article(url, topic, title, author, publicationDate);
                                        })
                                        .collect(Collectors.toList());
            //Printing collected topic urls
            articleList.forEach(article ->   System.out.println("Topic Info "+article +"\n--------------------------------------"));

      if (Objects.isNull(articleList) || articleList.isEmpty()){
          System.err.println("Provide Topic is not currently available in Library ");
          return;
      }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
