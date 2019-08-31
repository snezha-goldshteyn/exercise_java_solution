package com.exercise.quotes.repo;

import com.exercise.quotes.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemsRepository extends JpaRepository<Item, Integer> {
    List<Item> findByQuoteName(String name);
}
