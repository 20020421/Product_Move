package com.monopoco.productmove.repository;

import com.monopoco.productmove.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {

    List<TransactionHistory> findTransactionHistoriesByDistributorAgent_NameAndCreatedAtBetween(String distributorAgent_name, Date createdAt, Date createdAt2);
}
