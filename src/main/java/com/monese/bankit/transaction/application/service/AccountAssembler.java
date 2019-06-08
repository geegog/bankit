package com.monese.bankit.transaction.application.service;

import com.monese.bankit.transaction.application.dto.AccountDTO;
import com.monese.bankit.transaction.domain.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class AccountAssembler extends ResourceAssemblerSupport<Account, AccountDTO> {

    @Autowired
    private CustomerAssembler customerAssembler;

    public AccountAssembler() {
        super(TransactionService.class, AccountDTO.class);
    }


    @Override
    public AccountDTO toResource(Account account) {
        if (account == null)
            return null;
        AccountDTO dto = instantiateResource(account);
        dto.set_id(account.getId());
        dto.setCreated(account.getCreated());
        dto.setNumber(account.getNumber());
        dto.setType(account.getType());
        dto.setUpdated(account.getUpdated());
        dto.setCustomer(customerAssembler.toResource(account.getCustomer()));

        return dto;
    }
}
