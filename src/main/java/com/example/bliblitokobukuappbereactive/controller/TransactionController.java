package com.example.bliblitokobukuappbereactive.controller;

import java.util.concurrent.ExecutionException;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.example.bliblitokobukuappbereactive.dto.TransactionDTO;
import com.example.bliblitokobukuappbereactive.model.Transaction;
import com.example.bliblitokobukuappbereactive.service.impl.TransactionServiceImpl;

import io.swagger.annotations.ApiOperation;
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

    @ApiOperation("Get Transaction Monthly Report")
    @GetMapping("/report")
    public Flux<Transaction> getMonthlyReport(@RequestParam int month, @RequestParam int year)
    {
        return transactionServiceImpl.getMonthlyReport(month, year);
    }

    @ApiOperation("Insert Transaction")
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

    @ApiOperation("Delete Transaction")
    @DeleteMapping(path = "/delete/{transactionId}")
    public Mono<Void> deleteTransaction(@PathVariable String transactionId)
    {
        return transactionServiceImpl.deleteTransaction(transactionId);
    }


}
