package com.exercise.quotes.validation;

import com.exercise.logs.service.LogService;
import com.exercise.quotes.controllers.QuoteController;
import com.exercise.quotes.dto.ErrorDtoResponse;
import com.exercise.quotes.dto.QuoteDto;
import com.exercise.quotes.entities.operation.Operation;
import com.exercise.quotes.service.QuotesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class InputValidatorTest {
    ObjectMapper mapper = new ObjectMapper();

    @Mock
    private LogService logService;

    @Mock
    private QuotesService quotesService;

    @InjectMocks
    private QuoteController quoteController;

    private static final QuoteDto negativePriceQuoteDto = new QuoteDto("name1", -15, new ArrayList<>());
    private static final QuoteDto emptyNameQuoteDto = new QuoteDto("", 15, new ArrayList<>());

    private static final ErrorDtoResponse negativePriceResponse = ErrorDtoResponse.builder()
            .errorCode(3).level("error")
            .description("Price cannot be negative").build();
    private static final ErrorDtoResponse emptyNameResponse = ErrorDtoResponse.builder()
            .errorCode(3).level("error")
            .description("Name cannot be empty").build();

    @Before
    public void setUp() {
        Mockito.doNothing().when(logService).addQuoteLog(ArgumentMatchers.any(Operation.class),
                ArgumentMatchers.anyString(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyString());
    }

    @Test
    @SneakyThrows
    public void validatePrice() {

        String actualAnswer = quoteController.saveQuote(negativePriceQuoteDto);
        assertEquals(actualAnswer, mapper.writeValueAsString(negativePriceResponse));
    }

    @Test
    @SneakyThrows
    public void validateName() {
        String actualAnswer = quoteController.saveQuote(emptyNameQuoteDto);
        assertEquals(actualAnswer, mapper.writeValueAsString(emptyNameResponse));
    }
}