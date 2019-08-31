package com.exercise.logs.model;

import com.exercise.quotes.entities.operation.Operation;
import com.exercise.quotes.entities.operation.OperationConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quote_log")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuoteLog {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "quote_id")
    private String quoteId;

    @Column(name = "error_code")
    private int errorCode;

    @Column(name = "message")
    private String message;

    @Column(name = "operation", columnDefinition = "INTEGER")
    @Convert(converter = OperationConverter.class)
    private Operation operation;
}
