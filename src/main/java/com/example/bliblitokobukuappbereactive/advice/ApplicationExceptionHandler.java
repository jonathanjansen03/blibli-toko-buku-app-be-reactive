package com.example.bliblitokobukuappbereactive.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.example.bliblitokobukuappbereactive.model.response.Response;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({WebExchangeBindException.class})
  public Mono<Response<?>> WebExchangeBindHandler(WebExchangeBindException ex) {
    Map<String, String> errorMessage = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error -> {
      errorMessage.put(error.getField(), error.getDefaultMessage());
      log.info(errorMessage.toString());
    });
    Response<?> response = Response.builder().status(HttpStatus.BAD_REQUEST.value()).data(null)
        .message(errorMessage).build();
    return Mono.just(response);
  }
}
