package com.example.bliblitokobukuappbereactive.model.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDTO {

  private String id;

  @Size(min = 1, message = "Title is mandatory.")
  @Size(max = 100, message = "Title must not be more than {max} characters.")
  private String title;

  @Size(min = 1, message = "Author is mandatory.")
  @Size(max = 100, message = "Author must not be more than {max} characters.")
  private String author;

  @Range(min = 0, max = 100, message = "Stock must be between {min} and {max} (inclusive).")
  private int stock;

  @Range(min = 1000, max = 1000000, message = "Price must be between IDR {min} and IDR {max}.")
  private double price;

  @Range(min = 0, max = 1, message = "Discount must be between ${min * 100}% and ${max * 100}% (inclusive).")
  private double discount = 0;

}
