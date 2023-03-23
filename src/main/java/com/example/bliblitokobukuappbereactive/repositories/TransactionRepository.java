package com.example.bliblitokobukuappbereactive.repositories;

import com.example.bliblitokobukuappbereactive.models.Transaction;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {
    @Query(
            "{" +
                    "'$expr' : {" +
                    "'$and': [" +
                    "{'$eq': [{ '$month': '$purchasedAt' }, ?0]}," +
                    "{'$eq': [{ '$year': '$purchasedAt' }, ?1]}" +
                    "]" +
                    "}" +
                    "}"
    )
    Flux<Transaction> getMonthlyReport(int month, int year);
}
