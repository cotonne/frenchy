package fr.arolla.operations;

import fr.arolla.values.Value;

import java.util.Arrays;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Operations {
    ADD("+", new AddOperation()), EQUALS("égale", new EqualsOperation());

    public final String name;
    public final BiFunction<Value, Value, Value> o;
    private final static Map<String, Operations> operations = Arrays.stream(Operations.values())
            .collect(Collectors.toMap(Operations::getName, Function.identity()));

    Operations(String name, BiFunction<Value, Value, Value> o) {
        this.name = name;
        this.o = o;
    }

    public String getName() {
        return name;
    }

    public static Operations byName(String operation) {
        return operations.get(operation);
    }
}
