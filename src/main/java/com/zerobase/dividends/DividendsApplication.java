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
            Document document = connection.get(); // 전체 html 소스
            Elements elements = document.getElementsByAttributeValue("data-test", "historical-prices");
            Element element = elements.get(0); // table 전체

            Element tbody = element.children().get(1); // thead, tbody, tfoot
            for (Element e : tbody.children()) {
                String txt = e.text();
                if (!txt.endsWith("Dividend")) {
                    continue;
                }
                String[] splits = txt.split(" ");
                String month = splits[0];
                int day = Integer.valueOf(splits[1].replace(",", ""));
                int year = Integer.valueOf(splits[2]);
                String Dividend = splits[3];
                System.out.println(year + "/" + month + "/" + day + " -> " + Dividend);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
