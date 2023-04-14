package com.example.bliblitokobukuappbereactive.controller;

import java.util.concurrent.ExecutionException;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.example.bliblitokobukuappbereactive.dto.TransactionDTO;
import com.example.bliblitokobukuappbereactive.model.Transaction;
import com.example.bliblitokobukuappbereactive.service.impl.TransactionServiceImpl;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("gdn-bookstore-api/transactions")
@CrossOrigin(origins = "http://localhost:3000")
public class TransactionController {
    private TransactionServiceImpl transactionServiceImpl;

    @GetMapping
    public Flux<Transaction> getAllTransaction()
    {
        return transactionServiceImpl.getAllTransaction();
    }

    @GetMapping("/report")
    public Flux<Transaction> getMonthlyReport(@RequestParam int month, @RequestParam int year)
    {
        return transactionServiceImpl.getMonthlyReport(month, year);
    }

    @PostMapping
    (
        path = "/insert",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Mono<Transaction> insertTransaction(@Valid @RequestBody TransactionDTO transactionDTO)
        throws ExecutionException, InterruptedException
    {
        return transactionServiceImpl.insertTransaction(transactionDTO);
    }

    @DeleteMapping(path = "/delete/{transactionId}")
    public Mono<Void> deleteTransaction(@PathVariable String transactionId)
    {
        return transactionServiceImpl.deleteTransaction(transactionId);
    }


}
