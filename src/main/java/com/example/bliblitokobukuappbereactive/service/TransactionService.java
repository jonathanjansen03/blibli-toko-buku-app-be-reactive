package com.example.bliblitokobukuappbereactive.service;

import com.example.bliblitokobukuappbereactive.dto.TransactionDTO;
import com.example.bliblitokobukuappbereactive.model.Book;
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

        if(book.getStock() < transactionDTO.getQty()){
            return Mono.error(new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Purchase quantity must not be more than Book's stock"
            ));
        }

        book.setStock(book.getStock() - transactionDTO.getQty());
        bookRepository.save(book).subscribe();

        Transaction newTransaction = new Transaction(book, transactionDTO.getQty());

        return transactionRepository.save(newTransaction);
    }

    public Mono<Void> deleteTransaction(final String id)
    {
        return transactionRepository.deleteById(id);
    }
}
