package de.tngbl.parsley.onlinewardleymaps;

import de.tngbl.parsley.wardleymapping.*;
import io.quarkus.logging.Log;

import java.util.List;

public class InertiaHintStrategy implements InterpreterHintStrategy {

    private final InertiaHint hint;

    public InertiaHintStrategy(InertiaHint hint) {
        this.hint = hint;
    }

    @Override
    public void applyTo(WardleyMap map) {

        Component component = hint.getComponent();
        List<Movement> movements = component.getMovements();

        if (movements.size() == 0) {
            Log.warn(String.format("No Movement found for Component %s, thus skipping creation of Inertia for this component", component));
        } else if (movements.size() > 1) {
            Log.warn(String.format("Multiple Movm,ents found for Component %s! While Parsley allows this, OWM grammar constraints apply. Skipping creation of inertia.", component));
        }

        Movement movement = component.getMovements().get(0);
        movement.addInertia(getInertiaForMovement(movement));

    }

    private Inertia getInertiaForMovement(Movement movement) {

        float fromEvolution = movement.getFrom().getEvolution().value();
        float toEvolution = movement.getTo().getEvolution().value();
        float inertiaEvolution = (toEvolution + fromEvolution) / 2;

        return new Inertia(movement,EvolutionLevel.of(inertiaEvolution));
    }
}
