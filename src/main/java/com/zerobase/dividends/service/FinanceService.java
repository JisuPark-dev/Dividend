package com.zerobase.dividends.service;

import com.zerobase.dividends.exception.Impl.NoCompanyException;
import com.zerobase.dividends.model.ScrapedResult;
import com.zerobase.dividends.persist.CompanyRepository;
import com.zerobase.dividends.persist.DividendRepository;
import com.zerobase.dividends.persist.entity.CompanyEntity;
import com.zerobase.dividends.persist.entity.DividendEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.zerobase.dividends.model.constants.CacheKey.KEY_FINANCE;
import static com.zerobase.dividends.persist.entity.CompanyEntity.toCompany;

@Service
@Slf4j
@AllArgsConstructor
//@RequiredArgsConstructor
public class FinanceService {
    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;
    // 케싱 전 고려 요소
    // 1. 동일한 요청이 자주 들어오는가? 그렇다.
    // 2. 자주 변경되는 데이터인가? 아니다.
    @Cacheable(key = "#companyName", value = KEY_FINANCE)
    // Cache에 데이터가 없을 경우 내부 로직을 실행시키고, Cache에 데이터가 있을 경우 로직을 실행시키지 않고 캐시에서 가져다가 쓴다.
    public ScrapedResult getDividendByCompanyName(String companyName) {
        log.info("search Company -> " + companyName);
        // 1. 회사명을 기준으로 회사 정보를 조회
        CompanyEntity company = this.companyRepository.findByName(companyName)
                .orElseThrow(() -> new NoCompanyException());
        // 2. 조회된 회사의 아이디로  배당금 조회
        List<DividendEntity> dividendEntities = dividendRepository.findAllByCompanyId(company.getId());
        // 3. 결과 조합 후 반환
        return new ScrapedResult().builder()
                .company(toCompany(company))
                .dividends(dividendEntities.stream()
                        .map(DividendEntity::toDividend)
                        .collect(Collectors.toList()))
                .build();
    }

}
