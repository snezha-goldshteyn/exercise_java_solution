package com.exercise.logs.controllers;

import com.exercise.logs.model.QuoteLog;
import com.exercise.logs.service.LogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/log")
public class LogController {
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private LogService service;

    @GetMapping("/{quoteId}")
    public List<String> getQuoteLog(@PathVariable String quoteId) {
        return service.getQuoteLogs(quoteId).stream()
                .map(QuoteLog::toString).collect(Collectors.toList());
    }

    @GetMapping("/all")
    public List<String> getAllLogs() {
        return service.getAllLogs().stream()
                .map(QuoteLog::toString).collect(Collectors.toList());
    }
}
