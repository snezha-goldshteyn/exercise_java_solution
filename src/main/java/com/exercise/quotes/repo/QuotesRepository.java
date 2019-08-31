package com.exercise.quotes.repo;

import com.exercise.quotes.entities.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuotesRepository extends JpaRepository<Quote, String> {
}
