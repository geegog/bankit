package com.monese.bankit.transaction.domain.model;

import com.monese.bankit.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
public class Account extends BaseEntity {

    @Column(unique = true)
    private String number;

    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne
    private Customer customer;

    @Embedded
    private Money money;

    public void credit(BigDecimal amount) {
        this.money.getBalance().add(amount);
    }

    public void debit(BigDecimal amount) {
        this.money.getBalance().subtract(amount);
    }

    public boolean isFundEnough(BigDecimal amount) {
        return this.money.getBalance().doubleValue() >= amount.doubleValue();
    }

    public boolean isValidName(String firstName, String lastName) {
        if (this.customer.getFirstName().equalsIgnoreCase(firstName) && this.customer.getLastName().equalsIgnoreCase(lastName)) {
            return true;
        }
        return false;
    }

}
