package com.ntd.bank.transactionservice.repository;


import com.ntd.bank.transactionservice.model.Transaction;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {
}
