package com.zerobase.dividends.persist.entity;

import com.zerobase.dividends.model.Company;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = "COMPANY")
@Getter
@ToString
@NoArgsConstructor
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String ticker;
    private String name;

    public CompanyEntity(Company company) {
        this.ticker = company.getTicker();
        this.name = company.getName();
    }

    public static Company toCompany(CompanyEntity companyEntity) {
        return new Company().builder()
                .name(companyEntity.getName())
                .ticker(companyEntity.getTicker())
                .build();
    }
}

