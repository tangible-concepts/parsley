package de.tngbl.parsley.wardleymapping;


import org.jmolecules.ddd.annotation.Entity;

@Entity
public class Anchor extends Component {

    public Anchor(Name anchorName, VisibilityLevel anchorVisibilityLevel, EvolutionLevel anchorEvolutionLevel) {
        super(anchorName, anchorVisibilityLevel, anchorEvolutionLevel);
    }

    @Override
    public String toString() {
        return "Anchor{" +
                "name=" + name +
                ", visibility=" + visibility +
                ", evolution=" + evolution +
                '}';
    }
}
