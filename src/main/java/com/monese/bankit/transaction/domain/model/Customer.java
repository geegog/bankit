package com.monese.bankit.transaction.domain.model;

import com.monese.bankit.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
public class Customer extends BaseEntity {

    private String firstName;

    private String lastName;

    private LocalDate birthOfBirth;

    private String address;

}
