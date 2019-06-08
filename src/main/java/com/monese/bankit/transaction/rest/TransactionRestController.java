package com.monese.bankit.transaction.rest;

import com.monese.bankit.transaction.application.dto.TransferDTO;
import com.monese.bankit.transaction.application.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionRestController {

    @Autowired
    TransactionService transactionService;


    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferDTO transferDTO) {
        return new ResponseEntity<>(transactionService.transfer(transferDTO), HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<?> history(@RequestParam(value = "accountNumber") String accountNumber) {
        return new ResponseEntity<>(transactionService.allAccountHistory(accountNumber), HttpStatus.OK);
    }

}
