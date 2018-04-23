package fr.arolla;

import fr.arolla.frenchy.FrenchyLexer;
import fr.arolla.frenchy.FrenchyParser;
import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import javax.swing.*;
import java.util.Arrays;

public class VisualizeAST {
    public static void main(String[] args) {
        //prepare token stream
        String s = "étant donné la fonction f retournant 2 alors " +
                "soit x valant f";
        FrenchyLexer lexer = new FrenchyLexer(CharStreams.fromString(s));
        // Get a list of matched tokens
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Pass the tokens to the parser
        FrenchyParser parser = new FrenchyParser(tokens);

        FrenchyParser.ProgramContext tree = parser.program();

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
