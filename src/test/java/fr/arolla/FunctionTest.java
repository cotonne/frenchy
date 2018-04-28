package fr.arolla;

import fr.arolla.values.IntegerValue;
import fr.arolla.variables.Variable;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FunctionTest {

    private MyFrenchyVisitor visitor;

    @Before
    public void setUp() {
        visitor = new MyFrenchyVisitor();
    }

    @Test
    public void should_assign_the_returned_value_of_a_function() {
        String text = "étant donné la fonction f retournant 2 alors\n" +
                "soit x valant f";

        MyFrenchyVisitor context = visitor.visit(text);

        assertThat(context.stack).containsExactly(Variable.of("x", new IntegerValue(2)));
    }


    @Test
    public void should_assign_the_returned_value_of_a_function_with_statements() {
        String text = "étant donné la fonction f retournant 1 + 1 alors\n" +
                "soit x valant f";

        MyFrenchyVisitor context = visitor.visit(text);

        assertThat(context.stack).containsExactly(Variable.of("x", new IntegerValue(2)));
    }

    @Test
    public void should_assign_the_returned_value_of_a_function_according_to_the_context() {
        String text = "étant donné la fonction f retournant " +
                "   soit z valant 2\n" +
                "   x + z " +
                "alors\n" +
                "soit x valant 1\n" +
                "soit y valant f";

        MyFrenchyVisitor context = visitor.visit(text);

        assertThat(context.stack).contains(Variable.of("y", new IntegerValue(3)));
    }

    @Test
    public void should_assign_the_returned_value_of_a_function_according_to_the_context_of_the_function() {
        String text = "étant donné la fonction f retournant " +
                "   soit z valant 2\n" +
                "   x + z " +
                "alors\n" +
                "soit z valant 100\n" +
                "soit x valant 1\n" +
                "soit y valant f\n" +
                "soit w valant z";

        MyFrenchyVisitor context = visitor.visit(text);

        assertThat(context.stack).containsOnly(
                Variable.of("x", new IntegerValue(1)),
                Variable.of("y", new IntegerValue(3)),
                Variable.of("z", new IntegerValue(100)),
                Variable.of("w", new IntegerValue(100))
        );
    }
}
