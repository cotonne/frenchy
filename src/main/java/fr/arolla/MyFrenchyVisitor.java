package fr.arolla;

import fr.arolla.frenchy.FrenchyLexer;
import fr.arolla.frenchy.FrenchyParser;
import fr.arolla.frenchy.FrenchyParser.*;
import fr.arolla.frenchy.FrenchyVisitor;
import fr.arolla.operations.Operations;
import fr.arolla.values.BooleanValue;
import fr.arolla.values.IntegerValue;
import fr.arolla.values.Value;
import fr.arolla.variables.Variable;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.*;
import java.util.function.BiFunction;

public class MyFrenchyVisitor implements FrenchyVisitor<MyFrenchyVisitor> {
    public static final String VRAI = "vrai";

    public Stack<Object> stack = new Stack<>();
    private Map<String, Variable> variablesByName = new HashMap<>();
    private Map<String, List<FunctionStatementContext>> functionsByName = new HashMap<>();

    @Override
    public MyFrenchyVisitor visitProgram(ProgramContext ctx) {
        for (VariableDefinitionContext context : ctx.variableDefinition()) {
            MyFrenchyVisitor accept = context.accept(this);
            stack.push(accept.stack.pop());
        }
        return this;
    }

    @Override
    public MyFrenchyVisitor visitElement(ElementContext ctx) {
        Value item;
        if (ctx.VALUE() != null) {
            int value = Integer.valueOf(ctx.VALUE().getSymbol().getText());
            item = new IntegerValue(value);
        } else if (ctx.BOOLEAN() != null) {
            boolean value = VRAI.equals(ctx.BOOLEAN().getText());
            item = new BooleanValue(value);
        } else {
            String text = ctx.WORD().getText();
            if (variablesByName.containsKey(text)) {
                item = variablesByName.get(text).value;
            } else {
                item = evaluate(functionsByName.get(text));
            }
        }
        stack.push(item);
        return this;
    }

    private Value evaluate(List<FunctionStatementContext> functionDefinitionContexts) {
        MyFrenchyVisitor visitor = this;
        for (FunctionStatementContext line : functionDefinitionContexts) {
            visitor = line.accept(visitor);
        }
        return (Value) visitor.stack.pop();
    }

    @Override
    public MyFrenchyVisitor visitOperation(OperationContext ctx) {
        String text = ctx.getText();
        stack.push(Operations.byName(text).o);
        return this;
    }

    @Override
    public MyFrenchyVisitor visitIfStatement(IfStatementContext ctx) {
        MyFrenchyVisitor visitor = ctx.condition().accept(this);
        BooleanValue value = (BooleanValue) visitor.stack.pop();
        if ((boolean) value.value()) {
            return ctx.statementThen().accept(this);
        } else {
            return ctx.statementElse().accept(this);
        }
    }

    @Override
    public MyFrenchyVisitor visitStatementThen(StatementThenContext ctx) {
        return ctx.statement().accept(this);
    }

    @Override
    public MyFrenchyVisitor visitStatementElse(StatementElseContext ctx) {
        return ctx.statement().accept(this);
    }

    @Override
    public MyFrenchyVisitor visitStatement(StatementContext ctx) {
        List<Object> l = new ArrayList<>();
        for (int i = 0; i < ctx.getChildCount(); i++) {
            l.add(ctx.getChild(i).accept(this).stack.pop());
        }
        while (l.size() > 1) {
            Value left = (Value) l.remove(0);
            BiFunction<Value, Value, Value> operation = (BiFunction<Value, Value, Value>) l.remove(0);
            Value right = (Value) l.remove(0);
            l.add(0, operation.apply(left, right));
        }
        stack.push(l.get(0));
        return this;
    }

    @Override
    public MyFrenchyVisitor visitCondition(ConditionContext ctx) {
        boolean condition;
        if (ctx.BOOLEAN() != null) {
            condition = VRAI.equals(ctx.BOOLEAN().getText());
        } else {
            Value left = (Value) ctx.element(0).accept(this).stack.pop();
            Value right = (Value) ctx.element(1).accept(this).stack.pop();
            condition = left.equals(right);
        }
        this.stack.push(new BooleanValue(condition));
        return this;
    }

    @Override
    public MyFrenchyVisitor visitVariableDefinition(VariableDefinitionContext ctx) {
        MyFrenchyVisitor context = ctx.statement().accept(this);
        Value x = (Value) context.stack.pop();
        String name = ctx.WORD().getSymbol().getText();
        Variable variable = Variable.of(name, x);
        stack.push(variable);
        variablesByName.put(name, variable);
        return this;
    }

    @Override
    public MyFrenchyVisitor visitFunctionStatement(FunctionStatementContext ctx) {
        for (int i = 0; i < ctx.getChildCount(); i++) {
            ctx.getChild(i).accept(this);
        }
        return this;
    }

    @Override
    public MyFrenchyVisitor visitFunctionDefinition(FunctionDefinitionContext ctx) {
        functionsByName.put(ctx.WORD().getText(), ctx.functionStatement());
        return this;
    }

    @Override
    public MyFrenchyVisitor visit(ParseTree tree) {
        for (int i = 0; i < tree.getChildCount(); i++) {
            MyFrenchyVisitor aStack = tree.getChild(i).accept(this);
            if (!aStack.stack.isEmpty()) {
                stack.push(aStack.stack.pop());
            }
        }
        return this;
    }

    @Override
    public MyFrenchyVisitor visitChildren(RuleNode node) {
        for (int i = 0; i < node.getChildCount(); i++) {
            node.getChild(i).accept(this);
        }
        return this;
    }

    @Override
    public MyFrenchyVisitor visitTerminal(TerminalNode node) {
        return this;
    }

    @Override
    public MyFrenchyVisitor visitErrorNode(ErrorNode node) {
        System.err.println(node);
        return this;
    }

    MyFrenchyVisitor visit(String text) {
        FrenchyLexer lexer = new FrenchyLexer(CharStreams.fromString(text));
        // Get a list of matched tokens
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Pass the tokens to the parser
        FrenchyParser parser = new FrenchyParser(tokens);

        return visit(parser.program());
    }
}
