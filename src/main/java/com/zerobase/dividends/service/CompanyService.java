package com.zerobase.dividends.service;

import com.zerobase.dividends.model.Company;
import com.zerobase.dividends.model.ScrapedResult;
import com.zerobase.dividends.persist.CompanyRepository;
import com.zerobase.dividends.persist.DividendRepository;
import com.zerobase.dividends.persist.entity.CompanyEntity;
import com.zerobase.dividends.persist.entity.DividendEntity;
import com.zerobase.dividends.scraper.Scraper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.Trie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final Trie trie;
    private final Scraper scraper;
    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    public Company save(String ticker) {
        boolean existsByTicker = this.companyRepository.existsByTicker(ticker);
        if (existsByTicker) {
            throw new RuntimeException("already exist ticker -> " +ticker);
        }
        return this.storeCompanyAndDividend(ticker);
    }

    public Page<CompanyEntity> getAllCompany(final Pageable pageable) {
        return this.companyRepository.findAll(pageable);
    }

    // 입력된 ticker를 통해서 회사도 저장하고, 회사 배당금 내역도 저장
    private Company storeCompanyAndDividend(String ticker) {
        // ticker 기준으로 회사를 스크래핑 합니다.
        Company company = this.scraper.scrapCompanyByTicker(ticker);
        if (ObjectUtils.isEmpty(company)) {
            throw new RuntimeException("failed to scrap ticker -> " + ticker);
        }

        // 해당 회사가 존재할 경우, 배당금 정보를 스크래핑한다.
        ScrapedResult scrapedResult = this.scraper.scrap(company);

        //스크래핑 결과
        CompanyEntity companyEntity = this.companyRepository.save(new CompanyEntity(company));
        List<DividendEntity> dividendEntities =
                scrapedResult.getDividends().stream()
                        .map(e -> new DividendEntity(companyEntity.getId(), e))
                        .collect(Collectors.toList());
        this.dividendRepository.saveAll(dividendEntities);

        return company;
    }

    public List<String> getCompanyNamesByKeyword(String keyword) {
        Pageable limit = PageRequest.of(0, 10);
        Page<CompanyEntity> companyEntities = this.companyRepository.findByNameStartingWithIgnoreCase(keyword, limit);
        return companyEntities.stream()
                .map(e -> e.getName())
                .collect(Collectors.toList());
    }

    public void addAutoCompleteKeyword(String keyword) {
        this.trie.put(keyword, null); // ticker의 키워드가 아니라, 회사명의 키워드임.
    }

    public List<String> autocomplete(String keyword) {
        return (List<String>)this.trie.prefixMap(keyword).keySet()
                .stream().collect(Collectors.toList());
    }

    public void deleteAutocompleteKeyword(String keyword) {
        this.trie.remove(keyword);
    }

}
