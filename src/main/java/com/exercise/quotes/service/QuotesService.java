package com.exercise.quotes.service;

import com.exercise.quotes.dto.QuoteDto;

import java.util.List;

public interface QuotesService {
    QuoteDto getQuote(String name);
    boolean addQuote(QuoteDto quoteDto);
    boolean removeQuote(String name);
    boolean updateQuote(QuoteDto quoteDto);
    List<QuoteDto> getAllQuotes();

}
