package com.monese.bankit.transaction.domain.repository;

import com.monese.bankit.transaction.domain.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findAccountByAcNumber(String number);

}
