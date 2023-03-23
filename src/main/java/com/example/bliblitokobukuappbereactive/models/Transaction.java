package com.example.bliblitokobukuappbereactive.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@NoArgsConstructor
@Document(collection = "transactions")
public class Transaction {

    public static final String COLLECTION_NAME = "transactions";

    @Id
    private String id;

    @Version
    private int version;

    @DocumentReference(collection = "books")
    private Book book;

    private int qty;
    private LocalDateTime purchasedAt = LocalDateTime.now(ZoneId.of("Etc/GMT+7"));

    public  Transaction(Book book, int qty){
        this.book = book;
        this.qty = qty;
    }

}
