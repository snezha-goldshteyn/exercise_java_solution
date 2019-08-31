package com.exercise.quotes.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "quotes")
public class Quote {
    @Id
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private int price;
    @Column(name = "removed", columnDefinition = "boolean default false")
    private boolean removed;
    @Column(name = "remove_date")
    private LocalDateTime removeDate;

    public Quote() {
    }

    public Quote(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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
        Quote quote = (Quote) o;
        return price == quote.price &&
                Objects.equals(name, quote.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}
