package com.monese.bankit.transaction.application.service;

import com.monese.bankit.common.application.dto.MoneyDTO;
import com.monese.bankit.transaction.application.dto.*;
import com.monese.bankit.transaction.domain.model.Account;
import com.monese.bankit.transaction.domain.model.History;
import com.monese.bankit.transaction.domain.model.Source;
import com.monese.bankit.transaction.domain.model.Type;
import com.monese.bankit.transaction.domain.repository.AccountRepository;
import com.monese.bankit.transaction.domain.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountAssembler accountAssembler;

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    HistoryAssembler historyAssembler;

    private History saveHistory(Account account, BigDecimal amount, BigDecimal currentBalance, BigDecimal newBalance) {
        History history = new History();
        history.setAccount(account);
        history.setAmount(amount);
        history.setType(Type.DEBIT);
        history.setCurrentBalance(currentBalance);
        history.setNewBalance(newBalance);
        history.setSource(Source.MOBILE);

        return historyRepository.save(history);
    }

    public StatementDTO allAccountHistory(String accountNumber) {
        Account account = accountRepository.findAccountByAcNumber(accountNumber);
        MoneyDTO moneyDTO = MoneyDTO.of(account.getMoney().getBalance(), account.getMoney().getCurrency());
        List<HistoryDTO> histories = historyAssembler.toResources(historyRepository.findByAccount(account));
        StatementDTO statementDTO = new StatementDTO();
        statementDTO.setMoneyDTO(moneyDTO);
        statementDTO.setHistoryDTOList(histories);
        return statementDTO;
    }

    public ResponseDTO transfer(TransferDTO transferDTO) {
        Account sender = accountRepository.findAccountByAcNumber(transferDTO.getSenderAccount());
        Account receiver = accountRepository.findAccountByAcNumber(transferDTO.getReceiverAccount());

        BigDecimal senderCurrentBalance = sender.getMoney().getBalance();
        BigDecimal receiverCurrentBalance = receiver.getMoney().getBalance();

        if (sender.isFundEnough(transferDTO.getAmount()) && receiver.isValidName(transferDTO.getReceiverFirstName(), transferDTO.getReceiverLastName())) {
            sender.debit(transferDTO.getAmount());
            sender.setUpdated(LocalDateTime.now());

            accountRepository.save(sender);
            saveHistory(sender, transferDTO.getAmount(), senderCurrentBalance, sender.getMoney().getBalance());

            receiver.credit(transferDTO.getAmount());
            receiver.setUpdated(LocalDateTime.now());

            accountRepository.save(receiver);

            saveHistory(receiver, transferDTO.getAmount(), receiverCurrentBalance, receiver.getMoney().getBalance());
        }

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setReceiver(accountAssembler.toResource(receiver));
        responseDTO.setSender(accountAssembler.toResource(sender));

        return responseDTO;

    }

}


