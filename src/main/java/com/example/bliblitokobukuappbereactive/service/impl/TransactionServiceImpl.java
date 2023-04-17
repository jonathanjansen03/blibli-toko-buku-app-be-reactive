package com.example.bliblitokobukuappbereactive.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.bliblitokobukuappbereactive.model.Transaction;
import com.example.bliblitokobukuappbereactive.model.dto.TransactionDTO;
import com.example.bliblitokobukuappbereactive.repository.BookRepository;
import com.example.bliblitokobukuappbereactive.repository.TransactionRepository;
import com.example.bliblitokobukuappbereactive.service.TransactionService;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {
    private TransactionRepository transactionRepository;
    private BookRepository bookRepository;

    @Override
    public Flux<Transaction> getAllTransaction()
    {
        return transactionRepository.findAll();
    }

    @Override
    public Flux<Transaction> getMonthlyReport(int month, int year)
    {
        return transactionRepository.getMonthlyReport(month, year);
    }

    @Override
    public Mono<Transaction> insertTransaction(TransactionDTO transactionDTO)
    {
        return bookRepository
                .findById(transactionDTO.getBookId())
                .filter(retrievedBook -> retrievedBook.getStock() >= transactionDTO.getQty())
                .switchIfEmpty(
                    Mono.error(
                        new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Purchase quantity must not exceed book's stock"
                        )
                    )
                )
                .doOnSuccess(retrievedBook -> retrievedBook.setStock(retrievedBook.getStock() - transactionDTO.getQty()))
                .flatMap(retrievedBook -> bookRepository.save(retrievedBook))
                .flatMap(retrievedBook -> transactionRepository.save(new Transaction(retrievedBook, transactionDTO.getQty())));
    }

    @Override
    public Mono<Void> deleteTransaction(final String id)
    {
        return transactionRepository.deleteById(id);
    }

}
