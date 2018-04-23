package fr.arolla;

import fr.arolla.values.BooleanValue;
import fr.arolla.variables.Variable;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BooleanVariableTest {
    private MyFrenchyVisitor visitor;

    @Before
    public void setUp() {
        visitor = new MyFrenchyVisitor();
    }

    @Test
    public void should_exist() {
        String text = "soit x valant vrai";

        MyFrenchyVisitor context = visitor.visit(text);

        assertThat(context.stack).contains(Variable.of("x", new BooleanValue(true)));
    }


    @Test
    public void should_create_a_initialized_variable() {
        String text = "soit x valant 1 égale 0";

        MyFrenchyVisitor context = visitor.visit(text);

        assertThat(context.stack).containsExactly(Variable.of("x", new BooleanValue(false)));
    }
}
