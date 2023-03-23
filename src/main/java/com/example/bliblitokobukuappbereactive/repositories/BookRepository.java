package com.example.bliblitokobukuappbereactive.repositories;

import com.example.bliblitokobukuappbereactive.models.Book;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends ReactiveMongoRepository<Book, String> {
}
