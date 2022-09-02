package de.tngbl.parsley.wardleymapping;

import java.util.Optional;

/**
 * Inertia is tied to a specific {@link Movement}, where the Movement can have more than one Inertia.
 */
public class Inertia extends MapElement{

    private final Movement movement;
    private final EvolutionLevel evolution;
    private InertiaText text;

    public Inertia(Movement movement, EvolutionLevel evolution) {
        this.movement = movement;
        this.evolution = evolution;
    }

    public Inertia(Movement movement, EvolutionLevel evolution, InertiaText text) {
        this(movement, evolution);
        this.text = text;
    }

    public Movement getMovement() {
        return movement;
    }

    public EvolutionLevel getEvolution() {
        return evolution;
    }

    public Optional<InertiaText> getText() {
        return Optional.ofNullable(text);
    }


}
