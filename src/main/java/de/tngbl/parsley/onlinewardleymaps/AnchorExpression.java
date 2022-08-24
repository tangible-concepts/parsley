package de.tngbl.parsley.onlinewardleymaps;

import io.quarkus.logging.Log;

import java.util.List;

public class AnchorExpression extends Expression {

    private final String name;
    private final float visibility;
    private final float evolution;

    public AnchorExpression(List<Token> tokens) {
        super(tokens);

        this.name = ((Identifier) tokens.get(1)).getName();
        this.evolution = ((PositionLiteral) tokens.get(2)).getX();
        this.visibility = ((PositionLiteral) tokens.get(2)).getY();

    }

    @Override
    public boolean isApplicable(List<Token> tokens) {
        return tokens.size() >= 3 &&
                tokens.get(1) instanceof Identifier &&
                tokens.get(2) instanceof PositionLiteral;
    }

    @Override
    public InterpreterContext evaluate(InterpreterContext context) {

        try {
            context.addIdentifier(name);
            context.getMap().addAnchor(name, visibility, evolution);
        } catch (ParserException e) {
            Log.error("Component with identifier '" + name + "'already existis! Skipping component creation.");
        }

        return context;
    }

    @Override
    public String toString() {
        return "AnchorExpression{" +
                "name='" + name + '\'' +
                ", visibility=" + visibility +
                ", evolution=" + evolution +
                '}';
    }
}
