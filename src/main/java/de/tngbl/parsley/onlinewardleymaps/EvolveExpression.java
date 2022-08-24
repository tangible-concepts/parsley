package de.tngbl.parsley.onlinewardleymaps;

import io.quarkus.logging.Log;

import java.util.List;

public class EvolveExpression extends Expression {

    private final Identifier component;
    private final float evolution;

    public EvolveExpression(List<Token> tokens) {
        super(tokens);

        component = tokens.get(1) instanceof Identifier? (Identifier) tokens.get(1) : new Identifier((StringLiteral) tokens.get(1));
        this.evolution = ((NumberLiteral)tokens.get(2)).getNumber();
    }

    @Override
    public boolean isApplicable(List<Token> tokens) {

        return (tokens.get(0) == Commands.EVOLVE &&
            tokens.get(1) instanceof StringLiteral &&
            tokens.get(2) instanceof NumberLiteral);
    }

    @Override
    public InterpreterContext evaluate(InterpreterContext context) {

        if (! context.getKnownIdentifier().contains(component.getName()) ) {
            Log.warn(String.format("Undeclared identifier, skipping evolution for: %s", component));
            return context;
        }

        context.getMap().evolveComponent(component.getName(), evolution);
        return context;
    }

}
