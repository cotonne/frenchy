package fr.arolla;

import fr.arolla.operations.Operations;
import fr.arolla.values.BooleanValue;
import fr.arolla.values.IntegerValue;
import fr.arolla.values.Value;
import fr.arolla.variables.Variable;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class MyFrenchyListener extends fr.arolla.parser.FrenchyBaseListener {
    public static final String VRAI = "vrai";
    public Map<String, Variable> variablesByName = new HashMap<>();
    private Stack<Object> stack = new Stack<>();

    public void exitVariableDefinition(fr.arolla.parser.FrenchyParser.VariableDefinitionContext ctx) {
        String name = ctx.WORD().getText();
        variablesByName.put(name, Variable.of(name, (Value) stack.pop()));
    }

    public void enterElement(fr.arolla.parser.FrenchyParser.ElementContext ctx) {
        Value right;
        if (ctx.VALUE() != null) {
            right = new IntegerValue(Integer.parseInt(ctx.VALUE().getSymbol().getText()));
        } else if (ctx.BOOLEAN() != null) {
            right = new BooleanValue("vrai".equals(ctx.BOOLEAN().getText()));
        } else {
            right = variablesByName.get(ctx.WORD().getText());
        }

        if (!stack.isEmpty()) {
            String operation = (String) stack.pop();
            Value left = (Value) stack.pop();
            right = Operations.byName(operation).o.apply(right, left);
        }
        stack.push(right);
    }

    public void enterOperation(fr.arolla.parser.FrenchyParser.OperationContext ctx) {
        if (ctx.ADD() != null) {
            stack.push(ctx.ADD().getSymbol().getText());
        } else {
            stack.push(ctx.EQUALS().getSymbol().getText());
        }
    }

}
