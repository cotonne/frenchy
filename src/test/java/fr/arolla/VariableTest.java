package fr.arolla;

import fr.arolla.values.IntegerValue;
import fr.arolla.variables.Variable;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VariableTest {

    private MyFrenchyVisitor visitor;

    @Before
    public void setUp() {
        visitor = new MyFrenchyVisitor();
    }

    @Test
    public void should_create_a_initialized_variable() {
        String text = "soit x valant 1";

        MyFrenchyVisitor context = visitor.visit(text);

        assertThat(context.stack)
                .containsExactly(Variable.of("x", new IntegerValue(1)));
    }

    @Test
    public void should_perform_a_simple_addition() {
        String text = "soit x valant 1 + 1";

        MyFrenchyVisitor context = visitor.visit(text);

        assertThat(context.stack)
                .containsExactly(Variable.of("x", new IntegerValue(2)));
    }


    @Test
    public void should_manage_multiple_variables() {
        String text = "soit x valant 1 + 1\n" +
                "soit y valant 1 + 2";

        MyFrenchyVisitor context = visitor.visit(text);

        assertThat(context.stack).containsExactly(
                Variable.of("x", new IntegerValue(2)),
                Variable.of("y", new IntegerValue(3)));
    }


    @Test
    public void should_add_two_variables() {
        String text = "soit x valant 1 + 1\n" +
                "soit y valant 1 + 2\n" +
                "soit z valant x + y";

        MyFrenchyVisitor context = visitor.visit(text);

        assertThat(context.stack)
                .contains(Variable.of("z", new IntegerValue(5)));
    }

    @Test
    public void should_assign_a_value() {
        String text = "soit x valant 1 + 1\n" +
                "soit z valant x";

        MyFrenchyVisitor context = visitor.visit(text);

        assertThat(context.stack)
                .contains(Variable.of("z", new IntegerValue(2)));
    }
}
