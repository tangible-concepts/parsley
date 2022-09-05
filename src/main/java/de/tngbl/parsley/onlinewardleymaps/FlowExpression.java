package de.tngbl.parsley.onlinewardleymaps;

import io.quarkus.logging.Log;

import java.util.List;

public class FlowExpression extends LinkExpression {

    private final FlowOperator operator;

    public FlowExpression(List<Token> tokens) {
        super(tokens);
        this.operator = (FlowOperator) tokens.get(1);
    }

    @Override
    public boolean isApplicable(List<Token> tokens) {
        return tokens.get(0) instanceof StringLiteral &&
                tokens.get(1) instanceof FlowOperator &&
                tokens.get(2) instanceof StringLiteral;
    }

    @Override
    public InterpreterContext evaluate(InterpreterContext context) {

        Log.warn(String.format("FlowExpressions are not supported yet, treating Flow as Link: %s", toString()));
        return super.evaluate(context);
    }

    @Override
    public String toString() {
        return "FlowExpression{" +
                "from=" + from +
                ", operator=" + operator.getOperator() +
                ", to=" + to +
                '}';
    }
}
