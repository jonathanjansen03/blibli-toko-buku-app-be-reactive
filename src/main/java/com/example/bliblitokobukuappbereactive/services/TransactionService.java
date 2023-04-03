package com.example.bliblitokobukuappbereactive.services;

import com.example.bliblitokobukuappbereactive.dtos.TransactionDTO;
import com.example.bliblitokobukuappbereactive.models.Book;
import com.example.bliblitokobukuappbereactive.models.Transaction;
import com.example.bliblitokobukuappbereactive.repositories.BookRepository;
import com.example.bliblitokobukuappbereactive.repositories.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Component
@AllArgsConstructor
@Service
public class TransactionService {
    private TransactionRepository transactionRepository;
    private BookRepository bookRepository;

    public Flux<Transaction> getAllTransaction()
    {
        return transactionRepository
                .findAll()
                .doOnNext(transaction -> System.out.println(transaction.getBook()));

//                .doOnNext(transaction -> {
//                    try
//                    {
//                        transaction.setBook(
//                            bookRepository
//                                .findById(transaction.getBook().getId())
//                                .toFuture()
//                                .get()
//                        );
//                        System.out.println(transaction.getBook());
//                    }
//                    catch (InterruptedException e)
//                    {
//                        throw new RuntimeException(e);
//                    }
//                    catch (ExecutionException e)
//                    {
//                        throw new RuntimeException(e);
//                    }
//                });
    }

    public Mono<Transaction> getTransactionById(final String id)
    {
        return transactionRepository.findById(id);
    }

    public Flux<Transaction> getMonthlyReport(int month, int year)
    {
        return transactionRepository.getMonthlyReport(month, year);
    }

    public Mono<Transaction> insertTransaction(TransactionDTO transactionDTO)
            throws ExecutionException, InterruptedException
    {
        Book book = bookRepository.findById(transactionDTO.getBookId()).toFuture().get();

        Transaction newTransaction = new Transaction(book, transactionDTO.getQty());

        return transactionRepository.save(newTransaction);
    }

    public Mono<Void> deleteTransaction(final String id)
    {
        return transactionRepository.deleteById(id);
    }
}
