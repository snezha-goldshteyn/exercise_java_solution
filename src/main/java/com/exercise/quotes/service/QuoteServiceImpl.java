package com.exercise.quotes.service;

import com.exercise.quotes.exceptions.QuoteException;
import com.exercise.quotes.dto.ItemDto;
import com.exercise.quotes.dto.QuoteDto;
import com.exercise.quotes.entities.Item;
import com.exercise.quotes.entities.Quote;
import com.exercise.quotes.mappers.ItemMapper;
import com.exercise.quotes.mappers.QuoteMapper;
import com.exercise.quotes.repo.ItemsRepository;
import com.exercise.quotes.repo.QuotesRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Log4j
public class QuoteServiceImpl implements QuotesService {
    @Autowired
    QuotesRepository quotesRepository;

    @Autowired
    ItemsRepository itemsRepository;

    @Override
    public QuoteDto getQuote(String name) {
        Quote quote = getQuoteFromRepo(name);
        if (quote.isRemoved()) {
            throw new QuoteException("This quote " + name + " is deleted at" + quote.getRemoveDate(), 3);
        }
        List<Item> items = getItemsByQuoteName(name).stream()
                .filter(item -> !item.isRemoved())
                .collect(Collectors.toList());
        List<ItemDto> itemsDto = items.stream().map(ItemMapper::itemToItemDto).collect(Collectors.toList());
        return QuoteMapper.quoteToQuoteDto(quote, itemsDto);
    }

    @Override
    @Transactional
    public boolean addQuote(QuoteDto quoteDto) {
        Quote quote = QuoteMapper.quoteDtoToQuote(quoteDto);
        if (quotesRepository.existsById(quote.getName())) {
            Quote actualQuote = getQuoteFromRepo(quote.getName());
            if (!actualQuote.isRemoved()) {
                throw new QuoteException("Quote already exists", 1);
            }
            rebuildQuote(actualQuote);
            return true;
        }
        List<ItemDto> itemsDto = quoteDto.getItems();
        quotesRepository.save(quote);
        saveItems(itemsDto, quote);
        return true;
    }

    @Transactional
    protected void rebuildQuote(Quote actualQuote) {
        actualQuote.setRemoveDate(null);
        actualQuote.setRemoved(false);
        List<Item> itemsByQuoteName = getItemsByQuoteName(actualQuote.getName());
        for (Item item : itemsByQuoteName) {
            item.setRemoved(false);
            item.setRemoveDate(null);
        }
    }

    @Override
    @Transactional
    public boolean removeQuote(String name) {
        if (!quotesRepository.existsById(name)) {
            throw new QuoteException("Quote not exists", 2);
        }
        Quote quote = getQuoteFromRepo(name);
        if (quote.isRemoved()) {
            throw new QuoteException("This quote " + name + " is deleted at " + quote.getRemoveDate(), 3);
        }
        List<Item> items = getItemsByQuoteName(name);
        deleteItems(items);
        quote.setRemoved(true);
        quote.setRemoveDate(LocalDateTime.now());
        return true;
    }

    @Override
    @Transactional
    public boolean updateQuote(QuoteDto quoteDto) {
        if (!quotesRepository.existsById(quoteDto.getName())) {
            throw new QuoteException("Quote not exists", 2);
        }
        String quoteName = quoteDto.getName();
        Quote quote = getQuoteFromRepo(quoteName);
        if (quote.isRemoved()) {
            throw new QuoteException("This quote " + quoteName + " is deleted at " + quote.getRemoveDate(), 3);
        }
        List<Item> currentItems = getItemsByQuoteName(quoteName);
        List<Item> newItems = quoteDto.getItems().stream()
                .map(ItemMapper::itemDtoToItem)
                .collect(Collectors.toList());
        setQuoteToItems(quote, newItems);
        updateItems(newItems, currentItems);
        quote.setPrice(quoteDto.getPrice());
        return true;
    }

    @Override
    public List<QuoteDto> getAllQuotes() {
        List<Quote> quotes = quotesRepository.findAll()
                .stream().filter(quote -> !quote.isRemoved())
                .collect(Collectors.toList());
        List<QuoteDto> quotesDto = toQuoteDtoList(quotes);
        return quotesDto;
    }

    private List<QuoteDto> toQuoteDtoList(List<Quote> quotes) {
        List<QuoteDto> quotesDto = new ArrayList<>();
        for (Quote quote : quotes) {
            List<ItemDto> itemsDto = getItemsByQuoteName(quote.getName())
                    .stream().map(ItemMapper::itemToItemDto).collect(Collectors.toList());
            quotesDto.add(QuoteMapper.quoteToQuoteDto(quote, itemsDto));
        }
        return quotesDto;
    }

    @Transactional
    protected void saveItems(List<ItemDto> itemsDto, Quote quote) {
        List<Item> items = itemsDto.stream()
                .map(ItemMapper::itemDtoToItem)
                .collect(Collectors.toList());
        for (Item item : items) {
            item.setQuote(quote);
            itemsRepository.save(item);
        }
    }

    @Transactional
    protected void updateItems(List<Item> newItems, List<Item> currentItems) {
        Map<Item, Integer> itemsMap = getItemsMap(newItems, currentItems);
        List<Item> itemsForDelete = getItemsOnValue(itemsMap, 0);
        List<Item> itemsForSave = getItemsOnValue(itemsMap, 2);
        deleteItems(itemsForDelete);
        addItems(itemsForSave);
    }

    @Transactional
    protected void deleteItems(List<Item> itemsForDelete) {
        for (Item item : itemsForDelete) {
            item.setRemoveDate(LocalDateTime.now());
            item.setRemoved(true);
        }
    }

    private void addItems(List<Item> itemsForSave) {
        for (Item item : itemsForSave) {
            itemsRepository.save(item);
        }
    }

    private List<Item> getItemsOnValue(Map<Item, Integer> itemsMap, int value) {
        return itemsMap.entrySet().stream()
                .filter(entry -> entry.getValue() == value)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private Map<Item, Integer> getItemsMap(List<Item> newItems, List<Item> currentItems) {
        Map<Item, Integer> itemsMap = new HashMap<>();
        for (Item currentItem : currentItems) {
            itemsMap.put(currentItem, 0);
        }
        for (Item newItem : newItems) {
            int value = itemsMap.getOrDefault(newItem, -1);
            if (value == -1) {
                itemsMap.put(newItem, 2);
            } else {
                itemsMap.put(newItem, 1);
            }
        }
        return itemsMap;
    }

    private Quote getQuoteFromRepo(String name) {
        return quotesRepository.findById(name)
                .orElseThrow(() -> new IllegalArgumentException("There is no quote with name: " + name));
    }

    private List<Item> getItemsByQuoteName(String name) {
        return itemsRepository.findByQuoteName(name);
    }

    private void setQuoteToItems(Quote quote, List<Item> newItems) {
        for (Item newItem : newItems) {
            newItem.setQuote(quote);
        }
    }
}
