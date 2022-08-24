package de.tngbl.parsley.onlinewardleymaps;

import io.quarkus.logging.Log;

import java.util.List;

public class MarketExpression extends ComponentExpression {

    private final String name;

    public MarketExpression(List<Token> tokens) {
        super(tokens);
        this.name = ((Identifier) tokens.get(1)).getName();
    }

    @Override
    public boolean isApplicable(List<Token> tokens) {

        return tokens.size() >= 3 &&
                tokens.get(0) == Commands.MARKET &&
                tokens.get(1) instanceof Identifier &&
                tokens.get(2) instanceof PositionLiteral;
    }

    @Override
    public InterpreterContext evaluate(InterpreterContext context) {

        Log.error(String.format("MarketExpressions are not supported yet! Expressing Market '%s' as component!", name));
        return super.evaluate(context);
    }
}
