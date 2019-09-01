package com.exercise.quotes.validation;

import com.exercise.quotes.dto.QuoteDto;
import com.exercise.quotes.exceptions.QuoteException;
import org.springframework.stereotype.Component;

@Component
public class InputValidator {
    public static void validate(QuoteDto quoteDto) {
        if (quoteDto.getName().equals("")) {
            throw new QuoteException("Name cannot be empty", 4);
        }

        if (quoteDto.getPrice()<0) {
            throw new QuoteException("Price cannot be negative", 4);
        }
    }
}
