package fr.arolla;

import fr.arolla.variables.Variable;
import org.junit.Before;
import org.junit.Test;

import static fr.arolla.Analyzer.analyze;
import static org.assertj.core.api.Assertions.assertThat;

public class BooleanVariableTest {
    private MyFrenchyListener listener;

    @Before
    public void setUp() {
        listener = new MyFrenchyListener();
    }

    @Test
    public void should_exist() {
        String text = "soit x valant vrai";

        analyze(text, listener);

        assertThat(listener.variablesByName).containsValues(Variable.of("x", true));
    }


    @Test
    public void should_create_a_initialized_variable() {
        String text = "soit x valant 1 egale 1";

        analyze(text, listener);

        assertThat(listener.variablesByName.values()).containsExactly(Variable.of("x", true));
    }
}
