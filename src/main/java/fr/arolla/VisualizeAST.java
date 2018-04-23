package fr.arolla;

import fr.arolla.frenchy.FrenchyLexer;
import fr.arolla.frenchy.FrenchyParser;
import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import javax.swing.*;
import java.util.Arrays;

public class VisualizeAST {
    public static void main(String[] args) {
        //prepare token stream
        String s = "soit x valant si vrai alors 1 sinon 0";
        FrenchyLexer lexer = new FrenchyLexer(CharStreams.fromString(s));
        // Get a list of matched tokens
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Pass the tokens to the parser
        FrenchyParser parser = new FrenchyParser(tokens);

        FrenchyParser.ProgramContext program = parser.program();
        ParseTree tree = program.getChild(0);

        //show AST in console
        System.out.println(tree.toStringTree(parser));

        //show AST in GUI
        JFrame frame = new JFrame("Antlr AST");
        JPanel panel = new JPanel();
        TreeViewer viewr = new TreeViewer(Arrays.asList(
                parser.getRuleNames()), tree);
        viewr.setScale(1.5);//scale a little
        panel.add(viewr);
        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(200, 200);
        frame.setVisible(true);
    }
}
