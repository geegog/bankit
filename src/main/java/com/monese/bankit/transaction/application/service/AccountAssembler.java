package com.monese.bankit.transaction.application.service;

import com.monese.bankit.common.application.dto.MoneyDTO;
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
        MoneyDTO moneyDTO = MoneyDTO.of(account.getMoney().getBalance(), account.getMoney().getCurrency());
        dto.set_id(account.getId());
        dto.setCreated(account.getCreated());
        dto.setNumber(account.getAcNumber());
        dto.setAccountType(account.getAccountType());
        dto.setUpdated(account.getUpdated());
        dto.setMoney(moneyDTO);
        dto.setCustomer(customerAssembler.toResource(account.getCustomer()));

        return dto;
    }
}
