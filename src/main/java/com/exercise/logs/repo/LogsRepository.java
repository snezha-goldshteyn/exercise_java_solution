package com.exercise.logs.repo;

import com.exercise.logs.model.QuoteLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogsRepository extends JpaRepository<QuoteLog, Long> {
    List<QuoteLog> findByQuoteId(String name);
}
