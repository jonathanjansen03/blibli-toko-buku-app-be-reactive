package com.example.bliblitokobukuappbereactive.repositories;

import com.example.bliblitokobukuappbereactive.models.Book;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
}
