package com.monese.bankit.transaction.application.dto;

import com.monese.bankit.transaction.domain.model.Source;
import com.monese.bankit.transaction.domain.model.AccountType;
import com.monese.bankit.transaction.domain.model.TransactionType;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class HistoryDTO extends ResourceSupport {

    private Long _id;

    private LocalDateTime created;

    private LocalDateTime updated;

    private AccountDTO account;

    private BigDecimal amount;

    private BigDecimal currentBalance;

    private BigDecimal newBalance;

    private Source source;

    private TransactionType transactionType;

}
