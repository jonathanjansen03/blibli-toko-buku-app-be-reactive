package com.example.bliblitokobukuappbereactive.controllers;

import com.example.bliblitokobukuappbereactive.models.Transaction;
import com.example.bliblitokobukuappbereactive.services.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("gdn-bookstore-api/transactions")
@CrossOrigin(origins = "http://localhost:3000")
public class TransactionController {
    private TransactionService transactionService;

    @GetMapping
    public List<Transaction> getAllTransaction() {
        return transactionService.getAllTransaction().collectList().block();
    }

//    @GetMapping("/report")
//    public List<Transaction> getMonthlyReport(@RequestParam int month, @RequestParam int year){
//        return  transactionService.getMonthlyReport(month, year);
//    }

    @PostMapping(
            path = "/insert",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public Mono<Transaction> insertTransaction(@RequestBody Transaction transaction) {
        return transactionService.insertTransaction(transaction);
    }

    @PutMapping(
            path = "/update/{transactionId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public Mono<Transaction> updateTransaction(@RequestBody Transaction transaction, @PathVariable String transactionId) {
        return transactionService.updateTransaction(transactionId, transaction);
    }

    @DeleteMapping(
            path = "/delete/{transactionId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public Mono<Void> deleteTransaction(@PathVariable String transactionId) {
        return transactionService.deleteTransaction(transactionId);
    }

    @GetMapping(
            path="/{transactionId}"
    )
    public Mono<Transaction> getTransactionById(@PathVariable String transactionId) {
        return transactionService.getTransactionById(transactionId);
    }
}
