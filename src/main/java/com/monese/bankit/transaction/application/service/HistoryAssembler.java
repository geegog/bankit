package com.monese.bankit.transaction.application.service;

import com.monese.bankit.transaction.application.dto.HistoryDTO;
import com.monese.bankit.transaction.domain.model.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class HistoryAssembler extends ResourceAssemblerSupport<History, HistoryDTO> {

    @Autowired
    private AccountAssembler accountAssembler;

    public HistoryAssembler() {
        super(TransactionService.class, HistoryDTO.class);
    }


    @Override
    public HistoryDTO toResource(History history) {
        if (history == null)
            return null;
        HistoryDTO dto = instantiateResource(history);
        dto.set_id(history.getId());
        dto.setCreated(history.getCreated());
        dto.setAccount(accountAssembler.toResource(history.getAccount()));
        dto.setTransactionType(history.getTransactionType());
        dto.setUpdated(history.getUpdated());
        dto.setSource(history.getSource());
        dto.setCurrentBalance(history.getCurrentBalance());
        dto.setNewBalance(history.getNewBalance());

        return dto;
    }
}
