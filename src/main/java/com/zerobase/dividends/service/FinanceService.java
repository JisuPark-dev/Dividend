package com.zerobase.dividends.service;

import com.zerobase.dividends.model.ScrapedResult;
import com.zerobase.dividends.persist.CompanyRepository;
import com.zerobase.dividends.persist.DividendRepository;
import com.zerobase.dividends.persist.entity.CompanyEntity;
import com.zerobase.dividends.persist.entity.DividendEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.zerobase.dividends.persist.entity.CompanyEntity.toCompany;

@Service
@AllArgsConstructor
//@RequiredArgsConstructor
public class FinanceService {
    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;
    public ScrapedResult getDividendByCompanyName(String companyName) {
        // 1. 회사명을 기준으로 회사 정보를 조회
        CompanyEntity company = this.companyRepository.findByName(companyName)
                .orElseThrow(() -> new RuntimeException("존재 하지 않는 회사입니다."));
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
