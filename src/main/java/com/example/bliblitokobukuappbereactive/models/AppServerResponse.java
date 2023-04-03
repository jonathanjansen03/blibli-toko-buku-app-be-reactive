package com.example.bliblitokobukuappbereactive.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppServerResponse {
    private long documentCount;
    private List<Book> bookList;
}
