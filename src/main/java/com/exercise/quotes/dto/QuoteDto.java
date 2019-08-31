package com.exercise.quotes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuoteDto {
    private String name;
    private int price;
    private List<ItemDto> items;
}
