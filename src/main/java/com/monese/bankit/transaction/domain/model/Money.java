package com.monese.bankit.transaction.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Currency;

@Embeddable
@Value
@NoArgsConstructor(force=true,access= AccessLevel.PRIVATE)
@AllArgsConstructor(staticName="of")
class Money {

    @Column(precision = 8, scale = 2)
    private BigDecimal balance;

    private Currency currency;
}