package com.cochrane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Objects;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        //Website URL.
        String cochraneUrl = "https://www.cochranelibrary.com/cdsr/reviews/topics";
        try {
            // Connect to the Cochrane Library's review collection page
            Document doc = Jsoup.connect(cochraneUrl).get();

            // Find and select the elements that contain review information
            Elements reviewElements = doc.select(".site-container");

            for (Element reviewElement : reviewElements) {
                // Extract data from each review element
                String title = reviewElement.select(".title-class").text();
                String author = reviewElement.select(".author-class").text();
                String publicationDate = reviewElement.select(".date-class").text();

                // You can save this data to a database, file, or process it as needed
                System.out.println("Title: " + title);
                System.out.println("Author: " + author);
                System.out.println("Publication Date: " + publicationDate);
                System.out.println("--------------------------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
