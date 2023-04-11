package com.example.bliblitokobukuappbereactive.service;

import com.example.bliblitokobukuappbereactive.dto.TransactionDTO;
import com.example.bliblitokobukuappbereactive.model.Transaction;
import com.example.bliblitokobukuappbereactive.repository.BookRepository;
import com.example.bliblitokobukuappbereactive.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@Component
@AllArgsConstructor
@Service
public class TransactionService {
    private TransactionRepository transactionRepository;
    private BookRepository bookRepository;

    public Flux<Transaction> getAllTransaction()
    {
        return transactionRepository.findAll();
    }

    public Flux<Transaction> getMonthlyReport(int month, int year)
    {
        return transactionRepository.getMonthlyReport(month, year);
    }

    public Mono<Transaction> insertTransaction(TransactionDTO transactionDTO)
            throws ExecutionException, InterruptedException
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

    public Mono<Void> deleteTransaction(final String id)
    {
        return transactionRepository.deleteById(id);
    }

}
