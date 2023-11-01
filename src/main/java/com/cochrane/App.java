package com.cochrane;

import org.jsoup.Connection;
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
            List<Article> articleList=reviewElements.stream()
                                    .filter( element -> (!StringUtil.isBlank(searchTopic) || !StringUtil.isBlank(element.select(".btn-link").text()))
                                                    && (element.select(".btn-link").text().toLowerCase()).contains(searchTopic.toLowerCase())
                                    )
                                        .map(reviewElement -> {
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

            Article article = articleList.get(0);
            String searchedTopicUrl = article.getUrl();
            String topic=article.getTopic();
            String baseUrl="https://www.cochranelibrary.com";

            String customUrl = "https://www.cochranelibrary.com/search?p_p_id=scolarissearchresultsportlet_WAR_scolarissearchresults&p_p_lifecycle=0&_scolarissearchresultsportlet_WAR_scolarissearchresults_searchType=basic&_scolarissearchresultsportlet_WAR_scolarissearchresults_searchBy=1&_scolarissearchresultsportlet_WAR_scolarissearchresults_searchText=" +
                    article.getTopic();
            //call url and again parse Data with Jsoup;
            Document docByTopic = Jsoup.connect(customUrl).get();

            // Find and select the elements that contain review information
            String classHierarchyByTopic = ".site-container .search-results-section-body .search-results-item";
            Elements reviewElementsByTopic = docByTopic.select(classHierarchyByTopic);
            System.out.println(" total topic count : " + reviewElementsByTopic.size());

          List<Article> articleListFromTopicSearch=reviewElementsByTopic
                          .stream()
                          .map(reviewElement -> {
                                    // Extract data from each review element {URL, Topic, Title, Author, Date, }
                                    String url = baseUrl+reviewElement.select(".result-title a").attr("href");
                                    String title = reviewElement.select(".result-title").text();
                                    String author = reviewElement.select(".search-result-authors").text();
                                    String publicationDate = reviewElement.select(".search-result-metadata-block .search-result-date").text();
                                    return new Article(url, topic, title, author, publicationDate);
                          })
                         .collect(Collectors.toList());
            System.out.println(" total topic count : " + articleListFromTopicSearch.size());
            articleListFromTopicSearch.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
