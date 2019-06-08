package com.monese.bankit.transaction.application.dto;

import lombok.Data;

@Data
public class ResponseDTO {

    AccountDTO sender;

    AccountDTO receiver;

}
