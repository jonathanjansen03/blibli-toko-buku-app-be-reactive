package com.example.bliblitokobukuappbereactive.repositories;

import com.example.bliblitokobukuappbereactive.models.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {
}
