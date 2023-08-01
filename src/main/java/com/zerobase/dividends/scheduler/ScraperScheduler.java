package com.zerobase.dividends.scheduler;

import com.zerobase.dividends.model.Company;
import com.zerobase.dividends.model.ScrapedResult;
import com.zerobase.dividends.persist.CompanyRepository;
import com.zerobase.dividends.persist.DividendRepository;
import com.zerobase.dividends.persist.entity.CompanyEntity;
import com.zerobase.dividends.persist.entity.DividendEntity;
import com.zerobase.dividends.scraper.Scraper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.zerobase.dividends.model.constants.CacheKey.KEY_FINANCE;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScraperScheduler {
    private final CompanyRepository companyRepository;
    private final Scraper yahooFinanceScraper;
    private final DividendRepository dividendRepository;

    // 일정 주기마다 스크래핑 수행
    // 레디스 캐시에 있는 finance 값은 다 비운다는 뜻임.
    @CacheEvict(value = KEY_FINANCE, allEntries = true)
    @Scheduled(cron = "${scheduler.scrap.yahoo}")
    public void yahooFinanceScheduling() {
        log.info("scraping scheduler is started");
        // 저장된 회사 목록 조회
        List<CompanyEntity> companies = this.companyRepository.findAll();
        // 회사마다 배당금 정보를 새로 스크래핑
        for (var company : companies) {
            log.info("scraping scheduler is started -> " + company.getName());
            ScrapedResult scrapResult = this.yahooFinanceScraper.scrap(Company.builder()
                    .name(company.getName())
                    .ticker(company.getTicker())
                    .build());
            // 스크래핑한 배당금 정보 중에 데이터베이스에 없는 값은 저장
            scrapResult.getDividends().stream()
                    // 디비든 모델을 디비든 엔티티로 매핑함
                    .map(e -> new DividendEntity(company.getId(), e))
                    // 엘리먼트 하나씩 디비든 레퍼지토리에 삽입
                    .forEach(e->{
                        boolean exists = this.dividendRepository.existsByCompanyIdAndDateTime(e.getCompanyId(), e.getDateTime());
                        if(!exists){
                            this.dividendRepository.save(e);
                        }
                    });
            //연속적으로 스크래핑 대상 사이트 서버에 요청을 날리지 않도록 일시정지
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
