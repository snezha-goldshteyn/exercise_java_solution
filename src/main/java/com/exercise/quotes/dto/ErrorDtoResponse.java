package com.exercise.quotes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ErrorDtoResponse {
    private int errorCode;
    private String description;
    private String level;

}
