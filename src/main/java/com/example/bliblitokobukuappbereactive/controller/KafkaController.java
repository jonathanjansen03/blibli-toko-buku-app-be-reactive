package com.example.bliblitokobukuappbereactive.controller;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bliblitokobukuappbereactive.model.dto.BookDTO;
import com.example.bliblitokobukuappbereactive.service.BookPublisherService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Api
@Validated
@RestController
@Slf4j
public class KafkaController {
  private final BookPublisherService bookPublisherService;

  public KafkaController(BookPublisherService bookPublisherService) {
    this.bookPublisherService = bookPublisherService;
  }

  @ApiOperation("Publish Kafka to Insert a Book")
  @DeleteMapping(path = "/kafka/publish", consumes = {MediaType.APPLICATION_JSON_VALUE})
  public Mono<Void> deleteBook(@RequestBody BookDTO bookDTO) {
    log.info("#deleteBook with book in Kafka: {}", bookDTO);
    try {
      bookPublisherService.sendMessage(bookDTO);
      log.info("Successfully Publish a Book");
      return Mono.empty();
    } catch (Exception e) {
      log.error("#deleteBook ERROR! errorMessage: {}", e.getMessage(), e);
      return Mono.empty();
    }
  }
}
