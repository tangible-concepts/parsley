package de.tngbl.parsley.onlinewardleymaps;

import de.tngbl.parsley.wardleymapping.MapSemanticsViolated;
import io.quarkus.logging.Log;

import java.util.List;

/**
 * Examples:
 *
 * Public->Cup of Tea
 * Cup of Tea->Cup
 */
public class LinkExpression extends Expression {

    private final Identifier from;
    private final Identifier to;

    public LinkExpression(List<Token> tokens) {
        super(tokens);

        from = tokens.get(0) instanceof Identifier? (Identifier) tokens.get(0) : new Identifier((StringLiteral) tokens.get(0));
        to = tokens.get(2) instanceof Identifier? (Identifier) tokens.get(2) : new Identifier((StringLiteral) tokens.get(2));

    }

    @Override
    public boolean isApplicable(List<Token> tokens) {

        return tokens.size() >= 3 &&
                tokens.get(0) instanceof StringLiteral &&
                tokens.get(1) instanceof LinkOperator &&
                tokens.get(2) instanceof StringLiteral;

    }

    @Override
    public InterpreterContext evaluate(InterpreterContext context) {

        String from = this.from.getName();
        String to = this.to.getName();

        if (! context.getKnownIdentifier().contains(from) ) {
            Log.warn(String.format(UNDECLARED_IDENTIFIER_MESSAGE + " Skipping relationship %s -> %s ", from, from, to));
            return context;
        }

        if (! context.getKnownIdentifier().contains(to) ) {
            Log.warn(String.format(UNDECLARED_IDENTIFIER_MESSAGE + " Skipping relationship %s -> %s ", to, from, to));
            return context;
        }

        try {
            context.getMap().addLink(from, to);
        } catch (MapSemanticsViolated v) {
            Log.error(String.format("Map semantics violated when creating Link: %s", v.getLocalizedMessage()));
        }
        return context;
    }

    public Identifier getFrom() {
        return from;
    }

    public Identifier getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "LinkExpression{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
