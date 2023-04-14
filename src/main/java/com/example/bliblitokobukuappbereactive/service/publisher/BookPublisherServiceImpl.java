package com.example.bliblitokobukuappbereactive.service.publisher;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.bliblitokobukuappbereactive.dto.BookDTO;
import com.example.bliblitokobukuappbereactive.service.BookPublisherService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookPublisherServiceImpl implements BookPublisherService {

  public final static String BOOK_TOPIC = "book_topic";

  private final KafkaTemplate<String, BookDTO> kafkaTemplate;

  public BookPublisherServiceImpl(KafkaTemplate<String, BookDTO> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void sendMessage(BookDTO book) {
    log.info("Publish Book: {}", book);
    this.kafkaTemplate.send(BOOK_TOPIC, book);
  }
}
