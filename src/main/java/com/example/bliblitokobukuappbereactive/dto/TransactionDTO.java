package com.example.bliblitokobukuappbereactive.dto;

import com.example.bliblitokobukuappbereactive.constraint.ExistsConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionDTO {

    @ExistsConstraint
    String bookId;

    @Range(min = 1, message = "You must at least purchase 1 book")
    int qty;
}
