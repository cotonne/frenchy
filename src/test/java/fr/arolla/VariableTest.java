package fr.arolla;

import edu.emory.mathcs.backport.java.util.Collections;
import fr.arolla.parser.FrenchyParser;
import fr.arolla.variables.Variable;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class VariableTest {

    @Test
    public void should_create_a_initialized_variable() {
        MyFrenchyListener listener = new MyFrenchyListener();

        String text = "soit x valant 1";

        fr.arolla.lexer.FrenchyLexer lexer = new fr.arolla.lexer.FrenchyLexer(CharStreams.fromString(text));
        // Get a list of matched tokens
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Pass the tokens to the parser
        fr.arolla.parser.FrenchyParser parser = new fr.arolla.parser.FrenchyParser(tokens);

        FrenchyParser.ProgramContext program = parser.program();

        // Walk it and attach our listener
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, program);

        assertThat(listener.variables).containsExactly(Variable.of("x", 1));
    }

    @Test
    public void should_perform_a_simple_addition() {
        MyFrenchyListener listener = new MyFrenchyListener();

        String text = "soit x valant 1 + 1";

        fr.arolla.lexer.FrenchyLexer lexer = new fr.arolla.lexer.FrenchyLexer(CharStreams.fromString(text));
        // Get a list of matched tokens
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Pass the tokens to the parser
        fr.arolla.parser.FrenchyParser parser = new fr.arolla.parser.FrenchyParser(tokens);

        FrenchyParser.ProgramContext program = parser.program();

        // Walk it and attach our listener
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, program);

        assertThat(listener.variables).containsExactly(Variable.of("x", 2));
    }

}
