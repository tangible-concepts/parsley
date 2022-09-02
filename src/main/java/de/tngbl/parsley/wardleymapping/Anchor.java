package de.tngbl.parsley.wardleymapping;


import org.jmolecules.ddd.annotation.Entity;

@Entity
public class Anchor extends ComponentBase {

    public Anchor(Name anchorName, VisibilityLevel anchorVisibilityLevel, EvolutionLevel anchorEvolutionLevel) {
        super(anchorName, anchorVisibilityLevel, anchorEvolutionLevel);
    }

    @Override
    public String toString() {
        return "Anchor{" +
                "name=" + getName() +
                ", visibility=" + getVisibility() +
                ", evolution=" + getEvolution() +
                '}';
    }
}
