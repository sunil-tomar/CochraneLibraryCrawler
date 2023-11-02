package com.cochrane;

import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.cochrane.FileHandling.writeTextDataIntoFile;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        try {
            cochraneLibCrawler(args[0]);
        }catch (Exception e){
            System.err.println(" please provide topic as input in argument 1 \n" + e.getMessage());
            e.printStackTrace();
        }
    }

    static void cochraneLibCrawler(String searchTopic){
        System.out.println(" searching for Topic : "+searchTopic);
        //Website URL.
        String cochraneUrl = "https://www.cochranelibrary.com/cdsr/reviews/topics";
        try {
            // Connect to the Cochrane Library's review collection page
            Document doc = Jsoup.connect(cochraneUrl).get();

            // Find and select the elements that contain review information
            String classHierarchy=".site-container #content .columns-1 .portlet-content-container .portlet-body .container .row-fluid .dl-section .browse-by-list-item";
            Elements reviewElements = doc.select(classHierarchy);

            //Parsing data to collect Topic from browsed list.
            Article article=getArticleForProvidedTopic(reviewElements, searchTopic);

      if (Objects.isNull(article)){
          System.err.println("Provide Topic is not currently available in Library ");
          return;
      }
            String topic=article.getTopic();
            final String baseUrl="https://www.cochranelibrary.com";
            final String customUrl = "https://www.cochranelibrary.com/search" +
                    "?p_p_id=scolarissearchresultsportlet_WAR_scolarissearchresults" +
                    "&p_p_lifecycle=0" +
                    "&_scolarissearchresultsportlet_WAR_scolarissearchresults_searchType=basic" +
                    "&_scolarissearchresultsportlet_WAR_scolarissearchresults_searchBy=1" +
                    "&_scolarissearchresultsportlet_WAR_scolarissearchresults_searchText=" +
                    article.getTopic();

            //call url with provided topic;
            Document docByTopic = Jsoup.connect(customUrl).get();

            // Find and select the elements that contain review information
            String classHierarchyByTopic = ".site-container .search-results-section-body .search-results-item";
            Elements currentElementChunk = docByTopic.select(classHierarchyByTopic);

            //Parsing Data from Element to Article List
            List<Article> articleListFromTopicSearch=parseReviewForTopic(currentElementChunk, baseUrl, topic);

            //Converting data to text format
            String textData=articleListFromTopicSearch.stream().map(Article::toString).collect(Collectors.joining(","));
            //Writing to file.
            writeTextDataIntoFile(topic, textData);

            System.out.println("Search completed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Article getArticleForProvidedTopic(Elements reviewElements,String searchTopic) {
        Optional<Article> article=reviewElements.stream()
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
                }).findAny();
        return article.orElse(null);
    }

    private static List<Article> parseReviewForTopic(Elements currentElementChunk, String baseUrl, String topic) {
       return currentElementChunk
                .stream()
                .map(reviewElement -> {
                    String url = baseUrl+reviewElement.select(".result-title a").attr("href");
                    String title = reviewElement.select(".result-title").text();
                    String author = reviewElement.select(".search-result-authors").text();
                    String publicationDate = reviewElement.select(".search-result-metadata-block .search-result-date").text();
                    return new Article(url, topic, title, author, publicationDate);
                })
                .collect(Collectors.toList());
    }


}
