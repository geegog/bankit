package com.monese.bankit.transaction.domain.repository;

import com.monese.bankit.transaction.domain.model.Account;
import com.monese.bankit.transaction.domain.model.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {

    List<History> findByAccount(Account account);

}
