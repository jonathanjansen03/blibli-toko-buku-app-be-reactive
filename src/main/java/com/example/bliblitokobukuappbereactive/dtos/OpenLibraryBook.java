package com.example.bliblitokobukuappbereactive.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OpenLibraryBook {
    private String title;
    private List<String> author_name;
}
