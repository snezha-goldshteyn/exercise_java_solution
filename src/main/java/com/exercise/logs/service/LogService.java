package com.exercise.logs.service;

import com.exercise.logs.model.QuoteLog;
import com.exercise.quotes.entities.operation.Operation;

import java.util.List;

public interface LogService {
    List<QuoteLog> getAllLogs();
    List<QuoteLog> getQuoteLogs(String name);
    void addQuoteLog(Operation operation, String name, int errorCode, String message);
}
