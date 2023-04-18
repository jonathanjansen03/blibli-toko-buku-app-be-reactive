package com.example.bliblitokobukuappbereactive.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NormalResponse<T> {
    private int status;
    private T data;
    private int count;
}
