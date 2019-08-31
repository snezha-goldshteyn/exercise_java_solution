package com.exercise.quotes.mappers;

import com.exercise.quotes.dto.ItemDto;
import com.exercise.quotes.dto.QuoteDto;
import com.exercise.quotes.entities.Quote;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuoteMapper {
    public static QuoteDto quoteToQuoteDto(Quote quote, List<ItemDto> itemsDto) {
        return new QuoteDto(quote.getName(), quote.getPrice(), itemsDto);
    }

    public static Quote quoteDtoToQuote(QuoteDto quoteDto) {
        return new Quote(quoteDto.getName(), quoteDto.getPrice());
    }
}
