package com.zerobase.dividends.persist;

import com.zerobase.dividends.persist.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity,Long> {
    boolean existsByTicker(String ticker);

    Optional<CompanyEntity> findByName(String name);
}
