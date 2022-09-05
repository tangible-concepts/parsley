package de.tngbl.parsley.onlinewardleymaps;

import de.tngbl.parsley.wardleymapping.Component;
import de.tngbl.parsley.wardleymapping.MapSemanticsViolated;
import io.quarkus.logging.Log;

import java.util.List;

/**
 *
 * In the OWM grammar inertia is tied to a component, while parsleys domain model defines inertia as a charakteristic of Movement.
 * This mismatch needs to be addressed when resolving the component expression.
 *
 * Examples:
 * component Cup of Tea [0.79, 0.61] label [19, -4]
 * component Water [0.73, 0.78]
 */
public class ComponentExpression extends Expression {

    private final String name;
    private final float evolution;
    private final float visibility;
    private final boolean hasInertia;

    public ComponentExpression(List<Token> tokens) {
        super(tokens);
        this.name = ((Identifier) tokens.get(1)).getName();
        this.evolution = ((PositionLiteral) tokens.get(2)).getX();
        this.visibility = ((PositionLiteral) tokens.get(2)).getY();
        this.hasInertia = tokens.contains(new StringLiteral("inertia"));
    }

    @Override
    public boolean isApplicable(List<Token> tokens) {
        return tokens.size() >= 3 &&
                tokens.get(1) instanceof Identifier &&
                tokens.get(2) instanceof PositionLiteral;
    }

    @Override
    public InterpreterContext evaluate(InterpreterContext context) {

        float normalizedEvolution = normalizeToScale(evolution);
        float normalizedVisibility = normalizeToScale(visibility);

        try {
            context.addIdentifier(name);
            Component createdComponent = context.getMap().addComponent(name, normalizedVisibility, normalizedEvolution);

            if (hasInertia) {
                context.addHint(new InertiaHint(createdComponent));
            }

        } catch (ParserException e) {
            Log.error("Component with identifier '" + name + "'already existis! Skipping component creation.");
        } catch (MapSemanticsViolated v) {
            Log.error(String.format("Map semantics violated: '%s", v.getLocalizedMessage()));
        }

        return context;
    }

    private float normalizeToScale(float in) {

        float out;

        if (in < 0) {
            out = 0f;
        } else if (in > 1) {
            out = 1f;
        } else {
            out = in;
        }

        return out;
    }

    @Override
    public String toString() {
        return "ComponentExpression{" +
                "name='" + name + '\'' +
                ", evolution=" + evolution +
                ", visibility=" + visibility +
                '}';
    }
}
