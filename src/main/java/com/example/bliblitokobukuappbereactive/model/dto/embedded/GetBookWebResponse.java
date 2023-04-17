package com.example.bliblitokobukuappbereactive.model.dto.embedded;

import java.util.List;

import com.example.bliblitokobukuappbereactive.model.Book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBookWebResponse {
    private long documentCount;
    private List<Book> bookList;
}
