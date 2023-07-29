package com.zerobase.dividends;

import com.zerobase.dividends.model.Company;
import com.zerobase.dividends.model.ScrapedResult;
import com.zerobase.dividends.scraper.YahooFinanceScraper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class DividendsApplication {

    public static void main(String[] args) {
//        SpringApplication.run(DividendsApplication.class, args);
        YahooFinanceScraper scraper = new YahooFinanceScraper();
        var result = scraper.scrap(Company.builder().ticker("O").build());
        System.out.println("result = " + result);
    }

}
