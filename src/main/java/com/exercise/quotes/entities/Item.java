package com.exercise.quotes.entities;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Column(name = "item_id")
    private int itemId;
    @Column(name = "name")
    private String name;
    @Column(name = "removed", columnDefinition = "boolean default false")
    private boolean removed;
    @Column(name = "remove_date")
    private LocalDateTime removeDate;

    @ManyToOne
    @JoinColumn(name = "quote_name", referencedColumnName = "name", nullable = false)
    private Quote quote;

    public Item(int itemId, String name) {
        this.itemId = itemId;
        this.name = name;
    }

    public Item() {
    }

    public int getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public LocalDateTime getRemoveDate() {
        return removeDate;
    }

    public void setRemoveDate(LocalDateTime removeDate) {
        this.removeDate = removeDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return itemId == item.itemId &&
                name.equals(item.name) &&
                quote.equals(item.quote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, name, quote);
    }
}
