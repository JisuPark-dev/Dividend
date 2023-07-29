package com.zerobase.dividends.scraper;

import com.zerobase.dividends.model.Company;
import com.zerobase.dividends.model.ScrapedResult;

public interface Scraper {
    Company scrapCompanyByTicker(String ticker);

    ScrapedResult scrap(Company company);

}
