package com.monese.bankit.transaction.application.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferDTO {

    private String senderAccount;

    private String receiverAccount;

    private BigDecimal amount;

    private String receiverFirstName;

    private String receiverLastName;

}
