package com.example.bliblitokobukuappbereactive.model.response;

import com.example.bliblitokobukuappbereactive.model.dto.BookDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponse{
    private int status;
    private BookDTO data;
    private String errorMessage;
    private int currentPage;
    private int maximumPage;
    private int dataSize;
}
