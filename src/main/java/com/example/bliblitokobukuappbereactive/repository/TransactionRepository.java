package com.example.bliblitokobukuappbereactive.repository;

import com.example.bliblitokobukuappbereactive.model.Transaction;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

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
