package com.exercise.quotes.entities.operation;

import javax.persistence.AttributeConverter;

public class OperationConverter implements AttributeConverter<Operation, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Operation operation) {
        return operation.getId();
    }

    @Override
    public Operation convertToEntityAttribute(Integer id) {
        return Operation.getById(id);
    }
}
