package de.tngbl.parsley.wardleymapping;

import org.jmolecules.ddd.annotation.Entity;

import java.util.LinkedList;
import java.util.List;

/**
 * Baseclasse for everything on a map that has a name and a position;
 */
@Entity
public class Component extends ComponentBase {

    protected final List<Movement> movements = new LinkedList<>();

    public Component(Name name, VisibilityLevel visibility, EvolutionLevel evolution) {
        super(name, visibility, evolution);
    }

    // TODO should not be public, responsibility for evolution should reside in this class
    public void addMovement(Movement movement) {
        movements.add(movement);
    }

    public List<Movement> getMovements() {
        return movements;
    }

    @Override
    public String toString() {
        return "Component{" +
                "name=" + getName() +
                ", visibility=" + getVisibility() +
                ", evolution=" + getEvolution() +
                '}';
    }
}
