package fr.arolla.operations;

import fr.arolla.values.IntegerValue;
import fr.arolla.values.Value;

import java.util.function.BiFunction;

public class AddOperation implements BiFunction<Value, Value, Value> {
    @Override
    public IntegerValue apply(Value left, Value right) {
        return new IntegerValue((int) left.value() + (int) right.value());
    }
}
