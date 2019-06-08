package com.monese.bankit.transaction.domain.model;

import com.monese.bankit.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
public class History extends BaseEntity {

    @ManyToOne
    private Account account;

    @Column(precision = 8, scale = 2)
    private BigDecimal amount;

    @Column(precision = 8, scale = 2)
    private BigDecimal currentBalance;

    @Column(precision = 8, scale = 2)
    private BigDecimal newBalance;

    private Source source;

    private Type type;

}
