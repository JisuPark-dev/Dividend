package com.zerobase.dividends;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class DividendsApplication {

    public static void main(String[] args) {
//        SpringApplication.run(DividendsApplication.class, args);
        try {
            Connection connection = Jsoup.connect("https://finance.yahoo.com/quote/COKE/history?period1=99100800&period2=1690416000&interval=1mo&filter=history&frequency=1mo&includeAdjustedClose=true");
            Document document = connection.get();
            Elements elements = document.getElementsByAttributeValue("data-test", "historical-prices");
            Element element = elements.get(0);

            System.out.println(element);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
