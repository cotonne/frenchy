package fr.arolla.values;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class BooleanValue implements Value {
    private final boolean aBoolean;

    public BooleanValue(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    @Override
    public Object value() {
        return aBoolean;
    }
}
