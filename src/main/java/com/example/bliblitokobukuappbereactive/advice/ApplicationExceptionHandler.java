package com.example.bliblitokobukuappbereactive.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.example.bliblitokobukuappbereactive.model.response.ErrorResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({WebExchangeBindException.class})
  public Mono<ErrorResponse<Map<String, String>>> WebExchangeBindHandler(
      WebExchangeBindException ex) {
    Map<String, String> errorMessage = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error -> {
      errorMessage.put(error.getField(), error.getDefaultMessage());
      log.info(errorMessage.toString());
    });
    ErrorResponse<Map<String, String>> response = ErrorResponse.<Map<String, String>>builder()
        .status(HttpStatus.BAD_REQUEST.value()).message(errorMessage).build();
    return Mono.just(response);
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(BookNotFoundException.class)
  public Mono<ErrorResponse<String>> RunTimeExceptionHandler(BookNotFoundException ex) {
    String errorMessage = ex.getLocalizedMessage();
    ErrorResponse<String> response = ErrorResponse.<String>builder()
        .status(HttpStatus.NOT_FOUND.value()).message(errorMessage).build();
    return Mono.just(response);
  }
}
