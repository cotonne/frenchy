package fr.arolla.variables;

import fr.arolla.values.Value;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class Variable implements Value {
    private final String name;
    public final Value value;

    private Variable(String name, Value value) {
        this.name = name;
        this.value = value;
    }

    public static Variable of(String name, Value value) {
        return new Variable(name, value);
    }

    @Override
    public Object value() {
        return value.value();
    }
}
