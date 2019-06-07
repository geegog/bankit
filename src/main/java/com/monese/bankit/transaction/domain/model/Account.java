package com.monese.bankit.transaction.domain.model;

import com.monese.bankit.common.domain.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Account extends BaseEntity {

    private String number;

    private Type type;

    @ManyToOne
    private Customer customer;

    private Money amount;

}
