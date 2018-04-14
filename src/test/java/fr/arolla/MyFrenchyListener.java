package fr.arolla;

import fr.arolla.parser.FrenchyListener;
import fr.arolla.variables.Variable;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashMap;
import java.util.Map;

public class MyFrenchyListener implements FrenchyListener {
    public Map<String, Variable> variablesByName = new HashMap<>();
    private int currentValue;

    public void visitTerminal(TerminalNode node) {

    }

    public void visitErrorNode(ErrorNode node) {

    }

    public void enterEveryRule(ParserRuleContext ctx) {

    }

    public void exitEveryRule(ParserRuleContext ctx) {

    }

    public void enterProgram(fr.arolla.parser.FrenchyParser.ProgramContext ctx) {

    }

    public void exitProgram(fr.arolla.parser.FrenchyParser.ProgramContext ctx) {

    }

    public void enterStatement(fr.arolla.parser.FrenchyParser.StatementContext ctx) {
        currentValue = 0;
    }

    public void exitStatement(fr.arolla.parser.FrenchyParser.StatementContext ctx) {

    }

    public void enterVariable(fr.arolla.parser.FrenchyParser.VariableContext ctx) {
    }

    public void exitVariable(fr.arolla.parser.FrenchyParser.VariableContext ctx) {
        String name = ctx.WORD().getText();
        variablesByName.put(name, Variable.of(name, currentValue));
    }

    public void enterElement(fr.arolla.parser.FrenchyParser.ElementContext ctx) {
        if (ctx.VALUE() != null) {
            currentValue += Integer.parseInt(ctx.VALUE().getSymbol().getText());
        } else {
            currentValue += variablesByName.get(ctx.WORD().getText()).value;
        }
    }

    public void exitElement(fr.arolla.parser.FrenchyParser.ElementContext ctx) {

    }
}
