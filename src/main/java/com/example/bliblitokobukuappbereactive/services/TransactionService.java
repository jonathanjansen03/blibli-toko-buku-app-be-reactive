package com.example.bliblitokobukuappbereactive.services;

import com.example.bliblitokobukuappbereactive.models.Book;
import com.example.bliblitokobukuappbereactive.models.Transaction;
import com.example.bliblitokobukuappbereactive.repositories.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Service
public class TransactionService {
    private TransactionRepository transactionRepository;

    public Flux<Transaction> getAllTransaction() {
        return transactionRepository.findAll().switchIfEmpty(Flux.empty());
    }

    public Mono<Transaction> getTransactionById(final String id) {
        return transactionRepository.findById(id);
    }

    public Mono<Transaction> updateTransaction(final String id, final Transaction transaction) {
        Mono<Transaction> findTransaction = getTransactionById(id);
        if(findTransaction == null) {
            return Mono.empty();
        }
        return transactionRepository.save(transaction);
    }

    public Mono<Transaction> insertTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Mono<Void> deleteTransaction(final String id) {
        Mono<Transaction> findTransaction = getTransactionById(id);
        if(findTransaction == null) {
            return Mono.empty();
        }
        return transactionRepository.deleteById(id);
    }
}
