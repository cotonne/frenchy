package fr.arolla;

import fr.arolla.parser.FrenchyListener;
import fr.arolla.parser.FrenchyParser.ProgramContext;
import fr.arolla.parser.FrenchyParser.VariableContext;
import fr.arolla.variables.Variable;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Collection;
import java.util.HashSet;

public class MyFrenchyListener implements FrenchyListener {
    public Collection<Variable> variables = new HashSet<Variable>();

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

    public void exitVariable(VariableContext ctx) {
        System.out.println("exitVariable");
    }

    public void enterVariable(VariableContext ctx) {
        variables.add(Variable.of(ctx.WORD().getText(), Integer.parseInt(ctx.VALUE().getSymbol().getText())));
    }

    public void exitProgram(ProgramContext ctx) {

    }

    public void enterProgram(ProgramContext ctx) {

    }
}
