package com.example.bliblitokobukuappbereactive.service.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.bliblitokobukuappbereactive.model.dto.BookDTO;
import com.example.bliblitokobukuappbereactive.service.BookListenerService;
import com.example.bliblitokobukuappbereactive.service.BookService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookListenerServiceImpl implements BookListenerService {
  public final static String BOOK_TOPIC = "book_topic";

  public final static String GROUP_ID = "book_group";

  private final BookService bookService;

  public BookListenerServiceImpl(BookService bookService) {
    this.bookService = bookService;
  }

  @Override
  @KafkaListener(topics = BOOK_TOPIC, groupId = GROUP_ID,
      containerFactory = "KafkaListenerContainerFactory")
  public void onEventConsumed(BookDTO book) {
    log.info("Consume Book: {}", book);
    bookService.insertBook(book).subscribe();
  }
}
