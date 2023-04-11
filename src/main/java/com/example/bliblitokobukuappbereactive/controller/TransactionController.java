package com.example.bliblitokobukuappbereactive.controller;

import com.example.bliblitokobukuappbereactive.dto.TransactionDTO;
import com.example.bliblitokobukuappbereactive.model.Transaction;
import com.example.bliblitokobukuappbereactive.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.concurrent.ExecutionException;

@AllArgsConstructor
@RestController
@RequestMapping("gdn-bookstore-api/transactions")
@CrossOrigin(origins = "http://localhost:3000")
public class TransactionController {
    private TransactionService transactionService;

    @GetMapping
    public Flux<Transaction> getAllTransaction()
    {
        return transactionService.getAllTransaction();
    }

    @GetMapping("/report")
    public Flux<Transaction> getMonthlyReport(@RequestParam int month, @RequestParam int year)
    {
        return transactionService.getMonthlyReport(month, year);
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
        return transactionService.insertTransaction(transactionDTO);
    }

    @DeleteMapping(path = "/delete/{transactionId}")
    public Mono<Void> deleteTransaction(@PathVariable String transactionId)
    {
        return transactionService.deleteTransaction(transactionId);
    }


}
