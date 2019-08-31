package com.exercise.quotes.entities.operation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum Operation {
    CREATE(1), UPDATE(2), DELETE(3);

    private Integer id;

    public static Operation getById(Integer id) {
        return Arrays.stream(values())
                .filter(operation -> operation.getId().equals(id))
                .findFirst()
                .get();
    }
}
