package com.example.bliblitokobukuappbereactive.model.dto;

import org.hibernate.validator.constraints.Range;

import com.example.bliblitokobukuappbereactive.constraint.ExistsConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionDTO {

    @ExistsConstraint
    String bookId;

    @Range(min = 1, message = "You must at least purchase 1 book")
    int qty;
}
