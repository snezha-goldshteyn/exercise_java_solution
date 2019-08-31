package com.exercise.quotes.validation;

import com.exercise.quotes.dto.QuoteDto;
import org.springframework.stereotype.Component;

@Component
public class InputValidator {
    public static void validate(QuoteDto quoteDto) {
        if (quoteDto.getName().equals("")) {
            throw new IllegalStateException("Name cannot be empty");
        }

        if (quoteDto.getPrice()<0) {
            throw new IllegalStateException("Price cannot be negative");
        }
    }
}
