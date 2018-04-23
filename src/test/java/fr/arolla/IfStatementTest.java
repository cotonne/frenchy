package fr.arolla;

import fr.arolla.values.IntegerValue;
import fr.arolla.variables.Variable;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IfStatementTest {
    private MyFrenchyVisitor visitor;

    @Before
    public void setUp() {
        visitor = new MyFrenchyVisitor();
    }

    @Test
    public void should_create_an_initialized_variable() {
        String text = "soit x valant si vrai alors 1 sinon 0";

        MyFrenchyVisitor context = visitor.visit(text);

        assertThat(context.stack).containsExactly(Variable.of("x", new IntegerValue(1)));
    }

}
