package com.zerobase.dividends.persist.entity;

import com.zerobase.dividends.model.Dividend;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "DIVIDEND")
@Getter
@ToString
@NoArgsConstructor
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"companyId", "dateTime"}
                )
        }
)
public class DividendEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long companyId;
    private LocalDateTime dateTime;
    private String dividend;

    public DividendEntity(Long companyId, Dividend dividend) {
        this.companyId = companyId;
        this.dateTime = dividend.getDate();
        this.dividend = dividend.getDividend();
    }

    public static Dividend toDividend(DividendEntity dividendEntity) {
        return new Dividend().builder()
                .date(dividendEntity.getDateTime())
                .dividend(dividendEntity.getDividend())
                .build();
    }
}
