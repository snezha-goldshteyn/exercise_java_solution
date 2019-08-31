package com.exercise.quotes.service;

import com.exercise.quotes.dto.ItemDto;
import com.exercise.quotes.dto.QuoteDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuoteServiceImplTest {
    private static final String ITEM_NAME = "item";
    private static final String QUOTE_NAME = "name";
    private static final List<ItemDto> itemsDto = new ArrayList<>();
    static {
        itemsDto.add(new ItemDto(1, ITEM_NAME + 1, QUOTE_NAME + 1));
        itemsDto.add(new ItemDto(2, ITEM_NAME + 2, QUOTE_NAME + 1));
        itemsDto.add(new ItemDto(3, ITEM_NAME + 3, QUOTE_NAME + 1));
    }
    private static final QuoteDto QUOTE_DTO =
            new QuoteDto(QUOTE_NAME + 1, 10, itemsDto);
    private static final QuoteDto NEW_QUOTE_DTO =
            new QuoteDto(QUOTE_NAME + 2, 10, itemsDto);

    @Autowired
    private QuotesService service;

    @Before
    public void setUp() throws Exception {
        service.addQuote(QUOTE_DTO);
        service.removeQuote(NEW_QUOTE_DTO.getName());
    }

    @Test
    public void getQuote() {
        QuoteDto actualQuote = service.getQuote(QUOTE_NAME + 1);
        assertEquals(QUOTE_DTO, actualQuote);
    }

    @Test
    public void addQuote() {
        assertFalse(service.addQuote(QUOTE_DTO));
        assertTrue(service.addQuote(NEW_QUOTE_DTO));
    }

    @Test
    public void removeQuote() {
        assertFalse(service.removeQuote(QUOTE_NAME+2));
        assertTrue(service.removeQuote(QUOTE_NAME+1));
    }

    @Test
    public void updateQuote() {
        QUOTE_DTO.setPrice(15);
        assertTrue(service.updateQuote(QUOTE_DTO));
        assertFalse(service.updateQuote(NEW_QUOTE_DTO));
        QuoteDto actualQuoteDto = service.getQuote(QUOTE_NAME + 1);
        assertEquals(15, actualQuoteDto.getPrice());

    }

    @Test
    public void getAllQuotes() {
        List<QuoteDto> allQuotes = service.getAllQuotes();
        assertEquals(1, allQuotes.size());
        assertEquals(QUOTE_DTO, allQuotes.get(0));
    }
}