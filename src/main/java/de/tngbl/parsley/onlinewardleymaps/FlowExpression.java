package de.tngbl.parsley.onlinewardleymaps;

import io.quarkus.logging.Log;

import java.util.List;

public class FlowExpression extends Expression {


    public FlowExpression(List<Token> tokens) {
        super(tokens);
    }

    @Override
    public boolean isApplicable(List<Token> tokens) {
        return tokens.get(0) instanceof StringLiteral &&
                tokens.get(1) instanceof FlowOperator &&
                tokens.get(2) instanceof StringLiteral;
    }

    @Override
    public InterpreterContext evaluate(InterpreterContext context) {

        Log.error("FlowExpressions are not supported yet!");
        return context;
    }
}
