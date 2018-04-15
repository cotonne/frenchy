package fr.arolla.operations;

import fr.arolla.values.BooleanValue;
import fr.arolla.values.Value;

import java.util.function.BiFunction;

public class EqualsOperation implements BiFunction<Value, Value, Value> {
    @Override
    public Value apply(Value left, Value right) {
        return new BooleanValue(left.value() == right.value());
    }
}
