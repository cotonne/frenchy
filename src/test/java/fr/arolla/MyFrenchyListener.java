package fr.arolla;

import fr.arolla.parser.FrenchyListener;
import fr.arolla.parser.FrenchyParser.ProgramContext;
import fr.arolla.variables.Variable;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.IntStream;

public class MyFrenchyListener implements FrenchyListener {
    public Collection<Variable> variables = new HashSet<Variable>();
    private int currentValue;

    public void visitTerminal(TerminalNode node) {

    }

    public void visitErrorNode(ErrorNode node) {

    }

    public void enterEveryRule(ParserRuleContext ctx) {
        System.out.println("enterEveryRule");
        System.out.println(ctx);
    }

    public void exitEveryRule(ParserRuleContext ctx) {
        System.out.println("exitEveryRule");
        System.out.println(ctx);
    }

    public void enterProgram(fr.arolla.parser.FrenchyParser.ProgramContext ctx) {

    }

    public void exitProgram(fr.arolla.parser.FrenchyParser.ProgramContext ctx) {

    }

    public void enterStatement(fr.arolla.parser.FrenchyParser.StatementContext ctx) {
        currentValue = ctx.VALUE().stream()
                .map(TerminalNode::getSymbol)
                .map(Token::getText)
                .mapToInt(Integer::parseInt).sum();
    }

    public void exitStatement(fr.arolla.parser.FrenchyParser.StatementContext ctx) {

    }

    public void enterVariable(fr.arolla.parser.FrenchyParser.VariableContext ctx) {
    }

    public void exitVariable(fr.arolla.parser.FrenchyParser.VariableContext ctx) {
        variables.add(Variable.of(ctx.WORD().getText(), currentValue));
    }

}
