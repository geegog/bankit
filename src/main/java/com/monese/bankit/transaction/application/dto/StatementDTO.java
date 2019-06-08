package com.monese.bankit.transaction.application.dto;

import com.monese.bankit.common.application.dto.MoneyDTO;
import lombok.Data;

import java.util.List;

@Data
public class StatementDTO {

    private MoneyDTO moneyDTO;

    private List<HistoryDTO> historyDTOList;

}
