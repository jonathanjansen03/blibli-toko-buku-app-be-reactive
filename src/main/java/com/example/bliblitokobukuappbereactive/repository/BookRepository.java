package com.example.bliblitokobukuappbereactive.repository;

import com.example.bliblitokobukuappbereactive.model.Book;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends ReactiveMongoRepository<Book, String> {
}
