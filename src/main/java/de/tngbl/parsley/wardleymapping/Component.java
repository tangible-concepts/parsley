package de.tngbl.parsley.wardleymapping;

import org.jmolecules.ddd.annotation.Entity;

/**
 * Baseclasse for everything on a map that has a name and a position;
 */
@Entity
public class Component extends MapElement implements Named{

    protected final Name name;
    protected final VisibilityLevel visibility;
    protected final EvolutionLevel evolution;
    protected final boolean inertia;

    public Component(Name componentName, VisibilityLevel componentVisibility, EvolutionLevel componentEvolution) {
        this(componentName, componentVisibility, componentEvolution, false);
    }

    public Component(Name componentName, VisibilityLevel componentVisibility, EvolutionLevel componentEvolution, boolean hasInertia) {
        super();
        this.name = componentName;
        this.visibility = componentVisibility;
        this.evolution = componentEvolution;
        this.inertia = hasInertia;
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

    public boolean hasInertia() {
        return inertia;
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
