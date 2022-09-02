package de.tngbl.parsley.wardleymapping;

import org.jmolecules.ddd.annotation.Entity;

import java.util.LinkedList;
import java.util.List;

/**
 * Baseclasse for everything on a map that has a name and a position;
 */
@Entity
public class Component extends MapElement implements Named{

    protected final Name name;
    protected final VisibilityLevel visibility;
    protected final EvolutionLevel evolution;

    protected final List<Movement> movements = new LinkedList<>();

    public Component(Name name, VisibilityLevel visibility, EvolutionLevel evolution) {
        this.name = name;
        this.visibility = visibility;
        this.evolution = evolution;
    }

    // TODO should not be public, responsibility for evolution should reside in this class
    public void addMovement(Movement movement) {
        movements.add(movement);
    }

    @Override
    public Name getName() {
        return name;
    }

    public VisibilityLevel getVisibility() {
        return visibility;
    }

    public EvolutionLevel getEvolution() {
        return evolution;
    }

    public List<Movement> getMovements() {
        return movements;
    }

    @Override
    public String toString() {
        return "Component{" +
                "name=" + name +
                ", visibility=" + visibility +
                ", evolution=" + evolution +
                '}';
    }
}
