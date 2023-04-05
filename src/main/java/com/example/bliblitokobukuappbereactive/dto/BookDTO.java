package com.example.bliblitokobukuappbereactive.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDTO {

    private String id;

    @Length(min = 1, message = "Title is mandatory")
    private String title;

    @Length(min = 1, message = "Author is mandatory")
    private String author;

    @Range(min = 0, message = "Stock must be greater than or equals to 0")
    private int stock;

    @Range(min = 35000, message = "Price must be greater than 35000")
    private double price;

    @Range(min = 0, max = 1, message = "Discount value must be between 0-1")
    private double discount = 0;

}
