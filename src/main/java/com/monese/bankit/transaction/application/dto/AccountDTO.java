package com.monese.bankit.transaction.application.dto;

import com.monese.bankit.common.application.dto.MoneyDTO;
import com.monese.bankit.common.rest.ResourceSupport;
import com.monese.bankit.transaction.domain.model.Type;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccountDTO extends ResourceSupport {

    private Long _id;

    private LocalDateTime created;

    private LocalDateTime updated;

    private String number;

    private Type type;

    private CustomerDTO customer;

    private MoneyDTO money;

}
