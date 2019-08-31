package com.exercise.logs.service;

import com.exercise.logs.model.QuoteLog;
import com.exercise.logs.repo.LogsRepository;
import com.exercise.quotes.entities.operation.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogsRepository repository;

    @Override
    public List<QuoteLog> getAllLogs() {
        return repository.findAll();
    }

    @Override
    public List<QuoteLog> getQuoteLogs(String name) {
        return repository.findByQuoteId(name);
    }

    @Override
    public void addQuoteLog(Operation operation, String name, int errorCode, String message) {
        QuoteLog quoteLog = QuoteLog.builder()
                .operation(operation).quoteId(name)
                .errorCode(errorCode).message(message)
                .createdDate(LocalDateTime.now())
                .build();
        repository.save(quoteLog);
    }
}
