package com.example.bliblitokobukuappbereactive.dto.embedded;

import com.example.bliblitokobukuappbereactive.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBookWebResponse {
    private long documentCount;
    private List<Book> bookList;
}
