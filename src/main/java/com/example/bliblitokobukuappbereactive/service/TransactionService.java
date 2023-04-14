package com.example.bliblitokobukuappbereactive.service;

import java.util.concurrent.ExecutionException;

import com.example.bliblitokobukuappbereactive.dto.TransactionDTO;
import com.example.bliblitokobukuappbereactive.model.Transaction;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {
  Flux<Transaction> getAllTransaction();

  Flux<Transaction> getMonthlyReport(int month, int year);

  Mono<Transaction> insertTransaction(TransactionDTO transactionDTO)
      throws ExecutionException, InterruptedException;

  Mono<Void> deleteTransaction(final String id);
}
