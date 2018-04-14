package fr.arolla.variables;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class Variable {
    private final String name;
    public final int value;

    private Variable(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public static Variable of(String name, int value) {
        return new Variable(name, value);
    }
}
