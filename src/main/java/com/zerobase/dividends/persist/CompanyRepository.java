package com.zerobase.dividends.persist;

import com.zerobase.dividends.persist.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity,Long> {
    boolean existsByTicker(String ticker);
}
