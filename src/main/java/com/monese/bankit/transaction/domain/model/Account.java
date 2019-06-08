package com.monese.bankit.transaction.domain.model;

import com.monese.bankit.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
public class Account extends BaseEntity {

    @Column(unique = true)
    private String acNumber;

    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne
    private Customer customer;

    @Embedded
    private Money money;

    public void credit(BigDecimal amount) {
        this.money = Money.of(this.money.getBalance().add(amount), this.getMoney().getCurrency());
    }

    public void debit(BigDecimal amount) {
        this.money = Money.of(this.money.getBalance().subtract(amount), this.getMoney().getCurrency());
    }

    public boolean isFundEnough(BigDecimal amount) {
        return this.money.getBalance().doubleValue() >= amount.doubleValue();
    }

    public boolean isValidName(String firstName, String lastName) {
        return this.customer.getFirstName().equalsIgnoreCase(firstName) && this.customer.getLastName().equalsIgnoreCase(lastName);
    }

}
