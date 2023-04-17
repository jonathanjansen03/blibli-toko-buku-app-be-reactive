package com.example.bliblitokobukuappbereactive.model.response;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response<T> {
    private int status;
    private List<T> data;
    private Map<String, String> message;
    private int count;
}
