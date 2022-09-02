package de.tngbl.parsley.wardleymapping;

import org.jmolecules.ddd.annotation.Entity;

@Entity
public class EvolvedComponent extends Component {

    public static final String CONCATENATOR = "--->";

    public EvolvedComponent(Component origin, EvolutionLevel evolution) {
        super(new Name(origin.getName()), origin.getVisibility(), evolution);
    }

    public Name deriveName() {
        return Name.of(String.format("%s %s [%s]", super.getName().value(),CONCATENATOR, getEvolution().value()));
    }

    @Override
    public String toString() {
        return "EvolvedComponent{" +
                "name=" + getName() +
                ", visibility=" + getVisibility() +
                ", evolution=" + getEvolution() +
                ", movements=" + movements +
                '}';
    }
}
