package com.exercise.quotes.controllers;

import com.exercise.logs.service.LogService;
import com.exercise.quotes.dto.ErrorDtoResponse;
import com.exercise.quotes.dto.QuoteDto;
import com.exercise.quotes.entities.operation.Operation;
import com.exercise.quotes.service.QuotesService;
import com.exercise.quotes.validation.InputValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.exercise.quotes.controllers.ApiConstants.*;

@RestController
@RequestMapping("/api")
@Log4j
public class QuoteController {
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private QuotesService service;

    @Autowired
    private LogService logService;

    @PostMapping(ADD_QUOTE)
    @SneakyThrows
    public String saveQuote(@RequestBody QuoteDto quoteDto) {
        boolean result = false;
        try {
            InputValidator.validate(quoteDto);
            result = service.addQuote(quoteDto);
        } catch (Exception e) {
            ErrorDtoResponse response = ErrorDtoResponse.builder()
                    .errorCode(3).description(e.getMessage())
                    .level("error").build();
            logErrors(Operation.CREATE, quoteDto.getName(), response);
            return mapper.writeValueAsString(response);
        }
        if (!result) {
            ErrorDtoResponse response = ErrorDtoResponse.builder().errorCode(1).
                    description("quote already exists").
                    level("error").build();
            logErrors(Operation.CREATE, quoteDto.getName(), response);
            return mapper.writeValueAsString(response);
        }
        logSuccess(Operation.CREATE, quoteDto.getName());
        return "OK";
    }

    @DeleteMapping(DELETE_QUOTE)
    @SneakyThrows
    public String deleteQuote(@RequestParam String quoteName) {
        boolean result = false;
        try {
            result = service.removeQuote(quoteName);
        } catch (Exception e) {
            ErrorDtoResponse response = ErrorDtoResponse.builder()
                    .errorCode(3).description(e.getMessage())
                    .level("error").build();
            logErrors(Operation.DELETE, quoteName, response);
            return mapper.writeValueAsString(response);
        }
        if (!result) {
            ErrorDtoResponse response = ErrorDtoResponse.builder().errorCode(2)
                    .description("quote not exists")
                    .level("error").build();
            logErrors(Operation.DELETE, quoteName, response);
            return mapper.writeValueAsString(response);
        }
        logSuccess(Operation.DELETE, quoteName);
        return "OK";
    }

    @PutMapping(UPDATE_QUOTE)
    @SneakyThrows
    public String updateQuote(@RequestBody QuoteDto quoteDto) {
        boolean result = false;
        try {
            result = service.updateQuote(quoteDto);
        } catch (Exception e) {
            ErrorDtoResponse response = ErrorDtoResponse.builder()
                    .errorCode(3).description(e.getMessage())
                    .level("error").build();
            logErrors(Operation.UPDATE, quoteDto.getName(), response);
            return mapper.writeValueAsString(response);
        }
        if (!result) {
            ErrorDtoResponse response = ErrorDtoResponse.builder().errorCode(2)
                    .description("quote not exists")
                    .level("error").build();
            logErrors(Operation.UPDATE, quoteDto.getName(), response);
            return mapper.writeValueAsString(response);
        }
        logSuccess(Operation.UPDATE, quoteDto.getName());
        return "OK";
    }

    @GetMapping(GET_QUOTE)
    @SneakyThrows
    public String getQuote(@PathVariable String quoteName) {
        QuoteDto quoteDto;
        try {
            quoteDto = service.getQuote(quoteName);
        } catch (Exception e) {
            ErrorDtoResponse response = ErrorDtoResponse.builder().errorCode(1).
                    description(e.getMessage()).
                    level("error").build();
            log.error(e.getMessage());
            return mapper.writeValueAsString(response);
        }
        return mapper.writeValueAsString(quoteDto);
    }

    @GetMapping(GET_ALL_QUOTES)
    @SneakyThrows
    public String getAllQuotes() {
        List<QuoteDto> allQuotes = service.getAllQuotes();
        return mapper.writeValueAsString(allQuotes);
    }

    private void logErrors(Operation operation, String quoteName, ErrorDtoResponse response) {
        logService.addQuoteLog(operation, quoteName, response.getErrorCode(), response.getDescription());
        log.error(response.getDescription());
    }

    private void logSuccess(Operation create, String quoteName) {
        logService.addQuoteLog(Operation.CREATE, quoteName, 0, "Success");
    }
}
