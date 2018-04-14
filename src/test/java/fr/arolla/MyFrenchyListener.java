package fr.arolla;

import fr.arolla.parser.FrenchyListener;
import fr.arolla.variables.Variable;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashMap;
import java.util.Map;

public class MyFrenchyListener implements FrenchyListener {
    public static final String VRAI = "vrai";
    public Map<String, Variable> variablesByName = new HashMap<>();
    private int currentValue;
    private boolean isBoolean;
    private boolean currentBooleanValue;

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
        if (ctx.BOOLEAN() == null) {
            isBoolean = false;
            currentValue = 0;
        } else {
            isBoolean = true;
            currentBooleanValue = VRAI.equals(ctx.BOOLEAN().getText());
        }
    }

    public void exitStatement(fr.arolla.parser.FrenchyParser.StatementContext ctx) {

    }

    public void enterVariable(fr.arolla.parser.FrenchyParser.VariableContext ctx) {
    }

    public void exitVariable(fr.arolla.parser.FrenchyParser.VariableContext ctx) {
        String name = ctx.WORD().getText();
        Variable variable;
        if (isBoolean) {
            variable = Variable.of(name, currentBooleanValue);
        } else {
            variable = Variable.of(name, currentValue);
        }
        variablesByName.put(name, variable);
    }

    public void enterElement(fr.arolla.parser.FrenchyParser.ElementContext ctx) {
        if (ctx.VALUE() != null) {
            currentValue += Integer.parseInt(ctx.VALUE().getSymbol().getText());
        } else {
            currentValue += (int) variablesByName.get(ctx.WORD().getText()).value;
        }
    }

    public void exitElement(fr.arolla.parser.FrenchyParser.ElementContext ctx) {

    }
}
