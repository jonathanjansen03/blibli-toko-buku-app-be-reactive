package com.example.bliblitokobukuappbereactive.dto;

import com.example.bliblitokobukuappbereactive.constraint.IsAvailableConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionDTO {

    @IsAvailableConstraint
    String bookId;
    int qty;
}
