package fr.arolla;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class Analyzer {

    public static void analyze(String text, ParseTreeListener listener) {
        fr.arolla.lexer.FrenchyLexer lexer = new fr.arolla.lexer.FrenchyLexer(CharStreams.fromString(text));
        // Get a list of matched tokens
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Pass the tokens to the parser
        fr.arolla.parser.FrenchyParser parser = new fr.arolla.parser.FrenchyParser(tokens);

        fr.arolla.parser.FrenchyParser.ProgramContext program = parser.program();

        // Walk it and attach our listener
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, program);
    }

}
