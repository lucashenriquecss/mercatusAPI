package com.example.mercatusAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mercatusAPI.entitty.transaction.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    
}
