package com.monese.bankit.transaction.domain.model;

import com.monese.bankit.common.domain.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import java.time.LocalDate;

@Data
@Entity
public class Customer extends BaseEntity {

    private String firstName;

    private String lastName;

    private LocalDate birthOfBirth;

    private String address;

}
