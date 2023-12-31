package com.zerobase.dividends.persist;

import com.zerobase.dividends.persist.entity.DividendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DividendRepository extends JpaRepository<DividendEntity, Long> {
     List<DividendEntity> findAllByCompanyId(Long companyId);

     boolean existsByCompanyIdAndDateTime(Long companyId, LocalDateTime date);

     @Transactional
     void deleteAllByCompanyId(Long id);
}
