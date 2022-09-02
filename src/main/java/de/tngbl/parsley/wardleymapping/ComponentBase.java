package de.tngbl.parsley.wardleymapping;

public abstract class ComponentBase extends MapElement implements Named {
    protected final Name name;
    protected final VisibilityLevel visibility;
    protected final EvolutionLevel evolution;

    public ComponentBase(Name name, VisibilityLevel visibility, EvolutionLevel evolution) {
        this.name = name;
        this.visibility = visibility;
        this.evolution = evolution;
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
}
