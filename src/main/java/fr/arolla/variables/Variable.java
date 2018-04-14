package fr.arolla.variables;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class Variable<T> {
    private final String name;
    public final T value;

    private Variable(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public static <T> Variable of(String name, T value) {
        return new Variable<T>(name, value);
    }
}
