package com.exercise.quotes.mappers;

import com.exercise.quotes.dto.ItemDto;
import com.exercise.quotes.entities.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {
    public static ItemDto itemToItemDto(Item item) {
        return new ItemDto(item.getItemId(), item.getName(), item.getQuote().getName());
    }

    public static Item itemDtoToItem(ItemDto itemDto) {
        return new Item(itemDto.getId(), itemDto.getName());
    }
}
