package com.example.bliblitokobukuappbereactive.model.dto.openlibrary;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OpenLibraryResponse {
    private List<OpenLibraryBook> docs;
}
