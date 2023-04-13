package com.example.bliblitokobukuappbereactive;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class FreeTrainingTest {
  @Test
  public void testStreamMap() {
    List<Integer> number = Arrays.asList(1, 2, 3, 4, 5);
    for (int i : number) {
      log.info(String.valueOf(i));
    }
    List<Integer> newNumber =
        number.stream().map(element -> element + 1).collect(Collectors.toList());

    for (int i : newNumber) {
      log.info(String.valueOf(i));
    }
  }

  @Test
  public void testStreamFlatMap() {
    List<Integer> PrimeNumbers = Arrays.asList(5, 7, 11, 13);
    List<Integer> OddNumbers = Arrays.asList(1, 3, 5);
    List<Integer> EvenNumbers = Arrays.asList(2, 4, 6, 8);

    List<List<Integer>> listOfListonInts = Arrays.asList(PrimeNumbers, OddNumbers, EvenNumbers);

    log.info("The Structure before flattening is : " + listOfListonInts);

    List<Integer> listInts =
        listOfListonInts.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

    log.info("The Structure after flattening is : " + listInts);
  }

  @Test
  public void testFluxMap() {
    Flux.fromArray(new String[]{"Tom", "Melissa", "Steve", "Megan"})
            .map(String::toUpperCase)
            .subscribe(System.out::println);
  }
}
